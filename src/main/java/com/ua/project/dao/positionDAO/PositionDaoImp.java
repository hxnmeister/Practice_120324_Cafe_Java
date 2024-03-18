package com.ua.project.dao.positionDAO;

import com.ua.project.dao.ConnectionFactory;
import com.ua.project.exception.ConnectionDBException;
import com.ua.project.model.AssortmentType;
import com.ua.project.model.Position;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PositionDaoImp implements PositionDao {
    private static final String INSERT_POSITION = """
        INSERT INTO positions(title)
        VALUES (?)
    """;
    private static final String UPDATE_POSITION = """
        UPDATE positions
        SET title=?
        WHERE id=?
    """;
    private static final String DELETE_POSITION = """
        DELETE FROM positions
        WHERE id=?
    """;
    private static final String DELETE_ALL_POSITIONS = """
        DELETE FROM positions
    """;
    private static final String GET_ALL_POSITIONS = """
        SELECT *
        FROM positions
    """;
    private static final String CHECK_POSITION_AVAILABILITY = """
        SELECT EXISTS(
            SELECT title
            FROM positions
            WHERE title=?
        )
    """;
    private static final String GET_POSITION_BY_TITLE = """
        SELECT *
        FROM positions
        WHERE title=?
    """;

    @Override
    public void save(Position item) {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_POSITION)) {

            statement.setString(1, item.getTitle());
            statement.execute();
        }
        catch (ConnectionDBException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void saveMany(List<Position> items) {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_POSITION)) {

            for (Position item : items) {
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
    public void update(Position item) {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_POSITION)) {

            statement.setString(1, item.getTitle());
            statement.setLong(2, item.getId());

            statement.execute();
        }
        catch (ConnectionDBException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Position item) {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_POSITION)) {

            statement.setLong(1, item.getId());
            statement.execute();
        }
        catch (ConnectionDBException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Position> findAll() {
        List<Position> assortmentTypes = new ArrayList<Position>();

        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             Statement statement = connection.createStatement()) {

            try (ResultSet queryResult = statement.executeQuery(GET_ALL_POSITIONS)) {
                while (queryResult.next()) {
                    assortmentTypes.add(Position.builder()
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

            statement.execute(DELETE_ALL_POSITIONS);
        }
        catch (ConnectionDBException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public boolean isPositionAvailable(String title) {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement statement = connection.prepareStatement(CHECK_POSITION_AVAILABILITY)) {

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
    public Position getPositionByTitle(String title) {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement statement = connection.prepareStatement(GET_POSITION_BY_TITLE)) {

            statement.setString(1, title);
            try (ResultSet queryResult = statement.executeQuery()) {
                while (queryResult.next()) {
                    return Position.builder()
                            .id(queryResult.getLong("id"))
                            .title(queryResult.getString("title"))
                            .build();
                }
            }
        }
        catch (ConnectionDBException | SQLException e) {
            System.out.println(e.getMessage());
        }

        return Position.builder().build();
    }
}
