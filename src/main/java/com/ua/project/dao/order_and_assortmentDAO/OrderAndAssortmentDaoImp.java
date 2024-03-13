package com.ua.project.dao.order_and_assortmentDAO;

import com.ua.project.dao.ConnectionFactory;
import com.ua.project.exception.ConnectionDBException;
import com.ua.project.model.OrderAndAssortment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class OrderAndAssortmentDaoImp implements OrderAndAssortmentDao {
    private static final String INSERT_ORDER_AND_ASSORTMENT = """
        INSERT INTO orders_and_assortment(order_id, assortment_id)
        VALUES(?, ?)
    """;
    private static final String ASSIGN_ASSORTMENT_TO_ORDER = """
        INSERT INTO orders_and_assortment(order_id, assortment_id)
        VALUES (?, (
            SELECT a.id 
            FROM assortments a
            WHERE a.title=?
        )
    """;
    private static final String DELETE_ASSORTMENT_FROM_ORDER = """
        DELETE FROM orders_and_assortment
        WHERE order_id=? AND assortment_id=(
            SELECT a.id
            FROM assortments a
            WHERE a.title=?
        )
    """;
    private static final String DELETE_ALL_ORDER_AND_ASSORTMENT = """
        DELETE FROM orders_and_assortment
    """;

    @Override
    public void save(OrderAndAssortment orderAndAssortment) throws ConnectionDBException, SQLException {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_ORDER_AND_ASSORTMENT)) {

            statement.setLong(1, orderAndAssortment.getOrderId());
            statement.setLong(2, orderAndAssortment.getAssortmentId());

            statement.execute();
        }
    }

    @Override
    public void assignAssortmentToOrder(long orderId, String assortmentTitle) throws ConnectionDBException, SQLException {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement statement = connection.prepareStatement(ASSIGN_ASSORTMENT_TO_ORDER)) {

            statement.setLong(1, orderId);
            statement.setString(2, assortmentTitle);
        }
    }

    @Override
    public void deleteAssortmentFromOrder(long orderId, String assortmentTitle) throws ConnectionDBException, SQLException {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_ASSORTMENT_FROM_ORDER)) {

            statement.setLong(1, orderId);
            statement.setString(2, assortmentTitle);

            statement.execute();
        }
    }

    @Override
    public void deleteAll() throws ConnectionDBException, SQLException {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(DELETE_ALL_ORDER_AND_ASSORTMENT);
        }
    }
}
