package ru.otus.db;

import ru.otus.DataSet;
import ru.otus.ReflectionHelper;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class DBPrepared implements DataBase {
    private static final String CREATE_TABLE_USERS = "create table user(id integer primary key auto_increment,name varchar(255),age integer);";
    //private static final String INSERT_INTO_USER = "insert into user (id,name,age) values(?,?,?);";
    //private static final String SELECT_USER = "select name,age from user where id=%s;";
    private final DBConnection connection;

    public DBPrepared() throws DataBaseException {
        connection = new DBConnection();
    }

    public Connection getConnection() {
        return connection.getConnection();
    }

    public void close() throws DataBaseException {
        connection.close();
    }

    @Override
    public void prepareTables() throws DataBaseException {
        Executor exec = new Executor(getConnection());
        exec.execSimpleQuery(CREATE_TABLE_USERS);
    }

    @Override
    public <T extends DataSet> T loadUser(long id, Class<T> clazz) throws DataBaseException {
        String SELECT_USER_UNIV = "select";
        Executor execT = new Executor(getConnection());
        Map<String, String> map = ReflectionHelper.getFields(clazz);
        String sep = " ";
        for (Map.Entry<String, String> entry : map.entrySet()) {
            SELECT_USER_UNIV = SELECT_USER_UNIV + sep + entry.getKey();
            sep = ", ";
        }
        SELECT_USER_UNIV = SELECT_USER_UNIV + "  from user where id=%s;";
        return execT.execQuery(String.format(SELECT_USER_UNIV, id), result -> {
                    try {
                        result.next();
                        T res = clazz.newInstance();
                        res.setId(id);
                        for (Map.Entry<String, String> entry : map.entrySet()) {
                            ReflectionHelper.setValueByField(res, entry.getKey(), result.getObject(entry.getKey()));
                        }
                       /*result.next();
                       int age = result.getInt("age");
                       String name = result.getString("name");
                       T res = clazz.newInstance();
                       MyObject myUser = new MyObject(res);
                       myUser.setId(id);
                       myUser.setName(name);
                       myUser.setAge(age);*/
                        return res;
                    } catch (SQLException | InstantiationException | IllegalAccessException e) {
                        DataBaseException se = new DataBaseException("loadUser error");
                        se.initCause(e);
                        throw se;
                    }
                }
        );
    }

    @Override
    public <T extends DataSet> void addUsers(List<T> users) throws DataBaseException {
        try {
            Executor exec = new Executor(getConnection());
            //getConnection().setAutoCommit(false);
            for (DataSet user : users) {
                String INSERT_INTO_USER_UNIV = "insert into user (&listfields&) values(&listplaceholder&);";
                String listfields = "id";
                String listplaceholder = "?";
                Map<String, String> map = ReflectionHelper.getFields(user.getClass());
                String sep = ", ";
                ;
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    listfields = listfields + sep + entry.getKey();
                    listplaceholder = listplaceholder + sep + "?";
                }
                INSERT_INTO_USER_UNIV = INSERT_INTO_USER_UNIV.replace("&listfields&", listfields);
                INSERT_INTO_USER_UNIV = INSERT_INTO_USER_UNIV.replace("&listplaceholder&", listplaceholder);
                exec.execUpdate(INSERT_INTO_USER_UNIV, statement -> {
                    try {
                        statement.setInt(1, (int) user.getId());
                        int parametrIndex = 2;
                        for (Map.Entry<String, String> entry : map.entrySet()) {
                            statement.setObject(parametrIndex, ReflectionHelper.getValueByField(user, entry.getKey(), entry.getValue()));
                            parametrIndex++;
                        }
                        statement.execute();
                    } catch (SQLException e) {
                        DataBaseException se = new DataBaseException("addUsers error");
                        se.initCause(e);
                        throw se;
                    }
               /*for (DataSet user : users) {
                   MyObject myUser = new MyObject(user);
                   try {

                       statement.setInt(1, myUser.getId());
                       statement.setString(2, myUser.getName());
                       statement.setInt(3, myUser.getAge());
                       statement.execute();
                   } catch (SQLException e) {
                       DataBaseException se = new DataBaseException("addUsers error");
                       se.initCause(e);
                       throw se;
                   }
               }*/
                });
            }
            getConnection().commit();
        } catch (SQLException e) {
            try {
                getConnection().rollback();
            } catch (SQLException ex) {
                DataBaseException se = new DataBaseException("addUsers rollback error");
                se.initCause(ex);
                throw se;
            }
        } finally {
            try {
                getConnection().setAutoCommit(true);
            } catch (SQLException ex) {
                DataBaseException se = new DataBaseException("addUsers setAutoCommit error");
                se.initCause(ex);
                throw se;
            }
        }
    }
}
