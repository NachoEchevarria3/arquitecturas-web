package com.jie.dao;

import java.sql.SQLException;
import java.util.List;

public interface Dao<T> {
    void createTable() throws SQLException;
    void add(T t) throws SQLException;
    void update(T t) throws SQLException;
    void delete(T t) throws SQLException;
    T get(int id) throws SQLException;
    List<T> getAll() throws SQLException;
}
