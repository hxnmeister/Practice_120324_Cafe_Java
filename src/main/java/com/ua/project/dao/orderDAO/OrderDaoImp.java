package com.ua.project.dao.orderDAO;

import com.ua.project.dao.ConnectionFactory;
import com.ua.project.dao.assortmentDAO.AssortmentDao;
import com.ua.project.dao.assortmentDAO.AssortmentDaoImp;
import com.ua.project.dao.order_and_assortmentDAO.OrderAndAssortmentDao;
import com.ua.project.dao.order_and_assortmentDAO.OrderAndAssortmentDaoImp;
import com.ua.project.exception.ConnectionDBException;
import com.ua.project.model.Assortment;
import com.ua.project.model.Client;
import com.ua.project.model.Order;
import com.ua.project.model.Personal;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private static final String GET_ALL_ORDERS_BY_SPECIFIC_DATE = """
        SELECT *
        FROM orders
        WHERE DATE(timestamp)=?
    """;
    private static final String GET_ALL_ORDERS_BY_DATE_RANGE = """
        SELECT *
        FROM orders
        WHERE DATE(timestamp) BETWEEN ? AND ?
    """;
    private static final String GET_COUNT_OF_ORDERS_BY_SPECIFIC_DATE_AND_ASSORTMENT_TYPE = """
        SELECT COUNT(*)
        FROM orders o
        JOIN orders_and_assortment oa ON o.id=oa.order_id
        JOIN assortment a ON a.id=oa.assortment_id
        JOIN assortment_types at ON at.id=a.assortment_type_id
        WHERE DATE(o.timestamp)=? AND at.title=?
    """;
    private static final String GET_CLIENTS_THAT_ORDERED_TODAY_WITH_PERSONAL_BY_PERSONAL_AND_ASSORTMENT_TYPE = """
        SELECT DISTINCT c.first_name AS client_fn,
                	    c.last_name AS client_ln,
                		c.patronymic AS client_pat,
                		p.first_name AS personal_fn,
                		p.last_name AS personal_ln,
                		p.patronymic AS personal_pat
        FROM assortment_types at
        JOIN assortment a ON at.id=a.assortment_type_id
        JOIN orders_and_assortment oa ON a.id=oa.assortment_id
        JOIN orders o ON o.id=oa.order_id
        JOIN clients c ON c.id=o.client_id
        JOIN personal p ON p.id=o.personal_id
        JOIN positions pos ON pos.id=p.position_id
        WHERE DATE(o.timestamp)=CURRENT_DATE AND pos.title=? AND at.title=?;
    """;
    private static final String GET_AVG_ORDER_PRICE_BY_SPECIFIC_DATE = """
        SELECT AVG(CAST(price AS NUMERIC))
        FROM orders
        WHERE DATE(timestamp)=?
    """;
    private static final String GET_MAX_ORDER_PRICE_BY_SPECIFIC_DATE = """
        SELECT MAX(CAST(price AS NUMERIC))
        FROM orders
        WHERE DATE(timestamp)=?
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
                    orders.add(getOrderFromResultSet(queryResult));
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
                    orders.add(getOrderFromResultSet(queryResult));
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
                    orders.add(getOrderFromResultSet(queryResult));
                }
            }
        }
        catch (ConnectionDBException | SQLException e) {
            System.out.println(e.getMessage());
        }

        return orders;
    }

    @Override
    public List<Order> findOrdersBySpecificDate(Date date) {
        List<Order> orders = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_ORDERS_BY_SPECIFIC_DATE)) {

            statement.setDate(1, date);

            try (ResultSet queryResult = statement.executeQuery()) {
                while (queryResult.next()) {
                    orders.add(getOrderFromResultSet(queryResult));
                }
            }
        }
        catch (ConnectionDBException | SQLException e) {
            System.out.println(e.getMessage());
        }

        return orders;
    }

    @Override
    public List<Order> findOrdersByDateRange(Date rangeBegin, Date rangeEnd) {
        List<Order> orders = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_ORDERS_BY_DATE_RANGE)) {

            statement.setDate(1, rangeBegin);
            statement.setDate(2, rangeEnd);

            try (ResultSet queryResult = statement.executeQuery()) {
                while (queryResult.next()) {
                    orders.add(getOrderFromResultSet(queryResult));
                }
            }
        }
        catch (ConnectionDBException | SQLException e) {
            System.out.println(e.getMessage());
        }

        return orders;
    }

    @Override
    public int getCountDrinkOrdersBySpecificDateAndAssortmentType(Date date, String assortmentType) {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement statement = connection.prepareStatement(GET_COUNT_OF_ORDERS_BY_SPECIFIC_DATE_AND_ASSORTMENT_TYPE)) {

            statement.setDate(1, date);
            statement.setString(2, assortmentType);

            try (ResultSet queryResult = statement.executeQuery()) {
                if (queryResult.next()) {
                    return queryResult.getInt("count");
                }
            }
        }
        catch (ConnectionDBException | SQLException e) {
            System.out.println(e.getMessage());
        }

        return 0;
    }

    @Override
    public Map<Client, Personal> findClientsOrderToday(String assortmentType, String position) {
        Map<Client, Personal> resultMap = new HashMap<>();

        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement statement = connection.prepareStatement(GET_CLIENTS_THAT_ORDERED_TODAY_WITH_PERSONAL_BY_PERSONAL_AND_ASSORTMENT_TYPE)) {

            statement.setString(1, position);
            statement.setString(2, assortmentType);

            try (ResultSet queryResult = statement.executeQuery()) {
                while (queryResult.next()) {
                    Client client = Client.builder().firstName(queryResult.getString("client_fn")).lastName(queryResult.getString("client_ln")).patronymic(queryResult.getString("client_pat")).build();
                    Personal personal = Personal.builder().firstName(queryResult.getString("personal_fn")).lastName(queryResult.getString("personal_ln")).patronymic(queryResult.getString("personal_pat")).build();

                    resultMap.put(client, personal);
                }
            }
        }
        catch (ConnectionDBException | SQLException e) {
            System.out.println(e.getMessage());
        }

        return resultMap;
    }

    @Override
    public BigDecimal getAvgOrderPriceBySpecificDate(Date date) {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement statement = connection.prepareStatement(GET_AVG_ORDER_PRICE_BY_SPECIFIC_DATE)) {

            statement.setDate(1, date);

            try (ResultSet queryResult = statement.executeQuery()) {
                if (queryResult.next()) {
                    return queryResult.getBigDecimal("avg");
                }
            }
        }
        catch (ConnectionDBException | SQLException e) {
            System.out.println(e.getMessage());
        }

        return BigDecimal.ZERO;
    }

    @Override
    public BigDecimal getMaxPriceOrderBySpecificDate(Date date) {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement statement = connection.prepareStatement(GET_MAX_ORDER_PRICE_BY_SPECIFIC_DATE)) {

            statement.setDate(1, date);

            try (ResultSet queryResult = statement.executeQuery()) {
                if (queryResult.next()) {
                    return queryResult.getBigDecimal("max");
                }
            }
        }
        catch (ConnectionDBException | SQLException e) {
            System.out.println(e.getMessage());
        }

        return BigDecimal.ZERO;
    }

    private Order getOrderFromResultSet(ResultSet resultSet) throws SQLException {
        return Order.builder()
                .id(resultSet.getLong("id"))
                .price(resultSet.getBigDecimal("price"))
                .priceWithDiscount(resultSet.getBigDecimal("price_with_discount"))
                .timestamp(resultSet.getTimestamp("timestamp"))
                .personalId(resultSet.getLong("personal_id"))
                .clientId(resultSet.getLong("client_id"))
                .build();
    }
}
