package com.ua.project.dao;

import com.ua.project.exception.ConnectionDBException;

import java.sql.SQLException;
import java.util.List;

public interface CRUDInterface<T> {

    void save(T item);

    void saveMany(List<T> items);

    void update(T item);

    void delete(T item);

    List<T> findAll();

    void deleteAll();

}
