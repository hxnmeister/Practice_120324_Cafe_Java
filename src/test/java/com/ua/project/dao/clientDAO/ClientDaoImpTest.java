package com.ua.project.dao.clientDAO;

import com.ua.project.dao.assortmentDAO.AssortmentDao;
import com.ua.project.dao.assortmentDAO.AssortmentDaoImp;
import com.ua.project.model.Assortment;
import com.ua.project.model.Client;
import com.ua.project.service.CafeDbInitializer;
import com.ua.project.utils.DBTestData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.setProperty;
import static junit.framework.Assert.assertEquals;

public class ClientDaoImpTest {
    private static final ClientDao clientDao = new ClientDaoImp();

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
    void save_ShouldInsertClientIntoTable_WhenCalled() {
        int actualSize;
        int expectedSize;
        List<Client> actualList;
        List<Client> expectedList = getExpectedClientsList();
        Client insertData = Client.builder()
                .id(5L)
                .firstName("ClientFirstName123")
                .lastName("ClientLastName123")
                .patronymic("ClientPatron321")
                .birthDate(Date.valueOf("2000-05-05"))
                .contactPhone("0998866777")
                .contactEmail("some_mail@gmail.com")
                .discount(32)
                .build();

        expectedList.add(insertData);

        clientDao.save(insertData);
        actualList = clientDao.findAll();

        actualSize = actualList.size();
        expectedSize = expectedList.size();

        assertEquals(expectedSize, actualSize);
        assertEquals(expectedList, actualList);
    }

    @Test
    void findAll_ShouldReturnListOfClients_WhenCalled() {
        List<Client> expectedList = getExpectedClientsList();
        List<Client> actualList = clientDao.findAll();
        int actualSize = actualList.size();
        int expectedSize = expectedList.size();

        assertEquals(expectedSize, actualSize);
        assertEquals(expectedList, actualList);
    }

    @Test
    void saveMany_ShouldInsertListOfClientsIntoTable_WhenCalled() {
        int actualSize;
        int expectedSize;
        List<Client> actualList;
        List<Client> expectedList = getExpectedClientsList();
        List<Client> insertDataList = new ArrayList<Client>(List.of(
                Client.builder().id(5L).firstName("ClientFirstName5").lastName("ClientLastName5").patronymic("ClientPatron5").birthDate(Date.valueOf("2000-05-27"))
                        .contactPhone("+380995419866").contactEmail("clientmail5@sun.org.ua").discount(99).build(),
                Client.builder().id(6L).firstName("ClientFirstName6").lastName("ClientLastName6").patronymic("ClientPatron6").birthDate(Date.valueOf("2000-06-19"))
                        .contactPhone("+380901812366").contactEmail("clientmail5@star.ua").discount(87).build()
        ));

        expectedList.addAll(insertDataList);
        clientDao.saveMany(insertDataList);
        actualList = clientDao.findAll();

        actualSize = actualList.size();
        expectedSize = expectedList.size();

        assertEquals(expectedSize, actualSize);
        assertEquals(expectedList, actualList);
    }

    @Test
    void update_ShouldUpdateClientsIntoTable_WhenCalled() {
        Client updateData = Client.builder().id(2L).firstName("Valerii").lastName("Stronskyi").patronymic("Oleksandrovich").birthDate(Date.valueOf("2005-11-02"))
                .contactPhone("0998877766").contactEmail("some_temp@random.com.org.ua").discount(2).build();
        List<Client> actualList = clientDao.findAll();
        List<Client> expectedList = getExpectedClientsList();

        assertEquals(expectedList, actualList);
        clientDao.update(updateData);
        actualList.clear();
        expectedList.remove(1);
        expectedList.add(updateData);
        actualList = clientDao.findAll();

        assertEquals(expectedList, actualList);
    }

    @Test
    void delete_ShouldDeleteClientFromTable_WhenCalled() {
        List<Client> actualList = clientDao.findAll();
        List<Client> expectedList = getExpectedClientsList();

        assertEquals(expectedList, actualList);
        clientDao.delete(expectedList.get(1));
        actualList.clear();
        expectedList.remove(1);
        actualList = clientDao.findAll();

        assertEquals(expectedList, actualList);
    }

    @Test
    void deleteAll_ShouldDeleteClients_WhenCalled() {
        int actualSize;
        int expectedSize = 0;
        List<Client> actualList = clientDao.findAll();
        List<Client> expectedList = getExpectedClientsList();

        assertEquals(expectedList, actualList);
        clientDao.deleteAll();
        actualList.clear();
        expectedList.clear();
        actualList = clientDao.findAll();
        actualSize = actualList.size();

        assertEquals(expectedSize, actualSize);
        assertEquals(expectedList, actualList);
    }

    @Test
    void changeDiscountValueByName_ShouldUpdateClientDiscountInTheTable_WhenCalled() {
        Client client = Client.builder().id(3L).firstName("ClientFirstName3").lastName("ClientLastName3").patronymic("ClientPatron3").birthDate(Date.valueOf("2000-03-22"))
                .contactPhone("+380998341766").contactEmail("clientmail3@temp.com.ua").discount(45).build();
        List<Client> actualList = clientDao.findAll();
        List<Client> expectedList = getExpectedClientsList();

        assertEquals(expectedList, actualList);
        clientDao.changeDiscountValueByName(client);
        expectedList.remove(2);
        expectedList.add(client);
        actualList.clear();
        actualList = clientDao.findAll();

        assertEquals(expectedList, actualList);
    }

    @Test
    void deleteClientByName_ShouldDeleteClientByNameFromTable_WhenCalled() {
        Client client = Client.builder().id(2L).firstName("ClientFirstName2").lastName("ClientLastName2").patronymic("ClientPatron2").birthDate(Date.valueOf("2000-02-12"))
                .contactPhone("+380901877766").contactEmail("clientmail2@temp.org.ua").discount(0).build();
        List<Client> actualList = clientDao.findAll();
        List<Client> expectedList = getExpectedClientsList();

        assertEquals(expectedList, actualList);
        clientDao.deleteClientByName(client);
        expectedList.remove(client);
        actualList.clear();
        actualList = clientDao.findAll();

        assertEquals(expectedList, actualList);
    }

    private static List<Client> getExpectedClientsList() {
        return new ArrayList<Client>(List.of(
                Client.builder().id(1L).firstName("ClientFirstName1").lastName("ClientLastName1").patronymic("ClientPatron1").birthDate(Date.valueOf("2000-01-02"))
                        .contactPhone("+380998877766").contactEmail("clientmail1@temp.com.org.ua").discount(12).build(),
                Client.builder().id(2L).firstName("ClientFirstName2").lastName("ClientLastName2").patronymic("ClientPatron2").birthDate(Date.valueOf("2000-02-12"))
                        .contactPhone("+380901877766").contactEmail("clientmail2@temp.org.ua").discount(0).build(),
                Client.builder().id(3L).firstName("ClientFirstName3").lastName("ClientLastName3").patronymic("ClientPatron3").birthDate(Date.valueOf("2000-03-22"))
                        .contactPhone("+380998341766").contactEmail("clientmail3@temp.com.ua").discount(5).build(),
                Client.builder().id(4L).firstName("ClientFirstName4").lastName("ClientLastName4").patronymic("ClientPatron4").birthDate(Date.valueOf("2000-04-15"))
                        .contactPhone("+380998107766").contactEmail("clientmail4@temp.ua").discount(7).build()
        ));
    }
}
