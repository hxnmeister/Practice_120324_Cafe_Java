package com.ua.project.dao.order_and_assortmentDAO;

import com.ua.project.dao.CRUDInterface;
import com.ua.project.exception.ConnectionDBException;
import com.ua.project.model.OrderAndAssortment;

import java.sql.SQLException;
import java.util.List;

public interface OrderAndAssortmentDao {
    void save(OrderAndAssortment orderAndAssortment);
    List<OrderAndAssortment> findAll();
    void assignAssortmentToOrder(long orderId, String assortmentTitle);
    void deleteAssortmentFromOrder(long orderId, String assortmentTitle);
    void deleteAll();
}
