package com.ua.project.dao.personal_email_addressDAO;

import com.ua.project.model.PersonalEmailAddress;
import com.ua.project.service.CafeDbInitializer;
import com.ua.project.utils.DBTestData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.setProperty;
import static junit.framework.Assert.assertEquals;

public class PersonalEmailAddressDaoImpTest {
    private static final PersonalEmailAddressDao personalEmailAddressDao = new PersonalEmailAddressDaoImp();

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
    void save_ShouldInsertPersonalEmailAddressIntoTable_WhenCalled() {
        int actualSize;
        int expectedSize;
        List<PersonalEmailAddress> actualList;
        List<PersonalEmailAddress> expectedList = getExpectedPersonalEmailAddressesList();
        PersonalEmailAddress insertData = PersonalEmailAddress.builder().id(7L).emailAddress("some_mail@gmail.com").personalId(4).build();

        expectedList.add(insertData);

        personalEmailAddressDao.save(insertData);
        actualList = personalEmailAddressDao.findAll();

        actualSize = actualList.size();
        expectedSize = expectedList.size();

        assertEquals(expectedSize, actualSize);
        assertEquals(expectedList, actualList);
    }

    @Test
    void findAll_ShouldReturnListOfPersonalEmailAddresses_WhenCalled() {
        List<PersonalEmailAddress> expectedList = getExpectedPersonalEmailAddressesList();
        List<PersonalEmailAddress> actualList = personalEmailAddressDao.findAll();
        int actualSize = actualList.size();
        int expectedSize = expectedList.size();

        assertEquals(expectedSize, actualSize);
        assertEquals(expectedList, actualList);
    }

    @Test
    void saveMany_ShouldInsertListOfPersonalEmailAddressesIntoTable_WhenCalled() {
        int actualSize;
        int expectedSize;
        List<PersonalEmailAddress> actualList;
        List<PersonalEmailAddress> expectedList = getExpectedPersonalEmailAddressesList();
        List<PersonalEmailAddress> insertDataList = new ArrayList<PersonalEmailAddress>(List.of(
                PersonalEmailAddress.builder().id(7L).emailAddress("my_mail2@gmail.com").personalId(3).build(),
                PersonalEmailAddress.builder().id(8L).emailAddress("my_mail1@gmail.com").personalId(2).build()
        ));

        expectedList.addAll(insertDataList);
        personalEmailAddressDao.saveMany(insertDataList);
        actualList = personalEmailAddressDao.findAll();

        actualSize = actualList.size();
        expectedSize = expectedList.size();

        assertEquals(expectedSize, actualSize);
        assertEquals(expectedList, actualList);
    }

    @Test
    void update_ShouldUpdatePersonalEmailAddressIntoTable_WhenCalled() {
        PersonalEmailAddress updateData = PersonalEmailAddress.builder().id(4L).emailAddress("my_some_mail@temp.ua").personalId(2).build();
        List<PersonalEmailAddress> actualList = personalEmailAddressDao.findAll();
        List<PersonalEmailAddress> expectedList = getExpectedPersonalEmailAddressesList();

        assertEquals(expectedList, actualList);
        personalEmailAddressDao.update(updateData);
        actualList.clear();
        expectedList.remove(3);
        expectedList.add(updateData);
        actualList = personalEmailAddressDao.findAll();

        assertEquals(expectedList, actualList);
    }

    @Test
    void delete_ShouldDeletePersonalEmailAddressFromTable_WhenCalled() {
        List<PersonalEmailAddress> actualList = personalEmailAddressDao.findAll();
        List<PersonalEmailAddress> expectedList = getExpectedPersonalEmailAddressesList();

        assertEquals(expectedList, actualList);
        personalEmailAddressDao.delete(expectedList.get(1));
        actualList.clear();
        expectedList.remove(1);
        actualList = personalEmailAddressDao.findAll();

        assertEquals(expectedList, actualList);
    }

    @Test
    void deleteAll_ShouldDeletePersonalEmailAddresses_WhenCalled() {
        int actualSize;
        int expectedSize = 0;
        List<PersonalEmailAddress> actualList = personalEmailAddressDao.findAll();
        List<PersonalEmailAddress> expectedList = getExpectedPersonalEmailAddressesList();

        assertEquals(expectedList, actualList);
        personalEmailAddressDao.deleteAll();
        actualList.clear();
        expectedList.clear();
        actualList = personalEmailAddressDao.findAll();
        actualSize = actualList.size();

        assertEquals(expectedSize, actualSize);
        assertEquals(expectedList, actualList);
    }

    @Test
    void findByPersonalId_ShouldReturnListOfPersonalEmailAddresses_WhenPersonalIdIsInTheTable() {
        int actualSize;
        int expectedSize;
        List<PersonalEmailAddress> actualList = personalEmailAddressDao.findByPersonalId(3);
        List<PersonalEmailAddress> expectedList = new ArrayList<PersonalEmailAddress>(List.of(
                PersonalEmailAddress.builder().id(2L).emailAddress("mail2@temp.ua").personalId(3).build(),
                PersonalEmailAddress.builder().id(3L).emailAddress("mail3@temp.org").personalId(3).build()
        ));

        actualSize = actualList.size();
        expectedSize = expectedList.size();

        assertEquals(expectedSize, actualSize);
        assertEquals(expectedList, actualList);
    }

    @Test
    void findByPersonalId_ShouldReturnEmptyListOfPersonalEmailAddresses_WhenPersonalIdIsNotInTheTable() {
        int actualSize;
        int expectedSize = 0;
        List<PersonalEmailAddress> actualList = personalEmailAddressDao.findByPersonalId(8);
        List<PersonalEmailAddress> expectedList = new ArrayList<PersonalEmailAddress>();

        actualSize = actualList.size();

        assertEquals(expectedSize, actualSize);
        assertEquals(expectedList, actualList);
    }

    private static List<PersonalEmailAddress> getExpectedPersonalEmailAddressesList() {
        return new ArrayList<PersonalEmailAddress>(List.of(
                PersonalEmailAddress.builder().id(1L).emailAddress("mail1@temp.com").personalId(1).build(),
                PersonalEmailAddress.builder().id(2L).emailAddress("mail2@temp.ua").personalId(3).build(),
                PersonalEmailAddress.builder().id(3L).emailAddress("mail3@temp.org").personalId(3).build(),
                PersonalEmailAddress.builder().id(4L).emailAddress("mail4@temp.com").personalId(2).build(),
                PersonalEmailAddress.builder().id(5L).emailAddress("mail5@temp.ua").personalId(5).build(),
                PersonalEmailAddress.builder().id(6L).emailAddress("mail6@temp.org.ua").personalId(4).build()
        ));
    }
}
