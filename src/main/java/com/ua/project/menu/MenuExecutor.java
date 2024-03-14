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

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MenuExecutor {
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

        }
        else if(choice == 7) {

        }
        else if(choice == 8) {

        }
        else if(choice == 9) {

        }
        else if(choice == 10) {

        }
        else if(choice == 11) {

        }
        else if(choice == 12) {

        }
        else if(choice == 13) {

        }
        else if(choice == 14) {

        }
        else if(choice == 15) {

        }
        else {

        }
    }

    public static void menuItem1Execute() throws ConnectionDBException, SQLException {
        Scanner scanner = new Scanner(System.in);
        int quantity;
        String type;
        String title;
        BigDecimal price;
        AssortmentDao assortmentDao = new AssortmentDaoImp();
        AssortmentTypeDao assortmentTypeDao = new AssortmentTypeDaoImpl();

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

        type = type.toLowerCase();

        if (!assortmentTypeDao.isAssortmentTypeAvailable(type)) {
            assortmentTypeDao.save(AssortmentType.builder()
                    .title(type)
                    .build());
        }

        assortmentDao.save(Assortment.builder()
                .title(title)
                .quantity(quantity)
                .price(price)
                .assortmentTypeId(assortmentTypeDao.getAssortmentTypeByTitle(type).getId())
                .build());
    }

    public static void menuItem2Execute() throws ConnectionDBException, SQLException {
        addPersonal("barista");
    }

    public static void menuItem3Execute() throws ConnectionDBException, SQLException {
        addPersonal("confectioner");
    }

    public static void menuItem4Execute() throws ConnectionDBException, SQLException {
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

    public static void menuItem5Execute() throws ConnectionDBException, SQLException {
        Scanner scanner = new Scanner(System.in);
        String coffeeName;
        BigDecimal newPrice;
        AssortmentDao assortmentDao = new AssortmentDaoImp();

        showAllDrinks();
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

    public static void menuItem6Execute() throws ConnectionDBException, SQLException {
        Scanner scanner = new Scanner(System.in);
        Personal personal = new Personal();
        String newEmailAddress;
        PersonalDao personalDao = new PersonalDaoImp();
        PersonalEmailAddressDao personalDao = new PersonalEmailAddressDaoImp();
        List<PersonalEmailAddress> personalEmailAddressList = personalDao.findByPersonalId();

        showPersonalByPosition("confectioner");

        System.out.print(" Enter first name: ");
        personal.setFirstName(scanner.nextLine());

        System.out.print(" Enter last name: ");
        personal.setLastName(scanner.nextLine());

        System.out.print(" Enter new email address: ");
        newEmailAddress = scanner.nextLine();

        personalDao.changeEmailAddressByPositionAndName(newEmailAddress, );
    }

    private static void addPersonal(String position) throws ConnectionDBException, SQLException {
        Scanner scanner = new Scanner(System.in);
        String firstName;
        String lastName;
        String patronymic;
        PositionDao positionDao = new PositionDaoImp();
        PersonalDao personalDao = new PersonalDaoImp();

        System.out.println(" To add " + position + ", please follow the fields below:\n");

        System.out.print("  Enter first name: ");
        firstName = scanner.nextLine();

        System.out.print("  Enter last name: ");
        lastName = scanner.nextLine();

        System.out.print("  Enter patronymic: ");
        patronymic = scanner.nextLine();

        personalDao.save(Personal.builder()
                .firstName(firstName)
                .lastName(lastName)
                .patronymic(patronymic)
                .positionId(positionDao.getPositionByTitle(position).getId())
                .build());
    }

    private static void showAllDrinks() throws ConnectionDBException, SQLException {
        AssortmentDao assortmentDao = new AssortmentDaoImp();
        List<Assortment> assortmentList = assortmentDao.getAssortmentByType("drink");

        assortmentList.forEach((item) -> System.out.println(item.getTitle() + " | " + item.getQuantity() + " | " + item.getPrice() + "$"));
    }

    private static void showPersonalByPosition(String position) throws ConnectionDBException, SQLException {
        PersonalDao personalDao = new PersonalDaoImp();
        PersonalPhoneNumberDao personalPhoneNumberDao = new PersonalPhoneNumberDaoImp();
        PersonalEmailAddressDao personalEmailAddressDao = new PersonalEmailAddressDaoImp();
        List<Personal> personalList = personalDao.getPersonalByPosition(position.toLowerCase());

        System.out.println(" All personal by position " + position.toLowerCase() + ":");
        for (Personal personal : personalList) {
            System.out.println(personal.getFirstName() + " " + personal.getLastName() + " " + personal.getPatronymic() + "\n  Phone numbers and emails: ");

            for (PersonalPhoneNumber personalPhoneNumber : personalPhoneNumberDao.findByPersonalId(personal.getId())) {
                System.out.print(personalPhoneNumber.getPhoneNumber() + " ");
            }

            for (PersonalEmailAddress personalEmailAddress : personalEmailAddressDao.findByPersonalId(personal.getId())) {
                System.out.print(personalEmailAddress.getEmailAddress() + " ");
            }

            System.out.println();
        }
    }
}
