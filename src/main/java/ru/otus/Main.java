package ru.otus;

import ru.otus.db.DBPrepared;
import ru.otus.db.DataBase;
import ru.otus.db.DataBaseException;

import java.util.ArrayList;
import java.util.List;
import junit.*;

public class Main {
    public static void main(String[] args) throws DataBaseException {
        List<DataSet> users = new ArrayList<>();
        UserDataSet u1 =new UserDataSet(1,"Маша", 25);
        UserDataSet u2 =new UserDataSet(2,"Даша", 23);
        users.add(u1);
        users.add(u2);
        try (DataBase db = new DBPrepared()) {
            db.prepareTables();
            db.addUsers(users);
            UserDataSet u3=db.loadUser(1,u1.getClass());
            //Assert.assertEquals(true, u1.equals(u3));
            System.out.println(""+u3);
        } catch (Exception e) {
            DataBaseException se = new DataBaseException("main error");
            se.initCause(e);
            throw se;
        }
    }

}
