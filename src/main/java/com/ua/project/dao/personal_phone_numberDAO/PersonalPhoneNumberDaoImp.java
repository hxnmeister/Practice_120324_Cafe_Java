package com.ua.project.dao.personal_phone_numberDAO;

import com.ua.project.dao.ConnectionFactory;
import com.ua.project.exception.ConnectionDBException;
import com.ua.project.model.PersonalEmailAddress;
import com.ua.project.model.PersonalPhoneNumber;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonalPhoneNumberDaoImp implements PersonalPhoneNumberDao{
    private static final String INSERT_PERSONAL_PHONE_NUMBER = """
        INSERT INTO personal_phone_numbers(phone_number, personal_id)
        VALUES (?, ?)
    """;
    private static final String UPDATE_PERSONAL_PHONE_NUMBER = """
        UPDATE personal_phone_numbers
        SET phone_number=?
        WHERE id=?
    """;
    private static final String DELETE_PERSONAL_PHONE_NUMBER = """
        DELETE FROM personal_phone_numbers
        WHERE id=?
    """;
    private static final String DELETE_ALL_PERSONAL_PHONE_NUMBER = """
        DELETE FROM personal_phone_numbers
    """;
    private static final String GET_ALL_PERSONAL_PHONE_NUMBER = """
        SELECT *
        FROM personal_phone_numbers
    """;

    @Override
    public void save(PersonalPhoneNumber item) throws SQLException, ConnectionDBException {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_PERSONAL_PHONE_NUMBER)) {

            statement.setString(1, item.getPhoneNumber());
            statement.setLong(2, item.getPersonalId());

            statement.execute();
        }
    }

    @Override
    public void saveMany(List<PersonalPhoneNumber> items) throws SQLException, ConnectionDBException {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_PERSONAL_PHONE_NUMBER)) {

            for (PersonalPhoneNumber item : items) {
                statement.setString(1, item.getPhoneNumber());
                statement.setLong(2, item.getPersonalId());

                statement.addBatch();
            }

            statement.executeBatch();
        }
    }

    @Override
    public void update(PersonalPhoneNumber item) throws SQLException, ConnectionDBException {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_PERSONAL_PHONE_NUMBER)) {

            statement.setString(1, item.getPhoneNumber());
            statement.setLong(2, item.getId());

            statement.execute();
        }
    }

    @Override
    public void delete(PersonalPhoneNumber item) throws SQLException, ConnectionDBException {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_PERSONAL_PHONE_NUMBER)) {

            statement.setLong(1, item.getId());
            statement.execute();
        }
    }

    @Override
    public List<PersonalPhoneNumber> findAll() throws SQLException, ConnectionDBException {
        List<PersonalPhoneNumber> assortment = new ArrayList<PersonalPhoneNumber>();

        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             Statement statement = connection.createStatement()) {

            try (ResultSet queryResult = statement.executeQuery(GET_ALL_PERSONAL_PHONE_NUMBER)) {
                while (queryResult.next()) {
                    assortment.add(PersonalPhoneNumber.builder()
                            .id(queryResult.getLong("id"))
                            .phoneNumber(queryResult.getString("phone_address"))
                            .personalId(queryResult.getLong("personal_id"))
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

            statement.execute(DELETE_ALL_PERSONAL_PHONE_NUMBER);
        }
    }
}
