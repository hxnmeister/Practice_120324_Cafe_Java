package com.ua.project.service.business.order;

import com.ua.project.dao.orderDAO.OrderDao;
import com.ua.project.dao.orderDAO.OrderDaoImp;
import com.ua.project.model.Order;

import java.util.List;

public class OrderServiceImp implements OrderService{
    @Override
    public String getAllOrders() {
        StringBuilder builder = new StringBuilder();
        OrderDao orderDao = new OrderDaoImp();
        List<Order> orders = orderDao.findAll();

        orders.forEach((order) -> {
            builder.append("\n id:")
                    .append(order.getId())
                    .append("| ")
                    .append(order.getPrice())
                    .append(" (")
                    .append(order.getPriceWithDiscount())
                    .append(") | ")
                    .append(order.getTimestamp())
                    .append(" |");
        });

        return builder.toString();
    }
}
