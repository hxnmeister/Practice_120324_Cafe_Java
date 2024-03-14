package com.ua.project.dao.personal_email_addressDAO;

import com.ua.project.dao.CRUDInterface;
import com.ua.project.exception.ConnectionDBException;
import com.ua.project.model.PersonalEmailAddress;

import java.sql.SQLException;
import java.util.List;

public interface PersonalEmailAddressDao extends CRUDInterface<PersonalEmailAddress> {
    List<PersonalEmailAddress> findByPersonalId(long personalId) throws ConnectionDBException, SQLException;
}
