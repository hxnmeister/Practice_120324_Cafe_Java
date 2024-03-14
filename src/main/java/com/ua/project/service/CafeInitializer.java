package com.ua.project.service;

import com.ua.project.exception.ConnectionDBException;
import com.ua.project.exception.FileException;

import java.sql.SQLException;

import static java.lang.System.out;
import static java.lang.System.setProperty;

public class CafeInitializer {
    public void cafeInit() {
        setProperty("test", "false");

        try {
            CafeDbInitializer.deleteAllRowsInDB();
            CafeDbInitializer.createAssortmentTypes();
            CafeDbInitializer.createPositions();
            CafeDbInitializer.createRandomAssortment();
            CafeDbInitializer.createRandomClients();
            CafeDbInitializer.createRandomPersonal();
            CafeDbInitializer.createRandomPersonalEmailAddresses();
            CafeDbInitializer.createRandomPersonalPhoneNumber();
        }
        catch (ConnectionDBException e) {
            System.out.println(" During connection to DB an exception occurred!");
            e.printStackTrace();
        }
        catch (SQLException | FileException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
