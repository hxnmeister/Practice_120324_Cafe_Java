package com.ua.project.dao.ordersDAO;

import com.ua.project.dao.ConnectionFactory;
import com.ua.project.exception.ConnectionDBException;
import com.ua.project.model.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImp implements OrderDao {
    private static final String INSERT_ORDER = """
        INSERT INTO orders(price, price_with_discount, timestamp, personal_id, client_id)
        VALUES (?, ?, ?, ?, ?)
    """;
    private static final String UPDATE_ORDERS = """
        UPDATE orders
        SET price=?, price_with_discount=?, timestamp=?
        WHERE id=?
    """;
    private static final String DELETE_ORDER = """
        DELETE FROM orders
        WHERE id=?
    """;
    private static final String DELETE_ALL_ORDERS = """
        DELETE FROM orders
    """;
    private static final String GET_ALL_ORDERS = """
        SELECT *
        FROM orders
    """;

    @Override
    public void save(Order item) throws SQLException, ConnectionDBException {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_ORDER)) {

            statement.setBigDecimal(1, item.getPrice());
            statement.setBigDecimal(2, item.getPriceWithDiscount());
            statement.setTimestamp(3, item.getTimestamp());
            statement.setLong(4, item.getPersonalId());
            statement.setLong(5, item.getClientId());

            statement.execute();
        }
    }

    @Override
    public void saveMany(List<Order> items) throws SQLException, ConnectionDBException {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_ORDER)) {

            for (Order item : items) {
                statement.setBigDecimal(1, item.getPrice());
                statement.setBigDecimal(2, item.getPriceWithDiscount());
                statement.setTimestamp(3, item.getTimestamp());
                statement.setLong(4, item.getPersonalId());
                statement.setLong(5, item.getClientId());

                statement.addBatch();
            }

            statement.executeBatch();
        }
    }

    @Override
    public void update(Order item) throws SQLException, ConnectionDBException {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_ORDERS)) {

            statement.setBigDecimal(1, item.getPrice());
            statement.setBigDecimal(2, item.getPriceWithDiscount());
            statement.setTimestamp(3, item.getTimestamp());
            statement.setLong(4, item.getId());

            statement.execute();
        }
    }

    @Override
    public void delete(Order item) throws SQLException, ConnectionDBException {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_ORDER)) {

            statement.setLong(1, item.getId());
            statement.execute();
        }
    }

    @Override
    public List<Order> findAll() throws SQLException, ConnectionDBException {
        List<Order> orders = new ArrayList<Order>();

        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             Statement statement = connection.createStatement()) {

            try (ResultSet queryResult = statement.executeQuery(GET_ALL_ORDERS)) {
                while (queryResult.next()) {
                    orders.add(Order.builder()
                            .id(queryResult.getLong("id"))
                            .price(queryResult.getBigDecimal("price"))
                            .priceWithDiscount(queryResult.getBigDecimal("price_with_discount"))
                            .timestamp(queryResult.getTimestamp("timestamp"))
                            .personalId(queryResult.getLong("personal_id"))
                            .clientId(queryResult.getLong("client_id"))
                            .build());
                }
            }
        }

        return orders;
    }

    @Override
    public void deleteAll() throws SQLException, ConnectionDBException {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(DELETE_ALL_ORDERS);
        }
    }
}
