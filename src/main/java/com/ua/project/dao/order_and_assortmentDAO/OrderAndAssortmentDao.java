package com.ua.project.dao.order_and_assortmentDAO;

import com.ua.project.dao.CRUDInterface;
import com.ua.project.exception.ConnectionDBException;
import com.ua.project.model.OrderAndAssortment;

import java.sql.SQLException;

public interface OrderAndAssortmentDao {
    void save(OrderAndAssortment orderAndAssortment);
    void assignAssortmentToOrder(long orderId, String assortmentTitle);
    void deleteAssortmentFromOrder(long orderId, String assortmentTitle);
    void deleteAll();
}
