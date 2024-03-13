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

    @Override
    public void save(Position item) throws SQLException, ConnectionDBException {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_POSITION)) {

            statement.setString(1, item.getTitle());
            statement.execute();
        }
    }

    @Override
    public void saveMany(List<Position> items) throws SQLException, ConnectionDBException {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_POSITION)) {

            for (Position item : items) {
                statement.setString(1, item.getTitle());
                statement.addBatch();
            }

            statement.executeBatch();
        }
    }

    @Override
    public void update(Position item) throws SQLException, ConnectionDBException {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_POSITION)) {

            statement.setString(1, item.getTitle());
            statement.setLong(2, item.getId());

            statement.execute();
        }
    }

    @Override
    public void delete(Position item) throws SQLException, ConnectionDBException {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_POSITION)) {

            statement.setLong(1, item.getId());
            statement.execute();
        }
    }

    @Override
    public List<Position> findAll() throws SQLException, ConnectionDBException {
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

        return assortmentTypes;
    }

    @Override
    public void deleteAll() throws SQLException, ConnectionDBException {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(DELETE_ALL_POSITIONS);
        }
    }

    @Override
    public boolean isPositionAvailable(String title) throws SQLException, ConnectionDBException {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement statement = connection.prepareStatement(CHECK_POSITION_AVAILABILITY)) {

            statement.setString(1, title);

            try (ResultSet queryResult = statement.executeQuery()) {
                if (queryResult.next()) {
                    return queryResult.getBoolean(1);
                }

                return false;
            }
        }
    }
}
