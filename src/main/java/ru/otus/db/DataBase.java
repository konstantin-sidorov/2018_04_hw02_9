package ru.otus.db;


import ru.otus.DataSet;

import java.sql.SQLException;
import java.util.List;

public interface DataBase extends AutoCloseable {
    void prepareTables() throws SQLException;

    <T extends DataSet> void addUsers(List<T> users) throws SQLException ;
    <T extends DataSet> T loadUser(long id, Class<T> clazz) throws SQLException, IllegalAccessException, InstantiationException;
}
