package com.jie.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface Dao<T> {
    void createTable() throws SQLException;
    void add(T t);
    void update(T t, Map<String, Object> updates);
    void delete(T t);
    T get(int id);
    List<T> getAll();
}
