package com.ua.project.dao;

import com.ua.project.exception.ConnectionDBException;

import java.sql.SQLException;
import java.util.List;

public interface CRUDInterface<T> {

    void save(T item) throws SQLException, ConnectionDBException;

    void saveMany(List<T> items) throws SQLException, ConnectionDBException;

    void update(T item) throws SQLException, ConnectionDBException;

    void delete(T item) throws SQLException, ConnectionDBException;

    List<T> findAll() throws SQLException, ConnectionDBException;

    void deleteAll() throws SQLException, ConnectionDBException;

}
