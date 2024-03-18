package com.ua.project.dao.assortmentDAO;

import com.ua.project.dao.ConnectionFactory;
import com.ua.project.exception.ConnectionDBException;
import com.ua.project.model.Assortment;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AssortmentDaoImp implements AssortmentDao{
    private static final String INSERT_ASSORTMENT = """
        INSERT INTO assortment(title, quantity, price, assortment_type_id)
        VALUES (?, ?, ?, ?)
    """;
    private static final String UPDATE_ASSORTMENT = """
        UPDATE assortment
        SET title=?, quantity=?, price=?, assortment_type_id=?
        WHERE id=?
    """;
    private static final String DELETE_ASSORTMENT = """
        DELETE FROM assortment
        WHERE id=?
    """;
    private static final String DELETE_ALL_ASSORTMENT = """
        DELETE FROM assortment
    """;
    private static final String GET_ALL_ASSORTMENT = """
        SELECT *
        FROM assortment
    """;
    private static final String UPDATE_PRICE_BY_TYPE_AND_TITLE = """
        UPDATE assortment
        SET price=?
        WHERE id=(
            SELECT a.id
            FROM assortment a
            JOIN assortment_types at ON a.assortment_type_id = at.id
            WHERE a.title=? AND at.title=?
        )
    """;
    private static final String DELETE_ASSORTMENT_BY_TYPE_AND_TITLE = """
        DELETE FROM assortment
        WHERE id=(
            SELECT a.id
            FROM assortment a
            JOIN assortment_types at ON a.assortment_type_id = at.id
            WHERE a.title=? AND at.title=?
        )
    """;
    private static final String GET_ASSORTMENT_BY_TYPE = """
        SELECT a.id, a.title, a.quantity, a.price, a.assortment_type_id
        FROM assortment a
        JOIN assortment_types at ON a.assortment_type_id = at.id
        WHERE at.title=?
    """;

    @Override
    public void save(Assortment item) {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_ASSORTMENT)) {

            statement.setString(1, item.getTitle());
            statement.setInt(2, item.getQuantity());
            statement.setBigDecimal(3, item.getPrice());
            statement.setLong(4, item.getAssortmentTypeId());

            statement.execute();
        }
        catch (ConnectionDBException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void saveMany(List<Assortment> items) {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_ASSORTMENT)) {

            for (Assortment item : items) {
                statement.setString(1, item.getTitle());
                statement.setInt(2, item.getQuantity());
                statement.setBigDecimal(3, item.getPrice());
                statement.setLong(4, item.getAssortmentTypeId());

                statement.addBatch();
            }

            statement.executeBatch();
        }
        catch (ConnectionDBException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Assortment item) {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_ASSORTMENT)) {

            statement.setString(1, item.getTitle());
            statement.setInt(2, item.getQuantity());
            statement.setBigDecimal(3, item.getPrice());
            statement.setLong(4, item.getAssortmentTypeId());
            statement.setLong(5, item.getId());

            statement.execute();
        }
        catch (ConnectionDBException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Assortment item) {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_ASSORTMENT)) {

            statement.setLong(1, item.getId());
            statement.execute();
        }
        catch (ConnectionDBException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Assortment> findAll() {
        List<Assortment> assortment = new ArrayList<Assortment>();

        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             Statement statement = connection.createStatement()) {

            try (ResultSet queryResult = statement.executeQuery(GET_ALL_ASSORTMENT)) {
                while (queryResult.next()) {
                    assortment.add(Assortment.builder()
                            .id(queryResult.getLong("id"))
                            .title(queryResult.getString("title"))
                            .quantity(queryResult.getInt("quantity"))
                            .price(queryResult.getBigDecimal("price"))
                            .assortmentTypeId(queryResult.getLong("assortment_type_id"))
                            .build());
                }
            }
        }
        catch (ConnectionDBException | SQLException e) {
            System.out.println(e.getMessage());
        }

        return assortment;
    }

    @Override
    public void deleteAll() {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(DELETE_ALL_ASSORTMENT);
        }
        catch (ConnectionDBException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void changePriceByTypeAndTitle(String type, String title, BigDecimal newPrice) {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_PRICE_BY_TYPE_AND_TITLE)) {

            statement.setBigDecimal(1, newPrice);
            statement.setString(2, title);
            statement.setString(3, type);

            statement.execute();
        }
        catch (ConnectionDBException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteAssortmentByTypeAndTitle(String type, String title) {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_ASSORTMENT_BY_TYPE_AND_TITLE)) {

            statement.setString(1, title);
            statement.setString(2, type);

            statement.execute();
        }
        catch (ConnectionDBException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Assortment> getAssortmentByType(String type) {
        List<Assortment> assortment = new ArrayList<Assortment>();

        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ASSORTMENT_BY_TYPE)) {

            statement.setString(1, type);

            try (ResultSet queryResult = statement.executeQuery()) {
                while (queryResult.next()) {
                    assortment.add(Assortment.builder()
                            .id(queryResult.getLong("id"))
                            .title(queryResult.getString("title"))
                            .quantity(queryResult.getInt("quantity"))
                            .price(queryResult.getBigDecimal("price"))
                            .assortmentTypeId(queryResult.getLong("assortment_type_id"))
                            .build());
                }
            }
        }
        catch (ConnectionDBException | SQLException e) {
            System.out.println(e.getMessage());
        }

        return assortment;
    }
}
