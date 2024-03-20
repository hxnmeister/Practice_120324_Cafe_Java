package com.ua.project.menu;

public class MenuPublisher {

    private static final String SEPARATOR = "=".repeat(25);

    public static void showMenu() {
        System.out.println(SEPARATOR);
        System.out.println(" Available actions:");
        System.out.println(SEPARATOR);
        System.out.println("  1. Add assortment to menu;");
        System.out.println("  2. Add barista;");
        System.out.println("  3. Add confectioner;");
        System.out.println("  4. Add client;");
        System.out.println("  5. Change coffee price;");
        System.out.println("  6. Change email for confectioner;");
        System.out.println("  7. Change phone number for barista;");
        System.out.println("  8. Change discount for client;");
        System.out.println("  9. Display all drinks;");
        System.out.println("  10. Display all deserts;");
        System.out.println("  11. Display all baristas;");
        System.out.println("  12. Display all waiters;");
        System.out.println("  13. Delete desert from menu;");
        System.out.println("  14. Delete waiter with dismissal reason;");
        System.out.println("  15. Delete client from Database;");
        System.out.println("  0. Exit program;");
        System.out.println(SEPARATOR);
        System.out.print("\n Enter number: ");
    }
}
