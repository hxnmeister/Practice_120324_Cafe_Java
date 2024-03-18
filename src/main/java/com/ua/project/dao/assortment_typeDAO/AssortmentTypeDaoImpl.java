package com.ua.project.dao.assortment_typeDAO;

import com.ua.project.dao.ConnectionFactory;
import com.ua.project.exception.ConnectionDBException;
import com.ua.project.model.AssortmentType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AssortmentTypeDaoImpl implements AssortmentTypeDao{
    private static final String INSERT_ASSORTMENT_TYPE = """
        INSERT INTO assortment_types(title)
        VALUES (?)
    """;
    private static final String UPDATE_ASSORTMENT_TYPE = """
        UPDATE assortment_types
        SET title=?
        WHERE id=?
    """;
    private static final String DELETE_ASSORTMENT_TYPES = """
        DELETE FROM assortment_types
        WHERE id=?
    """;
    private static final String DELETE_ALL_ASSORTMENT_TYPES = """
        DELETE FROM assortment_types
    """;
    private static final String GET_ALL_ASSORTMENT_TYPES = """
        SELECT *
        FROM assortment_types
    """;
    private static final String CHECK_ASSORTMENT_TYPE_AVAILABILITY = """
        SELECT EXISTS(
            SELECT title
            FROM assortment_types
            WHERE title=?
        )
    """;
    private static final String GET_ASSORTMENT_TYPE_BY_TITLE = """
        SELECT *
        FROM assortment_types
        WHERE title=?
    """;

    @Override
    public void save(AssortmentType item) {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_ASSORTMENT_TYPE)) {

            statement.setString(1, item.getTitle());
            statement.execute();
        }
        catch (ConnectionDBException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void saveMany(List<AssortmentType> items) {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_ASSORTMENT_TYPE)) {

            for (AssortmentType item : items) {
                statement.setString(1, item.getTitle());
                statement.addBatch();
            }

            statement.executeBatch();
        }
        catch (ConnectionDBException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(AssortmentType item) {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_ASSORTMENT_TYPE)) {

            statement.setString(1, item.getTitle());
            statement.setLong(2, item.getId());

            statement.execute();
        }
        catch (ConnectionDBException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(AssortmentType item) {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_ASSORTMENT_TYPES)) {

            statement.setLong(1, item.getId());
            statement.execute();
        }
        catch (ConnectionDBException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<AssortmentType> findAll() {
        List<AssortmentType> assortmentTypes = new ArrayList<AssortmentType>();

        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             Statement statement = connection.createStatement()) {

            try (ResultSet queryResult = statement.executeQuery(GET_ALL_ASSORTMENT_TYPES)) {
                while (queryResult.next()) {
                    assortmentTypes.add(AssortmentType.builder()
                            .id(queryResult.getLong("id"))
                            .title(queryResult.getString("title"))
                            .build());
                }
            }
        }
        catch (ConnectionDBException | SQLException e) {
            System.out.println(e.getMessage());
        }

        return assortmentTypes;
    }

    @Override
    public void deleteAll() {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(DELETE_ALL_ASSORTMENT_TYPES);
        }
        catch (ConnectionDBException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public boolean isAssortmentTypeAvailable(String title) {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement statement = connection.prepareStatement(CHECK_ASSORTMENT_TYPE_AVAILABILITY)) {

            statement.setString(1, title);

            try (ResultSet queryResult = statement.executeQuery()) {
                if (queryResult.next()) {
                    return queryResult.getBoolean(1);
                }
            }
        }
        catch (ConnectionDBException | SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    @Override
    public AssortmentType getAssortmentTypeByTitle(String title) {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ASSORTMENT_TYPE_BY_TITLE)) {

            statement.setString(1, title);
            try (ResultSet queryResult = statement.executeQuery()) {
                while (queryResult.next()) {
                    return AssortmentType.builder()
                            .id(queryResult.getLong("id"))
                            .title(queryResult.getString("title"))
                            .build();
                }
            }
        }
        catch (ConnectionDBException | SQLException e) {
            System.out.println(e.getMessage());
        }

        return AssortmentType.builder().build();
    }
}
