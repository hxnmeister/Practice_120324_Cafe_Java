package com.ua.project.service.business.personal;

import com.ua.project.dao.personalDAO.PersonalDao;
import com.ua.project.dao.personalDAO.PersonalDaoImp;
import com.ua.project.dao.personal_email_addressDAO.PersonalEmailAddressDao;
import com.ua.project.dao.personal_email_addressDAO.PersonalEmailAddressDaoImp;
import com.ua.project.dao.personal_phone_numberDAO.PersonalPhoneNumberDao;
import com.ua.project.dao.personal_phone_numberDAO.PersonalPhoneNumberDaoImp;
import com.ua.project.dao.positionDAO.PositionDao;
import com.ua.project.dao.positionDAO.PositionDaoImp;
import com.ua.project.model.Personal;
import com.ua.project.model.PersonalEmailAddress;
import com.ua.project.model.PersonalPhoneNumber;

import java.util.List;
import java.util.Scanner;

public class PersonalServiceImp implements PersonalService {
    private static final PersonalDao personalDao = new PersonalDaoImp();

    @Override
    public void addPersonal(Personal personal, String position) {
        PositionDao positionDao = new PositionDaoImp();
        PersonalDao personalDao = new PersonalDaoImp();

        personalDao.save(Personal.builder()
                .firstName(personal.getFirstName())
                .lastName(personal.getLastName())
                .patronymic(personal.getPatronymic())
                .positionId(positionDao.getPositionByTitle(position).getId())
                .build());
    }

    @Override
    public String getPersonalByPosition(String position) {
        StringBuilder personalByPosition = new StringBuilder();
        PersonalDao personalDao = new PersonalDaoImp();
        PersonalPhoneNumberDao personalPhoneNumberDao = new PersonalPhoneNumberDaoImp();
        PersonalEmailAddressDao personalEmailAddressDao = new PersonalEmailAddressDaoImp();
        List<Personal> personalList = personalDao.getPersonalByPosition(position.toLowerCase());

        personalByPosition.append("\n All personal by position ").append(position.toLowerCase()).append(":\n");

        for (Personal personal : personalList) {
            personalByPosition.append("\n").append(personal.getFirstName()).append(" ").append(personal.getLastName()).append(" ").append(personal.getPatronymic()).append("\n  Phone numbers and emails:\n");

            for (PersonalPhoneNumber personalPhoneNumber : personalPhoneNumberDao.findByPersonalId(personal.getId())) {
                personalByPosition.append(personalPhoneNumber.getPhoneNumber()).append(" ");
            }

            for (PersonalEmailAddress personalEmailAddress : personalEmailAddressDao.findByPersonalId(personal.getId())) {
                personalByPosition.append(personalEmailAddress.getEmailAddress()).append(" ");
            }

            personalByPosition.append("\n").append("-".repeat(25));
        }

        return personalByPosition.toString();
    }
}
