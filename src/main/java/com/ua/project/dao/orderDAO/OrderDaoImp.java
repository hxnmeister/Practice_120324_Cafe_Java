package com.ua.project.dao.orderDAO;

import com.ua.project.dao.ConnectionFactory;
import com.ua.project.dao.assortmentDAO.AssortmentDao;
import com.ua.project.dao.assortmentDAO.AssortmentDaoImp;
import com.ua.project.dao.order_and_assortmentDAO.OrderAndAssortmentDao;
import com.ua.project.dao.order_and_assortmentDAO.OrderAndAssortmentDaoImp;
import com.ua.project.exception.ConnectionDBException;
import com.ua.project.model.Assortment;
import com.ua.project.model.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImp implements OrderDao {
    private static final String INSERT_ORDER = """
        INSERT INTO orders(price, price_with_discount, timestamp, personal_id, client_id)
        VALUES (?, ?, ?, ?, ?)
    """;
    private static final String INSERT_ORDER_AND_RETURN_ID = """
        INSERT INTO orders(price, price_with_discount, timestamp, personal_id, client_id)
        VALUES (?, ?, ?, ?, ?)
        RETURNING id
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
    private static final String GET_ALL_ORDERS_BY_PERSONAL_ID = """
        SELECT *
        FROM orders
        WHERE personal_id=?
    """;
    private static final String GET_ALL_ORDERS_BY_CLIENT_ID = """
        SELECT *
        FROM orders
        WHERE client_id=?
    """;

    @Override
    public void save(Order item) {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_ORDER)) {

            statement.setBigDecimal(1, item.getPrice());
            statement.setBigDecimal(2, item.getPriceWithDiscount());
            statement.setTimestamp(3, item.getTimestamp());
            statement.setLong(4, item.getPersonalId());
            statement.setLong(5, item.getClientId());

            statement.execute();
        }
        catch (ConnectionDBException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public long saveWithReturningId(Order item) {
        long newOrderId = 0;

        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_ORDER_AND_RETURN_ID)) {

            statement.setBigDecimal(1, item.getPrice());
            statement.setBigDecimal(2, item.getPriceWithDiscount());
            statement.setTimestamp(3, item.getTimestamp());
            statement.setLong(4, item.getPersonalId());
            statement.setLong(5, item.getClientId());

            try (ResultSet queryResult = statement.executeQuery()) {
                while (queryResult.next()) {
                    newOrderId = queryResult.getLong("id");
                }
            }
        }
        catch (ConnectionDBException | SQLException e) {
            System.out.println(e.getMessage());
        }

        return newOrderId;
    }

    @Override
    public void saveMany(List<Order> items) {
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
        catch (ConnectionDBException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Order item) {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_ORDERS)) {

            statement.setBigDecimal(1, item.getPrice());
            statement.setBigDecimal(2, item.getPriceWithDiscount());
            statement.setTimestamp(3, item.getTimestamp());
            statement.setLong(4, item.getId());

            statement.execute();
        }
        catch (ConnectionDBException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Order item) {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_ORDER)) {

            statement.setLong(1, item.getId());
            statement.execute();
        }
        catch (ConnectionDBException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Order> findAll() {
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
        catch (ConnectionDBException | SQLException e) {
            System.out.println(e.getMessage());
        }

        return orders;
    }

    @Override
    public void deleteAll() {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(DELETE_ALL_ORDERS);
        }
        catch (ConnectionDBException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void addNewOrder(List<Assortment> assortment, Order order) {
        long orderId = this.saveWithReturningId(order);
        OrderAndAssortmentDao orderAndAssortmentDao = new OrderAndAssortmentDaoImp();

        assortment.forEach((item) -> orderAndAssortmentDao.assignAssortmentToOrder(orderId, item.getTitle()));
    }

    @Override
    public List<Order> findOrdersByPersonalId(long id) {
        List<Order> orders = new ArrayList<Order>();

        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_ORDERS_BY_PERSONAL_ID)) {

            statement.setLong(1, id);

            try (ResultSet queryResult = statement.executeQuery()) {
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
        catch (ConnectionDBException | SQLException e) {
            System.out.println(e.getMessage());
        }

        return orders;
    }

    @Override
    public List<Order> findOrdersByClientId(long id) {
        List<Order> orders = new ArrayList<Order>();

        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_ORDERS_BY_CLIENT_ID)) {

            statement.setLong(1, id);

            try (ResultSet queryResult = statement.executeQuery()) {
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
        catch (ConnectionDBException | SQLException e) {
            System.out.println(e.getMessage());
        }

        return orders;
    }
}
