package com.ua.project.menu;

import com.ua.project.dao.assortmentDAO.AssortmentDao;
import com.ua.project.dao.assortmentDAO.AssortmentDaoImp;
import com.ua.project.dao.assortment_typeDAO.AssortmentTypeDao;
import com.ua.project.dao.assortment_typeDAO.AssortmentTypeDaoImpl;
import com.ua.project.dao.clientDAO.ClientDao;
import com.ua.project.dao.clientDAO.ClientDaoImp;
import com.ua.project.dao.personalDAO.PersonalDao;
import com.ua.project.dao.personalDAO.PersonalDaoImp;
import com.ua.project.dao.personal_email_addressDAO.PersonalEmailAddressDao;
import com.ua.project.dao.personal_email_addressDAO.PersonalEmailAddressDaoImp;
import com.ua.project.dao.personal_phone_numberDAO.PersonalPhoneNumberDao;
import com.ua.project.dao.personal_phone_numberDAO.PersonalPhoneNumberDaoImp;
import com.ua.project.dao.positionDAO.PositionDao;
import com.ua.project.dao.positionDAO.PositionDaoImp;
import com.ua.project.exception.ConnectionDBException;
import com.ua.project.model.*;
import com.ua.project.service.business.assortment.AssortmentService;
import com.ua.project.service.business.assortment.AssortmentServiceImp;
import com.ua.project.service.business.client.ClientService;
import com.ua.project.service.business.client.ClientServiceImp;
import com.ua.project.service.business.personal.PersonalService;
import com.ua.project.service.business.personal.PersonalServiceImp;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MenuExecutor {
    private static final AssortmentService assortmentService = new AssortmentServiceImp();
    private static final PersonalService personalService = new PersonalServiceImp();
    private static final ClientService clientService = new ClientServiceImp();

    public static void startMenu() throws ConnectionDBException, SQLException {
        Scanner scanner = new Scanner(System.in);
        int choice;

        MenuPublisher.showMenu();

        choice = scanner.nextInt();

        if(choice == 1) {
            menuItem1Execute();
        }
        else if(choice == 2) {
            menuItem2Execute();
        }
        else if(choice == 3) {
            menuItem3Execute();
        }
        else if(choice == 4) {
            menuItem4Execute();
        }
        else if(choice == 5) {
            menuItem5Execute();
        }
        else if(choice == 6) {
            menuItem6Execute();
        }
        else if(choice == 7) {
            menuItem7Execute();
        }
        else if(choice == 8) {
            menuItem8Execute();
        }
        else if(choice == 9) {
            menuItem9Execute();
        }
        else if(choice == 10) {
            menuItem10Execute();
        }
        else if(choice == 11) {
            menuItem11Execute();
        }
        else if(choice == 12) {
            menuItem12Execute();
        }
        else if(choice == 13) {
            menuItem13Execute();
        }
        else if(choice == 14) {
            menuItem14Execute();
        }
        else if(choice == 15) {
            menuItem15Execute();
        }
        else if (choice == 0) {
            System.exit(0);
        }
        else {
            System.out.println(" Incorrect number, allowed range from 1 to 15!\n Try again!");
        }
    }

    public static void menuItem1Execute() {
        Scanner scanner = new Scanner(System.in);
        int quantity;
        String type;
        String title;
        BigDecimal price;

        System.out.println(" To add item to menu, please follow the fields below:\n");

        while (true) {
            try {
                System.out.print("  Enter title: ");
                title = scanner.nextLine();

                System.out.print("  Enter quantity: ");
                quantity = scanner.nextInt();
                scanner.nextLine();

                System.out.print("  Enter price: ");
                price = scanner.nextBigDecimal();
                scanner.nextLine();

                System.out.print(" Enter type of item: ");
                type = scanner.nextLine();

                break;
            }
            catch (InputMismatchException e) {
                System.out.println("\n Please enter correct data!");
                scanner.nextLine();
            }
            catch (RuntimeException e) {
                System.out.println(e.getMessage());
                scanner.nextLine();
            }
        }

        assortmentService.addAssortment(Assortment.builder().title(title).quantity(quantity).price(price).build(), type.toLowerCase());
    }

    public static void menuItem2Execute() {
        final String POSITION = "barista";
        Personal personal = getPersonalBio(POSITION);

        personalService.addPersonal(personal, POSITION);
    }

    public static void menuItem3Execute() {
        final String POSITION = "confectioner";
        Personal personal = getPersonalBio(POSITION);

        personalService.addPersonal(personal, POSITION);
    }

    public static void menuItem4Execute() {
        Scanner scanner = new Scanner(System.in);
        int discount = new Random().nextInt(99);
        Date birthDate;
        String firstName;
        String lastName;
        String patronymic;
        String contactPhone;
        String contactEmail;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        ClientDao clientDao = new ClientDaoImp();

        System.out.println(" To add client, please follow the fields below:\n");

        System.out.print("  Enter first name: ");
        firstName = scanner.nextLine();

        System.out.print("  Enter last name: ");
        lastName = scanner.nextLine();

        System.out.print("  Enter patronymic: ");
        patronymic = scanner.nextLine();

        while (true) {
            try {
                System.out.print("  Enter date of birth (yyyy-MM-dd): ");
                birthDate = simpleDateFormat.parse(scanner.nextLine());

                break;
            }
            catch (ParseException e) {
                System.out.println(" Please enter correct date!");
                scanner.nextLine();
            }
        }

        System.out.print(" Enter contact phone: ");
        contactPhone = scanner.nextLine();

        System.out.print(" Enter contact email: ");
        contactEmail = scanner.nextLine();

        clientDao.save(Client.builder()
                .firstName(firstName)
                .lastName(lastName)
                .patronymic(patronymic)
                .birthDate(java.sql.Date.valueOf(simpleDateFormat.format(birthDate)))
                .contactPhone(contactPhone)
                .contactEmail(contactEmail)
                .discount(discount)
                .build());
    }

    public static void menuItem5Execute() {
        Scanner scanner = new Scanner(System.in);
        String coffeeName;
        String assortmentType = "drink";
        BigDecimal newPrice;
        AssortmentDao assortmentDao = new AssortmentDaoImp();

        System.out.println(assortmentService.getAllAssortmentByType(assortmentType));
        System.out.println();

        System.out.print(" Enter coffee name: ");
        coffeeName = scanner.nextLine();

        while (true) {
            try {
                System.out.print(" Enter new price: ");
                newPrice = scanner.nextBigDecimal();
                scanner.nextLine();

                break;
            }
            catch (InputMismatchException e) {
                System.out.println("\n Please enter correct value for price!");
                scanner.nextLine();
            }
        }

        assortmentDao.changePriceByTypeAndTitle("drink", coffeeName, newPrice);
    }

    public static void menuItem6Execute() {
        Scanner scanner = new Scanner(System.in);
        Personal personal = new Personal();
        String newEmailAddress;
        String oldEmailAddress;
        final String POSITION = "confectioner";
        PersonalDao personalDao = new PersonalDaoImp();

        System.out.println(personalService.getPersonalByPosition(POSITION));

        System.out.println("\n In fields below enter data about " + POSITION + " to change email\n");
        System.out.print(" Enter first name: ");
        personal.setFirstName(scanner.nextLine());

        System.out.print(" Enter last name: ");
        personal.setLastName(scanner.nextLine());

        System.out.print(" Enter old email: ");
        oldEmailAddress = scanner.nextLine();

        System.out.print(" Enter new email: ");
        newEmailAddress = scanner.nextLine();

        personalDao.changeEmailAddressByPositionAndName(newEmailAddress, oldEmailAddress, POSITION, personal);
    }

    public static void menuItem7Execute() {
        Scanner scanner = new Scanner(System.in);
        Personal personal = new Personal();
        String newPhoneNumber;
        String oldPhoneNumber;
        final String POSITION = "barista";
        PersonalDao personalDao = new PersonalDaoImp();

        System.out.println(personalService.getPersonalByPosition(POSITION));

        System.out.println("\n In fields below enter data about " + POSITION + " to change email\n");
        System.out.print(" Enter first name: ");
        personal.setFirstName(scanner.nextLine());

        System.out.print(" Enter last name: ");
        personal.setLastName(scanner.nextLine());

        System.out.print(" Enter old phone number: ");
        oldPhoneNumber = scanner.nextLine();

        System.out.print(" Enter new phone number: ");
        newPhoneNumber = scanner.nextLine();

        personalDao.changePhoneNumberByPositionAndName(newPhoneNumber, oldPhoneNumber, POSITION, personal);
    }

    public static void  menuItem8Execute() {
        Scanner scanner = new Scanner(System.in);
        ClientDao clientDao = new ClientDaoImp();
        Client client = new Client();

        System.out.println(clientService.getAllClients());

        System.out.println("\n In fields below enter data about client to change discount:\n");

        System.out.print("  Enter first name: ");
        client.setFirstName(scanner.nextLine());

        System.out.print("  Enter last name: ");
        client.setLastName(scanner.nextLine());

        while (true) {
            try {
                int newDiscount;

                System.out.print("  Enter new discount value: ");
                newDiscount = scanner.nextInt();

                if(newDiscount < 0 || newDiscount > 99) {
                    throw new IllegalArgumentException("\n Incorrect range for discount " + newDiscount + "!\n Please enter number between 0 and 99\n");
                }

                client.setDiscount(newDiscount);
                break;
            }
            catch (InputMismatchException e) {
                System.out.println("\n Please enter correct number!");
                scanner.nextLine();
            }
            catch (RuntimeException e) {
                System.out.println(e.getMessage());
                scanner.nextLine();
            }
        }

        clientDao.changeDiscountValueByName(client);
    }

    public static void menuItem9Execute() {
        String assortmentType = "drink";

        System.out.println(assortmentService.getAllAssortmentByType(assortmentType));
    }

    public static void menuItem10Execute() {
        String assortmentType = "desert";

        System.out.println(assortmentService.getAllAssortmentByType(assortmentType));
    }

    public static void menuItem11Execute() {
        String position = "barista";

        System.out.println(personalService.getPersonalByPosition(position));
    }

    public static void menuItem12Execute() {
        String position = "waiter";

        System.out.println(personalService.getPersonalByPosition(position));
    }

    public static void menuItem13Execute() {
        Scanner scanner = new Scanner(System.in);
        AssortmentDao assortmentDao = new AssortmentDaoImp();
        final String assortmentType = "desert";
        String desertTitle;

        System.out.println(assortmentService.getAllAssortmentByType(assortmentType));

        System.out.print(" Enter name of desert to delete it: ");
        desertTitle = scanner.nextLine();

        assortmentDao.deleteAssortmentByTypeAndTitle(assortmentType, desertTitle);
    }

    public static void menuItem14Execute() {
        Scanner scanner = new Scanner(System.in);
        Personal personal = new Personal();
        PersonalDao personalDao = new PersonalDaoImp();
        String dismissalReason;
        final String POSITION = "waiter";

        System.out.println(personalService.getPersonalByPosition(POSITION));
        System.out.println("\n To delete " + POSITION + " enter first, last names and dismissal reason:");

        System.out.print("\n Enter first name: ");
        personal.setFirstName(scanner.nextLine());

        System.out.print(" Enter last name: ");
        personal.setLastName(scanner.nextLine());

        System.out.print(" Enter dismissal reason: ");
        dismissalReason = scanner.nextLine();

        personalDao.deletePersonalByPositionAndName(dismissalReason, POSITION, personal);
    }

    public static void menuItem15Execute() {
        Scanner scanner = new Scanner(System.in);
        ClientDao clientDao = new ClientDaoImp();
        Client client = new Client();

        System.out.println(clientService.getAllClients());
        System.out.println("\n  To delete client in fields below enter first and last names:");

        System.out.print("\n Enter first name: ");
        client.setFirstName(scanner.nextLine());

        System.out.print(" Enter last name: ");
        client.setLastName(scanner.nextLine());

        clientDao.deleteClientByName(client);
    }

    private static Personal getPersonalBio(String position) {
        Scanner scanner = new Scanner(System.in);
        String firstName;
        String lastName;
        String patronymic;

        System.out.println(" To add " + position + ", please follow the fields below:\n");

        System.out.print("  Enter first name: ");
        firstName = scanner.nextLine();

        System.out.print("  Enter last name: ");
        lastName = scanner.nextLine();

        System.out.print("  Enter patronymic: ");
        patronymic = scanner.nextLine();

        return Personal.builder().firstName(firstName).lastName(lastName).patronymic(patronymic).build();
    }
}
