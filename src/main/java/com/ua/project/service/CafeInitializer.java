package com.ua.project.service;

import com.ua.project.exception.ConnectionDBException;
import com.ua.project.exception.FileException;

import java.sql.SQLException;

import static java.lang.System.out;
import static java.lang.System.setProperty;

public class CafeInitializer {
    public void cafeInit() {
        setProperty("test", "false");

        CafeDbInitializer.dropAllTablesInDB();
        CafeDbInitializer.createTables();
        CafeDbInitializer.createAssortmentTypes();
        CafeDbInitializer.createPositions();
        CafeDbInitializer.createRandomAssortment();
        CafeDbInitializer.createRandomClients();
        CafeDbInitializer.createRandomPersonal();
        CafeDbInitializer.createRandomPersonalEmailAddresses();
        CafeDbInitializer.createRandomPersonalPhoneNumber();
    }
}
