package com.ua.project.dao.assortment_typeDAO;

import com.ua.project.dao.CRUDInterface;
import com.ua.project.exception.ConnectionDBException;
import com.ua.project.model.AssortmentType;

import java.sql.SQLException;

public interface AssortmentTypeDao extends CRUDInterface<AssortmentType> {

    boolean isAssortmentTypeAvailable(String title) throws SQLException, ConnectionDBException;
    AssortmentType getAssortmentTypeByTitle(String title) throws ConnectionDBException, SQLException;
}
