package com.ua.project.dao.personal_email_addressDAO;

import com.ua.project.dao.ConnectionFactory;
import com.ua.project.exception.ConnectionDBException;
import com.ua.project.model.Assortment;
import com.ua.project.model.PersonalEmailAddress;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonalEmailAddressDaoImp implements PersonalEmailAddressDao {
    private static final String INSERT_PERSONAL_EMAIL_ADDRESS = """
        INSERT INTO personal_email_addresses(email_address, personal_id)
        VALUES (?, ?)
    """;
    private static final String UPDATE_PERSONAL_EMAIL_ADDRESS = """
        UPDATE personal_email_addresses
        SET email_address=?
        WHERE id=?
    """;
    private static final String DELETE_PERSONAL_EMAIL_ADDRESS = """
        DELETE FROM personal_email_addresses
        WHERE id=?
    """;
    private static final String DELETE_ALL_PERSONAL_EMAIL_ADDRESS = """
        DELETE FROM personal_email_addresses
    """;
    private static final String GET_ALL_PERSONAL_EMAIL_ADDRESS = """
        SELECT *
        FROM personal_email_addresses
    """;

    @Override
    public void save(PersonalEmailAddress item) throws SQLException, ConnectionDBException {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_PERSONAL_EMAIL_ADDRESS)) {

            statement.setString(1, item.getEmailAddress());
            statement.setLong(2, item.getPersonalId());

            statement.execute();
        }
    }

    @Override
    public void saveMany(List<PersonalEmailAddress> items) throws SQLException, ConnectionDBException {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_PERSONAL_EMAIL_ADDRESS)) {

            for (PersonalEmailAddress item : items) {
                statement.setString(1, item.getEmailAddress());
                statement.setLong(2, item.getPersonalId());

                statement.addBatch();
            }

            statement.executeBatch();
        }
    }

    @Override
    public void update(PersonalEmailAddress item) throws SQLException, ConnectionDBException {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_PERSONAL_EMAIL_ADDRESS)) {

            statement.setString(1, item.getEmailAddress());
            statement.setLong(2, item.getId());

            statement.execute();
        }
    }

    @Override
    public void delete(PersonalEmailAddress item) throws SQLException, ConnectionDBException {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_PERSONAL_EMAIL_ADDRESS)) {

            statement.setLong(1, item.getId());
            statement.execute();
        }
    }

    @Override
    public List<PersonalEmailAddress> findAll() throws SQLException, ConnectionDBException {
        List<PersonalEmailAddress> assortment = new ArrayList<PersonalEmailAddress>();

        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             Statement statement = connection.createStatement()) {

            try (ResultSet queryResult = statement.executeQuery(GET_ALL_PERSONAL_EMAIL_ADDRESS)) {
                while (queryResult.next()) {
                    assortment.add(PersonalEmailAddress.builder()
                            .id(queryResult.getLong("id"))
                            .emailAddress(queryResult.getString("email_address"))
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

            statement.execute(DELETE_ALL_PERSONAL_EMAIL_ADDRESS);
        }
    }
}
