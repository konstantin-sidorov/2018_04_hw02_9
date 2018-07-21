package ru.otus;


public abstract class DataSet {
    long id;

    public DataSet(long id) {
        this.id = id;
    }
    public DataSet() {

    }
    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
