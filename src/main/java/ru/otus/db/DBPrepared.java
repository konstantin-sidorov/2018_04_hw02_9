package ru.otus.db;


import ru.otus.DataSet;
import ru.otus.UserDataSet;

import java.sql.SQLException;
import java.util.List;

public class DBPrepared extends DBConnection {
    private static final String CREATE_TABLE_USERS = "create table user(id integer primary key auto_increment,name varchar(255),age integer);";
    private static final String INSERT_INTO_USER = "insert into user (id,name,age) values(?,?,?);";
    private static final String SELECT_USER = "select name,age from user where id=%s;";

    @Override
    public void prepareTables() throws SQLException {
        Executor exec = new Executor(getConnection());
        exec.execSimpleQuery(CREATE_TABLE_USERS);
    }
    @Override
    public <T extends DataSet> T loadUser(long id, Class<T> clazz) throws SQLException, IllegalAccessException, InstantiationException {
        Executor execT = new Executor(getConnection());

        return execT.execQuery(String.format(SELECT_USER, id), result -> {
            result.next();
            int age =result.getInt("age");
            String name = result.getString("name");
            T res=clazz.newInstance();
            res.setId(id);
            ((UserDataSet) res).setName(name);
            ((UserDataSet) res).setAge(age);
            return res;
        });
    }
    @Override
    public <T extends DataSet> void addUsers(List<T> users) throws SQLException {
        try {
            Executor exec = new Executor(getConnection());
            getConnection().setAutoCommit(false);
            exec.execUpdate(INSERT_INTO_USER, statement -> {
                for (DataSet user : users) {
                    statement.setInt(1, (int) user.getId());
                    statement.setString(2, ((UserDataSet) user).getName());
                    statement.setInt(3, ((UserDataSet) user).getAge());
                    statement.execute();
                }
            });
            getConnection().commit();
        } catch (SQLException e){
            getConnection().rollback();
        } finally {
            getConnection().setAutoCommit(true);
        }
    }
}
