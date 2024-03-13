package com.ua.project.dao.clientDAO;

import com.ua.project.dao.CRUDInterface;
import com.ua.project.exception.ConnectionDBException;
import com.ua.project.model.Client;

import java.sql.SQLException;

public interface ClientDao extends CRUDInterface<Client> {

    void changeDiscountValueByName(Client client) throws ConnectionDBException, SQLException;
    void deleteClientByName(Client client) throws ConnectionDBException, SQLException;
}
