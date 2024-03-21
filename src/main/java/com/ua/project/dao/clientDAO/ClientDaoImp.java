package com.ua.project.dao.clientDAO;

import com.ua.project.dao.ConnectionFactory;
import com.ua.project.exception.ConnectionDBException;
import com.ua.project.model.Client;
import com.ua.project.model.Personal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDaoImp implements ClientDao {
    private static final String INSERT_CLIENT = """
        INSERT INTO clients(first_name, last_name, patronymic, birth_date, contact_phone, contact_email, discount)
        VALUES (?, ?, ?, ?, ?, ?, ?)
    """;
    private static final String UPDATE_CLIENTS = """
        UPDATE clients
        SET first_name=?, last_name=?, patronymic=?, birth_date=?, contact_phone=?, contact_email=?, discount=?
        WHERE id=?
    """;
    private static final String DELETE_CLIENT = """
        DELETE FROM clients
        WHERE id=?
    """;
    private static final String DELETE_ALL_CLIENTS = """
        DELETE FROM clients
    """;
    private static final String GET_ALL_CLIENTS = """
        SELECT *
        FROM clients
    """;
    private static final String UPDATE_DISCOUNT_VALUE_BY_NAME = """
        UPDATE clients
        SET discount=?
        WHERE first_name=? AND last_name=?
    """;
    private static final String DELETE_CLIENT_BY_NAME = """
        DELETE FROM clients
        WHERE first_name=? AND last_name=?
    """;
    private static final String GET_CLIENT_ID_BY_NAME = """
        SELECT id
        FROM clients
        WHERE first_name=? AND last_name=?
        LIMIT 1
    """;

    @Override
    public void save(Client item) {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_CLIENT)) {

            statement.setString(1, item.getFirstName());
            statement.setString(2, item.getLastName());
            statement.setString(3, item.getPatronymic());
            statement.setDate(4, item.getBirthDate());
            statement.setString(5, item.getContactPhone());
            statement.setString(6, item.getContactEmail());
            statement.setInt(7, item.getDiscount());

            statement.execute();
        }
        catch (ConnectionDBException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void saveMany(List<Client> items) {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_CLIENT)) {

            for (Client item : items) {
                statement.setString(1, item.getFirstName());
                statement.setString(2, item.getLastName());
                statement.setString(3, item.getPatronymic());
                statement.setDate(4, item.getBirthDate());
                statement.setString(5, item.getContactPhone());
                statement.setString(6, item.getContactEmail());
                statement.setInt(7, item.getDiscount());

                statement.addBatch();
            }

            statement.executeBatch();
        }
        catch (ConnectionDBException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Client item) {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_CLIENTS)) {

            statement.setString(1, item.getFirstName());
            statement.setString(2, item.getLastName());
            statement.setString(3, item.getPatronymic());
            statement.setDate(4, item.getBirthDate());
            statement.setString(5, item.getContactPhone());
            statement.setString(6, item.getContactEmail());
            statement.setInt(7, item.getDiscount());
            statement.setLong(8, item.getId());

            statement.execute();
        }
        catch (ConnectionDBException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Client item) {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_CLIENT)) {

            statement.setLong(1, item.getId());
            statement.execute();
        }
        catch (ConnectionDBException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Client> findAll() {
        List<Client> clients = new ArrayList<Client>();

        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             Statement statement = connection.createStatement()) {

            try (ResultSet queryResult = statement.executeQuery(GET_ALL_CLIENTS)) {
                while (queryResult.next()) {
                    clients.add(Client.builder()
                            .id(queryResult.getLong("id"))
                            .firstName(queryResult.getString("first_name"))
                            .lastName(queryResult.getString("last_name"))
                            .patronymic(queryResult.getString("patronymic"))
                            .birthDate(queryResult.getDate("birth_date"))
                            .contactPhone(queryResult.getString("contact_phone"))
                            .contactEmail(queryResult.getString("contact_email"))
                            .discount(queryResult.getInt("discount"))
                            .build());
                }
            }
        }
        catch (ConnectionDBException | SQLException e) {
            System.out.println(e.getMessage());
        }

        return clients;
    }

    @Override
    public void deleteAll() {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(DELETE_ALL_CLIENTS);
        }
        catch (ConnectionDBException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void changeDiscountValueByName(Client client) {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_DISCOUNT_VALUE_BY_NAME)) {

            statement.setInt(1, client.getDiscount());
            statement.setString(2, client.getFirstName());
            statement.setString(3, client.getLastName());

            statement.execute();
        }
        catch (ConnectionDBException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteClientByName(Client client) {
        try(Connection connection = ConnectionFactory.getInstance().makeConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_CLIENT_BY_NAME)) {

            statement.setString(1, client.getFirstName());
            statement.setString(2, client.getLastName());

            statement.execute();
        }
        catch (ConnectionDBException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public long getIdByName(Client client) {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement statement = connection.prepareStatement(GET_CLIENT_ID_BY_NAME)) {

            statement.setString(1, client.getFirstName());
            statement.setString(2, client.getLastName());

            try (ResultSet queryResult = statement.executeQuery()) {
                queryResult.next();

                return queryResult.getLong("id");
            }
        }
        catch (ConnectionDBException | SQLException e) {
            System.out.println(e.getMessage());
        }

        return 0;
    }
}
