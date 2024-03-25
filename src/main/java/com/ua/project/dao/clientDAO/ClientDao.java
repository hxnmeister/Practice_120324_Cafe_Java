package com.ua.project.dao.clientDAO;

import com.ua.project.dao.CRUDInterface;
import com.ua.project.exception.ConnectionDBException;
import com.ua.project.model.Client;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface ClientDao extends CRUDInterface<Client> {

    void changeDiscountValueByName(Client client);
    void deleteClientByName(Client client);
    long getIdByName(Client client);
    int getMinDiscount();
    int getMaxDiscount();
    Client getClientWithMinDiscount();
    Client getClientWithMaxDiscount();
    Client findClientWithMaxOrderPriceBySpecificDay(Date date);
    double getAvgDiscount();
    List<Client> getYoungestClients();
    List<Client> getOldestClients();
    List<Client> getClientsWithBirthdayToday();
    List<Client> getClientsWithEmptyEmail();
}
