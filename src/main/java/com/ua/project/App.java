package com.ua.project;

import com.ua.project.exception.ConnectionDBException;
import com.ua.project.menu.MenuExecutor;
import com.ua.project.service.CafeInitializer;

import java.sql.SQLException;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {
        System.setProperty("test", "false");

//        CafeInitializer cafe = new CafeInitializer();
//        cafe.cafeInit();

        try {
            MenuExecutor.menuItem6Execute();
        }
        catch (ConnectionDBException e) {
            System.out.println(" Failed connection to DB!");
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
