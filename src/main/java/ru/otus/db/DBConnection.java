package ru.otus.db;


import ru.otus.DataSet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class DBConnection implements DataBase {
    private final Connection connection;

    public DBConnection() {
        try {
            //Class.forName("org.h2.Driver");
            DriverManager.registerDriver(new org.h2.Driver());
            connection = DriverManager.getConnection("jdbc:h2:mem:test");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected Connection getConnection() {
        return connection;
    }

    public void prepareTables() throws SQLException {

    }

    @Override
    public <T extends DataSet> void addUsers(List<T> users) throws SQLException  {

    }

    @Override
    public <T extends DataSet> T loadUser(long id, Class<T> clazz) throws SQLException, IllegalAccessException, InstantiationException {
        return null;
    }

    public void close() throws Exception {
        connection.close();
    }
}
