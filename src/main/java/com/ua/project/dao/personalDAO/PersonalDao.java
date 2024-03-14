package com.ua.project.dao.personalDAO;

import com.ua.project.dao.CRUDInterface;
import com.ua.project.exception.ConnectionDBException;
import com.ua.project.model.Personal;

import java.sql.SQLException;
import java.util.List;

public interface PersonalDao extends CRUDInterface<Personal> {

    void changeEmailAddressByPositionAndName(String newEmailAddress, String oldEmailAddress, String position, Personal personal) throws ConnectionDBException, SQLException;
    void changePhoneNumberByPositionAndName(String newPhoneNumber, String oldPhoneNumber, String position, Personal personal) throws ConnectionDBException, SQLException;
    void deletePersonalByPositionAndName(String dismissalReason, String position, Personal personal) throws ConnectionDBException, SQLException;
    List<Personal> getPersonalByPosition(String position) throws ConnectionDBException, SQLException;
}
