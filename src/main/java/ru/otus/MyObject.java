package ru.otus;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MyObject<T extends DataSet> {
    private T obj;

    public MyObject(T obj) {
        this.obj = obj;
    }

    public T getObj() {
        return obj;
    }

    public void setId(long id) {
        obj.setId(id);
    }

    public void setName(String name) {
        Class clazz = obj.getClass();
        try {
            Class[] paramTypes = new Class[]{String.class};
            Method nameMethod = clazz.getDeclaredMethod("setName", paramTypes);
            Object[] args = new Object[]{name};
            nameMethod.invoke(obj, args);
        } catch (NoSuchMethodException e) {
            try {
                Field nameField = clazz.getDeclaredField("name");
                nameField.set(obj, name);
            } catch (NoSuchFieldException e1) {
                e1.printStackTrace();
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            }
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void setAge(int age) {
        Class clazz = obj.getClass();
        try {
            Class[] paramTypes = new Class[]{int.class};
            Method nameMethod = clazz.getDeclaredMethod("setAge", paramTypes);
            Object[] args = new Object[]{age};
            nameMethod.invoke(obj, args);
        } catch (NoSuchMethodException e) {
            try {
                Field nameField = clazz.getDeclaredField("age");
                nameField.set(obj, age);
            } catch (NoSuchFieldException e1) {
                e1.printStackTrace();
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            }
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return (int) obj.getId();
    }

    public String getName() {
        Class clazz = obj.getClass();
        try {
            Class[] paramTypes = new Class[]{};
            Method nameMethod = clazz.getDeclaredMethod("getName", paramTypes);
            Object[] args = new Object[]{};
            return (String) nameMethod.invoke(obj, args);
        } catch (NoSuchMethodException e) {
            try {
                Field nameField = clazz.getDeclaredField("name");
                return (String) nameField.get(obj);
            } catch (NoSuchFieldException e1) {
                return new String("NoName");
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
                return new String("NoName");
            }
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return new String("NoName");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return new String("NoName");
        }
    }

    public int getAge() {
        Class clazz = obj.getClass();
        try {
            Class[] paramTypes = new Class[]{};
            Method nameMethod = clazz.getDeclaredMethod("getAge", paramTypes);
            Object[] args = new Object[]{};
            return (int) nameMethod.invoke(obj, args);
        } catch (NoSuchMethodException e) {
            try {
                Field nameField = clazz.getDeclaredField("age");
                return (int) nameField.get(obj);
            } catch (NoSuchFieldException e1) {
                return -1;
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
                return -1;
            }
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return -1;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
