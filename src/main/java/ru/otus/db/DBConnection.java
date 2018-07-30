package ru.otus.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {
    private final Connection connection;

    public DBConnection() throws DataBaseException {
        try {
            //Class.forName("org.h2.Driver");
            DriverManager.registerDriver(new org.h2.Driver());
            connection = DriverManager.getConnection("jdbc:h2:mem:test");
        } catch (SQLException e) {
            DataBaseException se = new DataBaseException("connection error");
            se.initCause(e);
            throw se;
        }
    }

    protected Connection getConnection() {
        return connection;
    }

    public void close() throws DataBaseException {
        try {
            connection.close();
        } catch (SQLException e) {
            DataBaseException se = new DataBaseException("close error");
            se.initCause(e);
            throw se;
        }
    }
}
