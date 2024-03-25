package com.ua.project.menu;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MenuPublisher {

    private static final String SEPARATOR = "=".repeat(25);

    public static void showMainMenu() {
        System.out.println(SEPARATOR);
        System.out.println(" Available actions:");
        System.out.println(SEPARATOR);
        System.out.println("  1. Add;");
        System.out.println("  2. Change;");
        System.out.println("  3. Delete;");
        System.out.println("  4. Display;");
        System.out.println("  0. Exit program;");
        System.out.println(SEPARATOR);
        System.out.print("\n Enter number: ");
    }

    public static void showAddMenu() {
        System.out.println(SEPARATOR);
        System.out.println(" Adding actions:");
        System.out.println(SEPARATOR);
        System.out.println("  1. Add assortment to menu;");
        System.out.println("  2. Add barista;");
        System.out.println("  3. Add confectioner;");
        System.out.println("  4. Add client;");
        System.out.println("  5. Add info about coffee order;");
        System.out.println("  6. Add info about desert order;");
        System.out.println("  7. Add record to schedule (for closest Monday);");
        System.out.println("  0. Return to main menu;");
        System.out.println(SEPARATOR);
        System.out.print("\n Enter number: ");
    }

    public static void showChangeMenu() {
        System.out.println(SEPARATOR);
        System.out.println(" Changing actions:");
        System.out.println(SEPARATOR);
        System.out.println("  1. Change coffee price;");
        System.out.println("  2. Change email for confectioner;");
        System.out.println("  3. Change phone number for barista;");
        System.out.println("  4. Change discount for client;");
        System.out.println("  5. Change schedule (for closest Tuesday);");
        System.out.println("  6. Change title of existing coffee;");
        System.out.println("  7. Change title of existing desert;");
        System.out.println("  8. Change data of existing order;");
        System.out.println("  0. Return to main menu;");
        System.out.println(SEPARATOR);
        System.out.print("\n Enter number: ");
    }

    public static void showDeleteMenu() {
        System.out.println(SEPARATOR);
        System.out.println(" Deleting actions:");
        System.out.println(SEPARATOR);
        System.out.println("  1. Delete desert from menu;");
        System.out.println("  2. Delete waiter with dismissal reason;");
        System.out.println("  3. Delete client;");
        System.out.println("  4. Delete order;");
        System.out.println("  5. Delete schedule by date;");
        System.out.println("  6. Delete schedule by date range;");
        System.out.println("  0. Return to main menu;");
        System.out.println(SEPARATOR);
        System.out.print("\n Enter number: ");
    }

    public static void showDisplayMenu() {
        System.out.println(SEPARATOR);
        System.out.println(" Display actions:");
        System.out.println(SEPARATOR);
        System.out.println("  1. Display all drinks;");
        System.out.println("  2. Display all deserts;");
        System.out.println("  3. Display all baristas;");
        System.out.println("  4. Display all waiters;");
        System.out.println("  5. Display all orders for specific waiter;");
        System.out.println("  6. Display all order for specific client;");
        System.out.println("  7. Display all order for specific desert;");
        System.out.println("  8. Display all order for specific date;");
        System.out.println("  9. Display min discount;");
        System.out.println("  10. Display max discount;");
        System.out.println("  11. Display avg discount;");
        System.out.println("  12. Display client with min discount;");
        System.out.println("  13. Display client with max discount;");
        System.out.println("  14. Display youngest clients;");
        System.out.println("  15. Display oldest clients;");
        System.out.println("  16. Display clients with birthday today (" + new SimpleDateFormat("dd MMM").format(new Date()) + ");");
        System.out.println("  17. Display clients without email;");
        System.out.println("  18. Display orders by specific day;");
        System.out.println("  19. Display orders by date range;");
        System.out.println("  20. Display orders for deserts by day;");
        System.out.println("  21. Display orders for drinks by day;");
        System.out.println("  22. Display clients that ordered drink today and barista;");
        System.out.println("  23. Display avg order price by specific day;");
        System.out.println("  24. Display max order price by specific day;");
        System.out.println("  25. Display client that has max order price by specific day;");
        System.out.println("  0. Return to main menu;");
        System.out.println(SEPARATOR);
        System.out.print("\n Enter number: ");
    }
}
