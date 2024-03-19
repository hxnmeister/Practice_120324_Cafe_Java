package com.ua.project.dao.personalDAO;

import com.ua.project.dao.personal_email_addressDAO.PersonalEmailAddressDao;
import com.ua.project.dao.personal_email_addressDAO.PersonalEmailAddressDaoImp;
import com.ua.project.dao.personal_phone_numberDAO.PersonalPhoneNumberDao;
import com.ua.project.dao.personal_phone_numberDAO.PersonalPhoneNumberDaoImp;
import com.ua.project.model.Personal;
import com.ua.project.model.PersonalEmailAddress;
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

public class PersonalDaoImpTest {
    private static final PersonalDao personalDao = new PersonalDaoImp();

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
    void save_ShouldInsertPersonalIntoTable_WhenCalled() {
        int actualSize;
        int expectedSize;
        List<Personal> actualList;
        List<Personal> expectedList = getExpectedPersonalList();
        Personal insertData = Personal.builder().id(6L).firstName("FirstName11").lastName("LastName11").patronymic("Patron11").positionId(3).build();

        expectedList.add(insertData);

        personalDao.save(insertData);
        actualList = personalDao.findAll();

        actualSize = actualList.size();
        expectedSize = expectedList.size();

        assertEquals(expectedSize, actualSize);
        assertEquals(expectedList, actualList);
    }

    @Test
    void findAll_ShouldReturnListOfPersonal_WhenCalled() {
        List<Personal> expectedList = getExpectedPersonalList();
        List<Personal> actualList = personalDao.findAll();
        int actualSize = actualList.size();
        int expectedSize = expectedList.size();

        assertEquals(expectedSize, actualSize);
        assertEquals(expectedList, actualList);
    }

    @Test
    void saveMany_ShouldInsertListOfPersonalIntoTable_WhenCalled() {
        int actualSize;
        int expectedSize;
        List<Personal> actualList;
        List<Personal> expectedList = getExpectedPersonalList();
        List<Personal> insertDataList = new ArrayList<Personal>(List.of(
                Personal.builder().id(6L).firstName("FirstName11").lastName("LastName11").patronymic("Patron11").positionId(3).build(),
                Personal.builder().id(7L).firstName("FirstName22").lastName("LastName22").patronymic("Patron22").positionId(1).build()
        ));

        expectedList.addAll(insertDataList);
        personalDao.saveMany(insertDataList);
        actualList = personalDao.findAll();

        actualSize = actualList.size();
        expectedSize = expectedList.size();

        assertEquals(expectedSize, actualSize);
        assertEquals(expectedList, actualList);
    }

    @Test
    void update_ShouldUpdatePersonalIntoTable_WhenCalled() {
        Personal updateData = Personal.builder().id(2L).firstName("Nikolai").lastName("Sidorov").patronymic("Olekseevich").positionId(1).build();
        List<Personal> actualList = personalDao.findAll();
        List<Personal> expectedList = getExpectedPersonalList();

        assertEquals(expectedList, actualList);
        personalDao.update(updateData);
        actualList.clear();
        expectedList.remove(1);
        expectedList.add(updateData);
        actualList = personalDao.findAll();

        assertEquals(expectedList, actualList);
    }

    @Test
    void delete_ShouldDeletePersonalFromTable_WhenCalled() {
        List<Personal> actualList = personalDao.findAll();
        List<Personal> expectedList = getExpectedPersonalList();

        assertEquals(expectedList, actualList);
        personalDao.delete(expectedList.get(1));
        actualList.clear();
        expectedList.remove(1);
        actualList = personalDao.findAll();

        assertEquals(expectedList, actualList);
    }

    @Test
    void deleteAll_ShouldDeletePersonal_WhenCalled() {
        int actualSize;
        int expectedSize = 0;
        List<Personal> actualList = personalDao.findAll();
        List<Personal> expectedList = getExpectedPersonalList();

        assertEquals(expectedList, actualList);
        personalDao.deleteAll();
        actualList.clear();
        expectedList.clear();
        actualList = personalDao.findAll();
        actualSize = actualList.size();

        assertEquals(expectedSize, actualSize);
        assertEquals(expectedList, actualList);
    }

