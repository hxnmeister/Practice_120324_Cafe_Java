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
    private static final String GET_MIN_DISCOUNT = """
        SELECT MIN(discount)
        FROM clients
    """;
    private static final String GET_MAX_DISCOUNT = """
        SELECT MAX(discount)
        FROM clients
    """;
    private static final String GET_CLIENT_WITH_MAX_DISCOUNT = """
        SELECT *
        FROM clients
        WHERE discount=(SELECT MAX(discount)
                        FROM clients)
    """;
    private static final String GET_CLIENT_WITH_MIN_DISCOUNT = """
        SELECT *
        FROM clients
        WHERE discount=(SELECT MIN(discount)
                        FROM clients)
    """;
    private static final String GET_AVG_DISCOUNT = """
        SELECT AVG(discount)
        FROM clients
    """;
    private static final String GET_YOUNGEST_CLIENTS = """
        SELECT *
        FROM clients
        WHERE birth_date=(SELECT MAX(birth_date)
                          FROM clients)
    """;
    private static final String GET_OLDEST_CLIENTS = """
        SELECT *
        FROM clients
        WHERE birth_date=(SELECT MIN(birth_date)
                          FROM clients)
    """;
    private static final String GET_CLIENTS_WITH_BIRTHDAY_TODAY = """
        SELECT *
        FROM clients
        WHERE EXTRACT(MONTH FROM birth_date) = EXTRACT(MONTH FROM CURRENT_DATE)
              AND EXTRACT(DAY FROM birth_date) = EXTRACT(DAY FROM CURRENT_DATE)
    """;
    private static final String GET_CLIENTS_WITHOUT_EMAIL = """
        SELECT *
        FROM clients
        WHERE contact_email=''
    """;
    private static final String GET_CLIENT_WITH_MAX_ORDER_PRICE_BY_SPECIFIC_DATE = """
        SELECT *
        FROM clients c
        JOIN orders o on c.id=o.client_id
        WHERE DATE(timestamp)=? AND CAST(price AS NUMERIC)=(SELECT MAX(CAST(price AS NUMERIC))
                													   FROM orders
                													   WHERE DATE(timestamp)=?)
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
                    clients.add(getClientFromResultSet(queryResult));
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
                if(queryResult.next()) {
                    return queryResult.getLong("id");
                }
            }
        }
        catch (ConnectionDBException | SQLException e) {
            System.out.println(e.getMessage());
        }

        return 0;
    }

    @Override
    public int getMinDiscount() {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             Statement statement = connection.createStatement()) {

            try (ResultSet queryResult = statement.executeQuery(GET_MIN_DISCOUNT)) {
                if (queryResult.next()) {
                    return queryResult.getInt("min");
                }
            }
        }
        catch (ConnectionDBException | SQLException e) {
            System.out.println(e.getMessage());
        }

        return 0;
    }

    @Override
    public int getMaxDiscount() {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             Statement statement = connection.createStatement()) {

            try (ResultSet queryResult = statement.executeQuery(GET_MAX_DISCOUNT)) {
                if (queryResult.next()) {
                    return queryResult.getInt("max");
                }
            }
        }
        catch (ConnectionDBException | SQLException e) {
            System.out.println(e.getMessage());
        }

        return 0;
    }

    @Override
    public Client getClientWithMinDiscount() {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             Statement statement = connection.createStatement()) {

            try (ResultSet queryResult = statement.executeQuery(GET_CLIENT_WITH_MIN_DISCOUNT)) {
                if (queryResult.next()) {
                    return getClientFromResultSet(queryResult);
                }
            }
        }
        catch (ConnectionDBException | SQLException e) {
            System.out.println(e.getMessage());
        }

        return Client.builder().build();
    }

    @Override
    public Client getClientWithMaxDiscount() {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             Statement statement = connection.createStatement()) {

            try (ResultSet queryResult = statement.executeQuery(GET_CLIENT_WITH_MAX_DISCOUNT)) {
                if (queryResult.next()) {
                    return getClientFromResultSet(queryResult);
                }
            }
        }
        catch (ConnectionDBException | SQLException e) {
            System.out.println(e.getMessage());
        }

        return Client.builder().build();
    }

    @Override
    public Client findClientWithMaxOrderPriceBySpecificDay(Date date) {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement statement = connection.prepareStatement(GET_CLIENT_WITH_MAX_ORDER_PRICE_BY_SPECIFIC_DATE)) {

            statement.setDate(1, date);
            statement.setDate(2, date);

            try (ResultSet queryResult = statement.executeQuery()) {
                if(queryResult.next()) {
                    return getClientFromResultSet(queryResult);
                }
            }
        }
        catch (ConnectionDBException | SQLException e) {
            System.out.println(e.getMessage());
        }

        return Client.builder().build();
    }

    @Override
    public double getAvgDiscount() {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             Statement statement = connection.createStatement()) {

            try (ResultSet queryResult = statement.executeQuery(GET_AVG_DISCOUNT)) {
                if (queryResult.next()) {
                    return queryResult.getDouble("avg");
                }
            }
        }
        catch (ConnectionDBException | SQLException e) {
            System.out.println(e.getMessage());
        }

        return 0;
    }

    @Override
    public List<Client> getYoungestClients() {
        List<Client> youngestClients = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             Statement statement = connection.createStatement()) {

            try (ResultSet queryResult = statement.executeQuery(GET_YOUNGEST_CLIENTS)) {
                while (queryResult.next()) {
                    youngestClients.add(getClientFromResultSet(queryResult));
                }
            }
        }
        catch (ConnectionDBException | SQLException e) {
            System.out.println(e.getMessage());
        }

        return youngestClients;
    }

    @Override
    public List<Client> getOldestClients() {
        List<Client> oldestClients = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             Statement statement = connection.createStatement()) {

            try (ResultSet queryResult = statement.executeQuery(GET_OLDEST_CLIENTS)) {
                while (queryResult.next()) {
                    oldestClients.add(getClientFromResultSet(queryResult));
                }
            }
        }
        catch (ConnectionDBException | SQLException e) {
            System.out.println(e.getMessage());
        }

        return oldestClients;
    }

    @Override
    public List<Client> getClientsWithBirthdayToday() {
        List<Client> birthdayToday = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             Statement statement = connection.createStatement()) {

            try (ResultSet queryResult = statement.executeQuery(GET_CLIENTS_WITH_BIRTHDAY_TODAY)) {
                while (queryResult.next()) {
                    birthdayToday.add(getClientFromResultSet(queryResult));
                }
            }
        }
        catch (ConnectionDBException | SQLException e) {
            System.out.println(e.getMessage());
        }

        return birthdayToday;
    }

    @Override
    public List<Client> getClientsWithEmptyEmail() {
        List<Client> withoutEmail = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             Statement statement = connection.createStatement()) {

            try (ResultSet queryResult = statement.executeQuery(GET_CLIENTS_WITHOUT_EMAIL)) {
                while (queryResult.next()) {
                    withoutEmail.add(getClientFromResultSet(queryResult));
                }
            }
        }
        catch (ConnectionDBException | SQLException e) {
            System.out.println(e.getMessage());
        }

        return withoutEmail;
    }

    private Client getClientFromResultSet(ResultSet resultSet) throws SQLException {
        return Client.builder()
                .id(resultSet.getLong("id"))
                .firstName(resultSet.getString("first_name"))
                .lastName(resultSet.getString("last_name"))
                .patronymic(resultSet.getString("patronymic"))
                .birthDate(resultSet.getDate("birth_date"))
                .contactPhone(resultSet.getString("contact_phone"))
                .contactEmail(resultSet.getString("contact_email"))
                .discount(resultSet.getInt("discount"))
                .build();
    }
}
