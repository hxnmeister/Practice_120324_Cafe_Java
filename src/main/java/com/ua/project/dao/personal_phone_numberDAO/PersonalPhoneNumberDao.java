package com.ua.project.dao.personal_phone_numberDAO;

import com.ua.project.dao.CRUDInterface;
import com.ua.project.exception.ConnectionDBException;
import com.ua.project.model.PersonalPhoneNumber;

import java.sql.SQLException;
import java.util.List;

public interface PersonalPhoneNumberDao extends CRUDInterface<PersonalPhoneNumber> {
    List<PersonalPhoneNumber> findByPersonalId(long personalId) throws ConnectionDBException, SQLException;
}
