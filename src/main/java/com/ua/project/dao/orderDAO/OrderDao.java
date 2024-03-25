package com.ua.project.dao.orderDAO;

import com.google.common.collect.Multimap;
import com.ua.project.dao.CRUDInterface;
import com.ua.project.model.Assortment;
import com.ua.project.model.Client;
import com.ua.project.model.Order;
import com.ua.project.model.Personal;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Map;

public interface OrderDao extends CRUDInterface<Order> {
    void addNewOrder(List<Assortment> assortment, Order order);
    long saveWithReturningId(Order item);
    List<Order> findOrdersByPersonalId(long id);
    List<Order> findOrdersByClientId(long id);
    List<Order> findOrdersBySpecificDate(Date date);
    List<Order> findOrdersByDateRange(Date rangeBegin, Date rangeEnd);
    Map<Client, Personal> findClientsOrderToday(String assortmentType, String position);
    BigDecimal getAvgOrderPriceBySpecificDate(Date date);
    BigDecimal getMaxPriceOrderBySpecificDate(Date date);
    int getCountDrinkOrdersBySpecificDateAndAssortmentType(Date date, String assortmentType);
}
