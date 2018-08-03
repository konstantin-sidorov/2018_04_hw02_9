package ru.otus;

import ru.otus.db.DataBaseException;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.TreeMap;

public class ReflectionHelper {
    public static <T extends DataSet> Map<String, String> getFields(Class<T> clazz) {
        Map<String, String> map = new TreeMap<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field f : fields) {
            map.put(f.getName(), f.getType().getSimpleName());
        }
        return map;
    }

    public static <T extends DataSet, R> void setValueByField(T obj, String nameField, R valueField) throws DataBaseException {
        try {
            Class c = obj.getClass();
            Field field = c.getDeclaredField(nameField);
            field.setAccessible(true);
            field.set(obj, valueField);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            DataBaseException se = new DataBaseException("Doesn't find by field:" + nameField);
            se.initCause(e);
            throw se;

        }
    }

    public static <T extends DataSet, R> R getValueByField(T obj, String nameField, String type) throws DataBaseException {
        try {
            Class c = obj.getClass();
            Field field = c.getDeclaredField(nameField);
            field.setAccessible(true);
            return (R) field.get(obj);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            DataBaseException se = new DataBaseException("Doesn't find by field:" + nameField);
            se.initCause(e);
            throw se;

        }
    }
}
