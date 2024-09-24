package com.jie.repository;

import java.util.List;

public interface Repository<T> {
    void save(T t);
    T findById(int id);
    List<T> findAll();
    void delete(T t);
}
