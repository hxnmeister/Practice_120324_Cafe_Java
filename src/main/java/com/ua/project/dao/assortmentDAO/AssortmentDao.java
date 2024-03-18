package com.ua.project.dao.assortmentDAO;

import com.ua.project.dao.CRUDInterface;
import com.ua.project.exception.ConnectionDBException;
import com.ua.project.model.Assortment;
import com.ua.project.model.AssortmentType;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public interface AssortmentDao extends CRUDInterface<Assortment> {

    void changePriceByTypeAndTitle(String type, String title, BigDecimal newPrice);
    void deleteAssortmentByTypeAndTitle(String type, String title);
    List<Assortment> getAssortmentByType(String type);
}
