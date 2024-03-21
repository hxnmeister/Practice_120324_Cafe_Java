package com.ua.project.service.business.client;

import com.ua.project.dao.clientDAO.ClientDao;
import com.ua.project.dao.clientDAO.ClientDaoImp;
import com.ua.project.model.Client;
import com.ua.project.service.CafeDbInitializer;
import com.ua.project.utils.DBTestData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.setProperty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ClientServiceImpTest {
    @BeforeAll
    static void initCafeTestDB() {
        setProperty("test", "true");
        CafeDbInitializer.createTables();
    }

    @BeforeEach
    void initData() {
        DBTestData.insert();
    }

    @AfterEach
    void deleteData() {
        CafeDbInitializer.createTables();
    }

    @Test
    void getAllClients_ShouldReturnStringWithFormattedTextToShow_WhenCalled() {
        List<Client> clients = new ArrayList<>(List.of(
                Client.builder().firstName("FirstName1").lastName("LastName1").patronymic("Patron1").birthDate(Date.valueOf("2000-01-02")).contactPhone("0998877666").contactEmail("some_mail@gmail.com").discount(1).build(),
                Client.builder().firstName("FirstName2").lastName("LastName2").patronymic("Patron2").birthDate(Date.valueOf("2000-02-05")).contactPhone("0998877666").contactEmail("some_mail@gmail.com").discount(5).build(),
                Client.builder().firstName("FirstName3").lastName("LastName3").patronymic("Patron3").birthDate(Date.valueOf("2000-03-07")).contactPhone("0998877666").contactEmail("some_mail@gmail.com").discount(10).build()
        ));
        StringBuilder actual = new StringBuilder();
        StringBuilder expected = new StringBuilder();
        ClientDao clientDao = Mockito.mock(ClientDaoImp.class);
        when(clientDao.findAll()).thenReturn(clients);

        expected.append("\n All clients:\n");
        clients.forEach((client) -> expected.append("\n  ")
                .append(client.getFirstName())
                .append(" ")
                .append(client.getLastName())
                .append(" ")
                .append(client.getPatronymic())
                .append("\n  Birthday: ")
                .append(client.getBirthDate())
                .append("\n  Contact phone: ")
                .append(client.getContactPhone())
                .append("\n  Contact email: ")
                .append(client.getContactEmail())
                .append("\n ")
                .append("-".repeat(40)));

        actual.append("\n All clients:\n");
        clientDao.findAll().forEach((client) -> actual.append("\n  ")
                .append(client.getFirstName())
                .append(" ")
                .append(client.getLastName())
                .append(" ")
                .append(client.getPatronymic())
                .append("\n  Birthday: ")
                .append(client.getBirthDate())
                .append("\n  Contact phone: ")
                .append(client.getContactPhone())
                .append("\n  Contact email: ")
                .append(client.getContactEmail())
                .append("\n ")
                .append("-".repeat(40)));

        assertEquals(expected.toString(), actual.toString());
    }
}
