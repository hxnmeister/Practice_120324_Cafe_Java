package com.ua.project.menu;

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
        System.out.println("  3. Delete client from Database;");
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
        System.out.println("  0. Return to main menu;");
        System.out.println(SEPARATOR);
        System.out.print("\n Enter number: ");
    }
}
