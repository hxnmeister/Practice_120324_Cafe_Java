package com.ua.project.dao.personalDAO;

import com.ua.project.dao.ConnectionFactory;
import com.ua.project.exception.ConnectionDBException;
import com.ua.project.model.Personal;
import com.ua.project.model.PersonalPhoneNumber;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonalDaoImp implements PersonalDao {
    private static final String INSERT_PERSONAL = """
        INSERT INTO personal(first_name, last_name, patronymic, position_id)
        VALUES (?, ?, ?, ?)
    """;
    private static final String UPDATE_PERSONAL = """
        UPDATE personal
        SET first_name=?, last_name=?, patronymic=?
        WHERE id=?
    """;
    private static final String DELETE_PERSONAL = """
        DELETE FROM personal
        WHERE id=?
    """;
    private static final String DELETE_ALL_PERSONAL = """
        DELETE FROM personal
    """;
    private static final String GET_ALL_PERSONAL = """
        SELECT *
        FROM personal
    """;
    private static final String UPDATE_EMAIL_ADDRESS_BY_POSITION_AND_NAME = """
        UPDATE personal_email_addresses pea
        SET email_address=?
        WHERE pea.personal_id=(
            SELECT p.id
            FROM personal p
            JOIN positions pos ON p.position_id=pos.id
            WHERE pos.title=? AND p.first_name=? AND p.last_name=?
        ) AND pea.email_address=?
    """;
    private static final String UPDATE_PHONE_NUMBER_BY_POSITION_AND_NAME = """
        UPDATE personal_phone_numbers ppn
        SET phone_number=?
        WHERE ppn.personal_id=(
            SELECT p.id
            FROM personal p
            JOIN positions pos ON p.position_id=pos.id
            WHERE pos.title=? AND p.first_name=? AND p.last_name=?
        ) AND ppn.phone_number=?
    """;
    private static final String DELETE_PERSONAL_BY_POSITION_AND_NAME = """
        DELETE FROM personal
        WHERE first_name=? AND last_name=? AND position_id=(
            SELECT p.id
            FROM positions p
            WHERE p.title=?
        )
    """;
    private static final String GET_PERSONAL_BY_POSITION = """
        SELECT per.id, per.first_name, per.last_name, per.patronymic, per.position_id
        FROM personal per
        JOIN positions pos ON per.position_id = pos.id
        WHERE pos.title=?
    """;

    @Override
    public void save(Personal item) throws SQLException, ConnectionDBException {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_PERSONAL)) {

            statement.setString(1, item.getFirstName());
            statement.setString(2, item.getLastName());
            statement.setString(3, item.getPatronymic());
            statement.setLong(4, item.getPositionId());

            statement.execute();
        }
    }

    @Override
    public void saveMany(List<Personal> items) throws SQLException, ConnectionDBException {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_PERSONAL)) {

            for (Personal item : items) {
                statement.setString(1, item.getFirstName());
                statement.setString(2, item.getLastName());
                statement.setString(3, item.getPatronymic());
                statement.setLong(4, item.getPositionId());

                statement.addBatch();
            }

            statement.executeBatch();
        }
    }

    @Override
    public void update(Personal item) throws SQLException, ConnectionDBException {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_PERSONAL)) {

            statement.setString(1, item.getFirstName());
            statement.setString(2, item.getLastName());
            statement.setString(3, item.getPatronymic());
            statement.setLong(2, item.getId());

            statement.execute();
        }
    }

    @Override
    public void delete(Personal item) throws SQLException, ConnectionDBException {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_PERSONAL)) {

            statement.setLong(1, item.getId());
            statement.execute();
        }
    }

    @Override
    public List<Personal> findAll() throws SQLException, ConnectionDBException {
        List<Personal> assortment = new ArrayList<Personal>();

        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             Statement statement = connection.createStatement()) {

            try (ResultSet queryResult = statement.executeQuery(GET_ALL_PERSONAL)) {
                while (queryResult.next()) {
                    assortment.add(Personal.builder()
                            .id(queryResult.getLong("id"))
                            .firstName(queryResult.getString("first_name"))
                            .lastName(queryResult.getString("last_name"))
                            .patronymic(queryResult.getString("patronymic"))
                            .positionId(queryResult.getLong("position_id"))
                            .build());
                }
            }
        }
        return assortment;
    }

    @Override
    public void deleteAll() throws SQLException, ConnectionDBException {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(DELETE_ALL_PERSONAL);
        }
    }

    @Override
    public void changeEmailAddressByPositionAndName(String newEmailAddress, String oldEmailAddress, String position, Personal personal) throws ConnectionDBException, SQLException {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_EMAIL_ADDRESS_BY_POSITION_AND_NAME)) {

            statement.setString(1, newEmailAddress);
            statement.setString(2, position);
            statement.setString(3, personal.getFirstName());
            statement.setString(4, personal.getLastName());
            statement.setString(5, oldEmailAddress);

            statement.execute();
        }
    }

    @Override
    public void changePhoneNumberByPositionAndName(String newPhoneNumber, String oldPhoneNumber, String position, Personal personal) throws ConnectionDBException, SQLException {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_PHONE_NUMBER_BY_POSITION_AND_NAME)) {

            statement.setString(1, newPhoneNumber);
            statement.setString(2, position);
            statement.setString(3, personal.getFirstName());
            statement.setString(4, personal.getLastName());
            statement.setString(5, oldPhoneNumber);

            statement.execute();
        }
    }

    @Override
    public void deletePersonalByPositionAndName(String dismissalReason, String position, Personal personal) throws ConnectionDBException, SQLException {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_PERSONAL_BY_POSITION_AND_NAME)) {

            statement.setString(1, personal.getFirstName());
            statement.setString(2, personal.getLastName());
            statement.setString(3, position);

            statement.execute();
        }
    }

    @Override
    public List<Personal> getPersonalByPosition(String position) throws ConnectionDBException, SQLException {
        List<Personal> personal = new ArrayList<Personal>();

        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement statement = connection.prepareStatement(GET_PERSONAL_BY_POSITION)) {

            statement.setString(1, position);

            try (ResultSet queryResult = statement.executeQuery()) {
                while (queryResult.next()) {
                    personal.add(Personal.builder()
                            .id(queryResult.getLong("id"))
                            .firstName(queryResult.getString("first_name"))
                            .lastName(queryResult.getString("last_name"))
                            .patronymic(queryResult.getString("patronymic"))
                            .positionId(queryResult.getLong("position_id"))
                            .build());
                }
            }
        }

        return personal;
    }
}
