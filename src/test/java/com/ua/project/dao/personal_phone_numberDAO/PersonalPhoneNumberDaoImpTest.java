package com.ua.project.dao.personal_phone_numberDAO;

import com.ua.project.model.PersonalPhoneNumber;
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

public class PersonalPhoneNumberDaoImpTest {
    private static final PersonalPhoneNumberDao personalPhoneNumberDao = new PersonalPhoneNumberDaoImp();

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
    void save_ShouldInsertPersonalPhoneNumberIntoTable_WhenCalled() {
        int actualSize;
        int expectedSize;
        List<PersonalPhoneNumber> actualList;
        List<PersonalPhoneNumber> expectedList = getExpectedPersonalPhoneNumbersList();
        PersonalPhoneNumber insertData = PersonalPhoneNumber.builder().id(7L).phoneNumber("0887766555").personalId(3).build();

        expectedList.add(insertData);

        personalPhoneNumberDao.save(insertData);
        actualList = personalPhoneNumberDao.findAll();

        actualSize = actualList.size();
        expectedSize = expectedList.size();

        assertEquals(expectedSize, actualSize);
        assertEquals(expectedList, actualList);
    }

    @Test
    void findAll_ShouldReturnListOfPersonalPhoneNumbers_WhenCalled() {
        List<PersonalPhoneNumber> expectedList = getExpectedPersonalPhoneNumbersList();
        List<PersonalPhoneNumber> actualList = personalPhoneNumberDao.findAll();
        int actualSize = actualList.size();
        int expectedSize = expectedList.size();

        assertEquals(expectedSize, actualSize);
        assertEquals(expectedList, actualList);
    }

    @Test
    void saveMany_ShouldInsertListOfPersonalPhoneNumbersIntoTable_WhenCalled() {
        int actualSize;
        int expectedSize;
        List<PersonalPhoneNumber> actualList;
        List<PersonalPhoneNumber> expectedList = getExpectedPersonalPhoneNumbersList();
        List<PersonalPhoneNumber> insertDataList = new ArrayList<PersonalPhoneNumber>(List.of(
                PersonalPhoneNumber.builder().id(7L).phoneNumber("0887766555").personalId(1).build(),
                PersonalPhoneNumber.builder().id(8L).phoneNumber("+380443322111").personalId(4).build()
        ));

        expectedList.addAll(insertDataList);
        personalPhoneNumberDao.saveMany(insertDataList);
        actualList = personalPhoneNumberDao.findAll();

        actualSize = actualList.size();
        expectedSize = expectedList.size();

        assertEquals(expectedSize, actualSize);
        assertEquals(expectedList, actualList);
    }

    @Test
    void update_ShouldUpdatePersonalPhoneNumberIntoTable_WhenCalled() {
        PersonalPhoneNumber updateData = PersonalPhoneNumber.builder().id(5L).phoneNumber("0998877088").personalId(5).build();
        List<PersonalPhoneNumber> actualList = personalPhoneNumberDao.findAll();
        List<PersonalPhoneNumber> expectedList = getExpectedPersonalPhoneNumbersList();

        assertEquals(expectedList, actualList);
        personalPhoneNumberDao.update(updateData);
        actualList.clear();
        expectedList.remove(4);
        expectedList.add(updateData);
        actualList = personalPhoneNumberDao.findAll();

        assertEquals(expectedList, actualList);
    }

    @Test
    void delete_ShouldDeletePersonalPhoneNumberFromTable_WhenCalled() {
        List<PersonalPhoneNumber> actualList = personalPhoneNumberDao.findAll();
        List<PersonalPhoneNumber> expectedList = getExpectedPersonalPhoneNumbersList();

        assertEquals(expectedList, actualList);
        personalPhoneNumberDao.delete(expectedList.get(1));
        actualList.clear();
        expectedList.remove(1);
        actualList = personalPhoneNumberDao.findAll();

        assertEquals(expectedList, actualList);
    }

    @Test
    void deleteAll_ShouldDeletePersonalEmailAddresses_WhenCalled() {
        int actualSize;
        int expectedSize = 0;
        List<PersonalPhoneNumber> actualList = personalPhoneNumberDao.findAll();
        List<PersonalPhoneNumber> expectedList = getExpectedPersonalPhoneNumbersList();

        assertEquals(expectedList, actualList);
        personalPhoneNumberDao.deleteAll();
        actualList.clear();
        expectedList.clear();
        actualList = personalPhoneNumberDao.findAll();
        actualSize = actualList.size();

        assertEquals(expectedSize, actualSize);
        assertEquals(expectedList, actualList);
    }

    @Test
    void findByPersonalId_ShouldReturnListOfPersonalPhoneNumbers_WhenPersonalIdIsInTheTable() {
        int actualSize;
        int expectedSize;
        List<PersonalPhoneNumber> actualList = personalPhoneNumberDao.findByPersonalId(2);
        List<PersonalPhoneNumber> expectedList = new ArrayList<PersonalPhoneNumber>(List.of(
                PersonalPhoneNumber.builder().id(1L).phoneNumber("+380998877666").personalId(2).build(),
                PersonalPhoneNumber.builder().id(3L).phoneNumber("+380998687666").personalId(2).build()
        ));

        actualSize = actualList.size();
        expectedSize = expectedList.size();

        assertEquals(expectedSize, actualSize);
        assertEquals(expectedList, actualList);
    }

    @Test
    void findByPersonalId_ShouldReturnEmptyListOfPersonalPhoneNumbers_WhenPersonalIdIsNotInTheTable() {
        int actualSize;
        int expectedSize = 0;
        List<PersonalPhoneNumber> actualList = personalPhoneNumberDao.findByPersonalId(8);
        List<PersonalPhoneNumber> expectedList = new ArrayList<PersonalPhoneNumber>();

        actualSize = actualList.size();

        assertEquals(expectedSize, actualSize);
        assertEquals(expectedList, actualList);
    }

    private static List<PersonalPhoneNumber> getExpectedPersonalPhoneNumbersList() {
        return new ArrayList<PersonalPhoneNumber>(List.of(
                PersonalPhoneNumber.builder().id(1L).phoneNumber("+380998877666").personalId(2).build(),
                PersonalPhoneNumber.builder().id(2L).phoneNumber("+380998811666").personalId(1).build(),
                PersonalPhoneNumber.builder().id(3L).phoneNumber("+380998687666").personalId(2).build(),
                PersonalPhoneNumber.builder().id(4L).phoneNumber("+380924877666").personalId(4).build(),
                PersonalPhoneNumber.builder().id(5L).phoneNumber("+380998877086").personalId(5).build(),
                PersonalPhoneNumber.builder().id(6L).phoneNumber("+380998745666").personalId(3).build()
        ));
    }
}
