package com.ua.project.service.business.client;

import com.ua.project.dao.clientDAO.ClientDao;
import com.ua.project.dao.clientDAO.ClientDaoImp;
import com.ua.project.model.Client;

import java.util.List;

public class ClientServiceImp implements ClientService {
    @Override
    public String getAllClients() {
        StringBuilder clientsBuilder = new StringBuilder();
        ClientDao clientDao = new ClientDaoImp();
        List<Client> clients = clientDao.findAll();

        System.out.println("\n All clients:\n");
        clients.forEach((client) -> clientsBuilder
                .append("  ")
                .append(client.getFirstName())
                .append(" ")
                .append(client.getLastName())
                .append(" ")
                .append(client.getPatronymic())
                .append("\n  Birthday: ")
                .append(client.getBirthDate())
                .append("\n  Contact phone: ")
                .append(client.getContactPhone())
                .append("\n  Contact email: ")
                .append(client.getContactEmail())
                .append("\n ")
                .append("-".repeat(40)));

        return clientsBuilder.toString();
    }
}
