package com.ua.project.dao.orderDAO;

import com.google.common.collect.Multimap;
import com.ua.project.dao.CRUDInterface;
import com.ua.project.model.Assortment;
import com.ua.project.model.Order;

import java.util.List;

public interface OrderDao extends CRUDInterface<Order> {
    void addNewOrder(List<Assortment> assortment, Order order);
    long saveWithReturningId(Order item);
}
