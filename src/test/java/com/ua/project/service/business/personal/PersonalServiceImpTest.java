package com.ua.project.service.business.personal;

import com.ua.project.dao.assortmentDAO.AssortmentDao;
import com.ua.project.dao.assortmentDAO.AssortmentDaoImp;
import com.ua.project.dao.personalDAO.PersonalDao;
import com.ua.project.dao.personalDAO.PersonalDaoImp;
import com.ua.project.dao.personal_email_addressDAO.PersonalEmailAddressDao;
import com.ua.project.dao.personal_email_addressDAO.PersonalEmailAddressDaoImp;
import com.ua.project.dao.personal_phone_numberDAO.PersonalPhoneNumberDao;
import com.ua.project.dao.personal_phone_numberDAO.PersonalPhoneNumberDaoImp;
import com.ua.project.model.Assortment;
import com.ua.project.model.Personal;
import com.ua.project.model.PersonalEmailAddress;
import com.ua.project.model.PersonalPhoneNumber;
import com.ua.project.service.CafeDbInitializer;
import com.ua.project.utils.DBTestData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.setProperty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class PersonalServiceImpTest {
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
    void getPersonalByPosition_ShouldReturnStringWithFormattedTextToShow_WhenCalled() {
        List<Personal> personalList = new ArrayList<>(List.of(
                Personal.builder().firstName("FirstName1").lastName("LastName1").patronymic("Patron1").build(),
                Personal.builder().firstName("FirstName2").lastName("LastName2").patronymic("Patron2").build(),
                Personal.builder().firstName("FirstName3").lastName("LastName3").patronymic("Patron3").build()
        ));
        List<PersonalPhoneNumber> phoneNumbers = new ArrayList<>(List.of(
                PersonalPhoneNumber.builder().phoneNumber("0998877666").build(),
                PersonalPhoneNumber.builder().phoneNumber("0998855566").build()
        ));
        List<PersonalEmailAddress> emailAddresses = new ArrayList<>(List.of(
                PersonalEmailAddress.builder().emailAddress("email1@gmail.com").build(),
                PersonalEmailAddress.builder().emailAddress("email2@gmail.com").build()
        ));

        String actual;
        String expected;
        PersonalDao personalDao = Mockito.mock(PersonalDaoImp.class);
        PersonalPhoneNumberDao personalPhoneNumberDao = Mockito.mock(PersonalPhoneNumberDaoImp.class);
        PersonalEmailAddressDao personalEmailAddressDao = Mockito.mock(PersonalEmailAddressDaoImp.class);
        when(personalDao.getPersonalByPosition("waiter")).thenReturn(personalList);
        when(personalPhoneNumberDao.findByPersonalId(1)).thenReturn(phoneNumbers);
        when(personalEmailAddressDao.findByPersonalId(1)).thenReturn(emailAddresses);

        actual = getFormattedLineFromList(personalList, emailAddresses, phoneNumbers, "waiter");
        expected = getFormattedLineFromList(personalDao.getPersonalByPosition("waiter"), personalEmailAddressDao.findByPersonalId(1), personalPhoneNumberDao.findByPersonalId(1), "waiter");

        assertEquals(expected, actual);
    }

    private static String getFormattedLineFromList(List<Personal> personalList, List<PersonalEmailAddress> emailAddresses, List<PersonalPhoneNumber> personalPhoneNumbers, String position) {
        StringBuilder builder = new StringBuilder();

        builder.append("\n All personal by position ").append(position.toLowerCase()).append(":\n");

        for (Personal personal : personalList) {
            builder.append("\n id:").append(personal.getId()).append("| ").append(personal.getFirstName()).append(" ").append(personal.getLastName()).append(" ").append(personal.getPatronymic()).append("\n  Phone numbers and emails:\n");

            for (PersonalPhoneNumber personalPhoneNumber : personalPhoneNumbers) {
                builder.append(personalPhoneNumber.getPhoneNumber()).append(" ");
            }

            for (PersonalEmailAddress personalEmailAddress : emailAddresses) {
                builder.append(personalEmailAddress.getEmailAddress()).append(" ");
            }

            builder.append("\n").append("-".repeat(25));
        }

        return builder.toString();
    }
}
