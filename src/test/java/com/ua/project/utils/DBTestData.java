package com.ua.project.utils;

import com.ua.project.dao.ConnectionFactory;
import com.ua.project.exception.ConnectionDBException;
import com.ua.project.service.PropertyFactory;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.System.setProperty;

@NoArgsConstructor
public class DBTestData {
    private static final String SQL_INSERT_DATA;
    private static final String SQL_DELETE_ALL_DATA;
    private static final String SQL_DROP_ALL_TABLES;

    static {
        setProperty("test", "true");

        SQL_INSERT_DATA = PropertyFactory.getInstance().getProperty().getProperty("db.sqlInsertTables");
        SQL_DELETE_ALL_DATA = PropertyFactory.getInstance().getProperty().getProperty("db.sqlDeleteRows");
        SQL_DROP_ALL_TABLES = PropertyFactory.getInstance().getProperty().getProperty("db.sqlDropTables");
    }

    public static void insert() {
        executeQuery(SQL_INSERT_DATA);
    }

    public static void deleteAll() {
        executeQuery(SQL_DELETE_ALL_DATA);
    }

    public static void drop() {
        executeQuery(SQL_DROP_ALL_TABLES);
    }

    private static void executeQuery(String query) {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection()) {
            try (Stream<String> stringStream = Files.lines((Paths.get(query)))) {
                StringBuilder queryBuilder = new StringBuilder();

                for (String line : stringStream.collect(Collectors.toList())) {
                    queryBuilder.append(line).append(" ");
                }

                try (Statement statement = connection.createStatement()) {
                    statement.execute(queryBuilder.toString());
                }
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        catch (ConnectionDBException|SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
