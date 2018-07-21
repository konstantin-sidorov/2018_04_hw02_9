package ru.otus;


import org.junit.Assert;
import ru.otus.db.*;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        List<DataSet> users = new ArrayList<>();
        UserDataSet u1 =new UserDataSet(1,"Маша", 25);
        UserDataSet u2 =new UserDataSet(2,"Даша", 23);
        users.add(u1);
        users.add(u2);
        try (DataBase db = new DBPrepared()) {
            db.prepareTables();
            db.addUsers(users);
            UserDataSet u3=db.loadUser(1,u1.getClass());
            Assert.assertEquals(true, u1.equals(u3));
        }
    }

}