    @Test
    void changeEmailAddressByPositionAndName_ShouldChangePersonalEmailAddressByPositionAndName_WhenCalled() {
        String newEmailAddress = "some_mail@gmail.com";
        PersonalEmailAddressDao personalEmailAddressDao = new PersonalEmailAddressDaoImp();
        Personal personal = Personal.builder().id(3L).firstName("FirstName3").lastName("LastName3").patronymic("Patronymic3").positionId(3).build();
        personalDao.changeEmailAddressByPositionAndName(newEmailAddress, "mail2@temp.ua", "barista", personal);

        PersonalEmailAddress actual = new PersonalEmailAddress();
        for (PersonalEmailAddress item : personalEmailAddressDao.findAll()) {
            if (item.getEmailAddress().equals(newEmailAddress)) {
                actual = item;
                break;
            }
        }

        PersonalEmailAddress expected = PersonalEmailAddress.builder().id(2L).emailAddress("some_mail@gmail.com").personalId(3).build();

        assertEquals(expected, actual);
    }

    @Test
    void changePhoneNumberByPositionAndName_ShouldChangePersonalPhoneNumberByPositionAndName_WhenCalled() {
        String newPhoneNumber = "0887766555";
        PersonalPhoneNumberDao personalPhoneNumberDao = new PersonalPhoneNumberDaoImp();
        Personal personal = Personal.builder().id(2L).firstName("FirstName2").lastName("LastName2").patronymic("Patronymic2").positionId(1).build();
        personalDao.changePhoneNumberByPositionAndName(newPhoneNumber, "+380998687666", "waiter", personal);

        PersonalPhoneNumber actual = new PersonalPhoneNumber();
        for (PersonalPhoneNumber item : personalPhoneNumberDao.findAll()) {
            if (item.getPhoneNumber().equals(newPhoneNumber)) {
                actual = item;
                break;
            }
        }

        PersonalPhoneNumber expected = PersonalPhoneNumber.builder().id(3L).phoneNumber("0887766555").personalId(2).build();

        assertEquals(expected, actual);
    }

    @Test
    void deletePersonalByPositionAndName_ShouldDeletePersonalByPositionAndName_WhenCalled() {
        int actualSize;
        int expectedSize;
        Personal personal = Personal.builder().id(3L).firstName("FirstName3").lastName("LastName3").patronymic("Patronymic3").positionId(3).build();
        List<Personal> actualList;
        List<Personal> expectedList = getExpectedPersonalList();

        personalDao.deletePersonalByPositionAndName("", "barista", personal);
        actualList = personalDao.findAll();
        expectedList.remove(2);

        actualSize = actualList.size();
        expectedSize = expectedList.size();

        assertEquals(expectedSize, actualSize);
        assertEquals(expectedList, actualList);
    }

    @Test
    void getPersonalByPosition_ShouldReturnListOfPersonal_WhenPositionIsInTheTable() {
        int actualSize;
        int expectedSize;
        List<Personal> actualList = personalDao.getPersonalByPosition("waiter");
        List<Personal> expectedList = new ArrayList<Personal>(List.of(
                Personal.builder().id(2L).firstName("FirstName2").lastName("LastName2").patronymic("Patronymic2").positionId(1).build(),
                Personal.builder().id(4L).firstName("FirstName4").lastName("LastName4").patronymic("Patronymic4").positionId(1).build()
        ));

        actualSize = actualList.size();
        expectedSize = expectedList.size();

        assertEquals(expectedSize, actualSize);
        assertEquals(expectedList, actualList);
    }

    private static List<Personal> getExpectedPersonalList() {
        return new ArrayList<Personal>(List.of(
                Personal.builder().id(1L).firstName("FirstName1").lastName("LastName1").patronymic("Patronymic1").positionId(2).build(),
                Personal.builder().id(2L).firstName("FirstName2").lastName("LastName2").patronymic("Patronymic2").positionId(1).build(),
                Personal.builder().id(3L).firstName("FirstName3").lastName("LastName3").patronymic("Patronymic3").positionId(3).build(),
                Personal.builder().id(4L).firstName("FirstName4").lastName("LastName4").patronymic("Patronymic4").positionId(1).build(),
                Personal.builder().id(5L).firstName("FirstName5").lastName("LastName5").patronymic("Patronymic5").positionId(2).build()
        ));
    }
}
