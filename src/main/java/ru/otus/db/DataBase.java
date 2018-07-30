package ru.otus.db;

import ru.otus.DataSet;

import java.util.List;

public interface DataBase extends AutoCloseable {
    void prepareTables() throws DataBaseException;

    <T extends DataSet> void addUsers(List<T> users) throws DataBaseException;

    <T extends DataSet> T loadUser(long id, Class<T> clazz) throws DataBaseException;
}
