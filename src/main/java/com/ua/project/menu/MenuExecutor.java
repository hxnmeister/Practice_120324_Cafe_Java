package com.ua.project.menu;

import com.ua.project.dao.CRUDInterface;
import com.ua.project.dao.assortmentDAO.AssortmentDao;
import com.ua.project.dao.assortmentDAO.AssortmentDaoImp;
import com.ua.project.dao.assortment_typeDAO.AssortmentTypeDao;
import com.ua.project.dao.assortment_typeDAO.AssortmentTypeDaoImpl;
import com.ua.project.dao.clientDAO.ClientDao;
import com.ua.project.dao.clientDAO.ClientDaoImp;
import com.ua.project.dao.orderDAO.OrderDao;
import com.ua.project.dao.orderDAO.OrderDaoImp;
import com.ua.project.dao.order_and_assortmentDAO.OrderAndAssortmentDao;
import com.ua.project.dao.order_and_assortmentDAO.OrderAndAssortmentDaoImp;
import com.ua.project.dao.personalDAO.PersonalDao;
import com.ua.project.dao.personalDAO.PersonalDaoImp;
import com.ua.project.dao.personal_email_addressDAO.PersonalEmailAddressDao;
import com.ua.project.dao.personal_email_addressDAO.PersonalEmailAddressDaoImp;
import com.ua.project.dao.personal_phone_numberDAO.PersonalPhoneNumberDao;
import com.ua.project.dao.personal_phone_numberDAO.PersonalPhoneNumberDaoImp;
import com.ua.project.dao.positionDAO.PositionDao;
import com.ua.project.dao.positionDAO.PositionDaoImp;
import com.ua.project.dao.scheduleDAO.ScheduleDao;
import com.ua.project.dao.scheduleDAO.ScheduleDaoImp;
import com.ua.project.exception.ConnectionDBException;
import com.ua.project.model.*;
import com.ua.project.service.business.assortment.AssortmentService;
import com.ua.project.service.business.assortment.AssortmentServiceImp;
import com.ua.project.service.business.client.ClientService;
import com.ua.project.service.business.client.ClientServiceImp;
import com.ua.project.service.business.order.OrderService;
import com.ua.project.service.business.order.OrderServiceImp;
import com.ua.project.service.business.personal.PersonalService;
import com.ua.project.service.business.personal.PersonalServiceImp;
import com.ua.project.service.business.schedule.ScheduleService;
import com.ua.project.service.business.schedule.ScheduleServiceImp;
import org.codehaus.plexus.util.StringUtils;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class MenuExecutor {
    private static final AssortmentService assortmentService = new AssortmentServiceImp();
    private static final PersonalService personalService = new PersonalServiceImp();
    private static final ScheduleService scheduleService = new ScheduleServiceImp();
    private static final ClientService clientService = new ClientServiceImp();
    private static final OrderService orderService = new OrderServiceImp();

    public static void startMenu() throws ConnectionDBException, SQLException {
        Scanner scanner = new Scanner(System.in);
        int mainMenuChoice;
        int subMenuChoice;

        MenuPublisher.showMainMenu();
        mainMenuChoice = scanner.nextInt();

        if(mainMenuChoice == 1) {
            while (true) {
                MenuPublisher.showAddMenu();
                subMenuChoice = scanner.nextInt();

                if(subMenuChoice == 1) {
                    addingMenuItem1Execute();
                }
                else if(subMenuChoice == 2) {
                    addingMenuItem2Execute();
                }
                else if(subMenuChoice == 3) {
                    addingMenuItem3Execute();
                }
                else if(subMenuChoice == 4) {
                    addingMenuItem4Execute();
                }
                else if(subMenuChoice == 5) {
                    addingMenuItem5Execute();
                }
                else if(subMenuChoice == 6) {
                    addingMenuItem6Execute();
                }
                else if(subMenuChoice == 7) {
                    addingMenuItem7Execute();
                }
                else if(subMenuChoice == 0) {
                    System.out.println(" Returning to main menu...");
                    break;
                }
                else {
                    System.out.println(" Incorrect number, allowed range from 1 to 4!\n Try again!");
                }
            }
        }
        else if(mainMenuChoice == 2) {
            while (true) {
                MenuPublisher.showChangeMenu();
                subMenuChoice = scanner.nextInt();

                if(subMenuChoice == 1) {
                    changingMenuItem1Execute();
                }
                else if(subMenuChoice == 2) {
                    changingMenuItem2Execute();
                }
                else if(subMenuChoice == 3) {
                    changingMenuItem3Execute();
                }
                else if(subMenuChoice == 4) {
                    changingMenuItem4Execute();
                }
                else if(subMenuChoice == 5) {
                    changingMenuItem5Execute();
                }
                else if(subMenuChoice == 6) {
                    changingMenuItem6Execute();
                }
                else if(subMenuChoice == 7) {
                    changingMenuItem7Execute();
                }
                else if(subMenuChoice == 8) {
                    changingMenuItem8Execute();
                }
                else if(subMenuChoice == 0) {
                    System.out.println(" Returning to main menu...");
                    break;
                }
                else {
                    System.out.println(" Incorrect number, allowed range from 1 to 8!\n Try again!");
                }
            }
        }
        else if(mainMenuChoice == 3) {
            while (true) {
                MenuPublisher.showDeleteMenu();
                subMenuChoice = scanner.nextInt();

                if(subMenuChoice == 1) {
                    deleteMenuItem1Execute();
                }
                else if(subMenuChoice == 2) {
                    deleteMenuItem2Execute();
                }
                else if(subMenuChoice == 3) {
                    deleteMenuItem3Execute();
                }
                else if(subMenuChoice == 4) {
                    deleteMenuItem4Execute();
                }
                else if(subMenuChoice == 5) {
                    deleteMenuItem5Execute();
                }
                else if(subMenuChoice == 6) {
                    deleteMenuItem6Execute();
                }
                else if(subMenuChoice == 0) {
                    System.out.println(" Returning to main menu...");
                    break;
                }
                else {
                    System.out.println(" Incorrect number, allowed range from 1 to 6!\n Try again!");
                }
            }
        }
        else if(mainMenuChoice == 4) {
            while (true) {
                MenuPublisher.showDisplayMenu();
                subMenuChoice = scanner.nextInt();

                if(subMenuChoice == 1) {
                    displayMenuItem1Execute();
                }
                else if(subMenuChoice == 2) {
                    displayMenuItem2Execute();
                }
                else if(subMenuChoice == 3) {
                    displayMenuItem3Execute();
                }
                else if(subMenuChoice == 4) {
                    displayMenuItem4Execute();
                }
                else if(subMenuChoice == 5) {
                    displayMenuItem5Execute();
                }
                else if(subMenuChoice == 6) {
                    displayMenuItem6Execute();
                }
                else if(subMenuChoice == 7) {
                    displayMenuItem7Execute();
                }
                else if(subMenuChoice == 8) {
                    displayMenuItem8Execute();
                }
                else if(subMenuChoice == 0) {
                    System.out.println(" Returning to main menu...");
                    break;
                }
                else {
                    System.out.println(" Incorrect number, allowed range from 1 to 8!\n Try again!");
                }
            }
        }
        else if(mainMenuChoice == 0) {
            System.out.println(" Shutting down the program...");
            System.exit(0);
        }
        else {
            System.out.println(" Incorrect number, allowed range from 1 to 4!\n Try again!");
        }
    }

    public static void addingMenuItem1Execute() {
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

    public static void addingMenuItem2Execute() {
        final String POSITION = "barista";
        Personal personal = getPersonalBio(POSITION);

        personalService.addPersonal(personal, POSITION);
    }

    public static void addingMenuItem3Execute() {
        final String POSITION = "confectioner";
        Personal personal = getPersonalBio(POSITION);

        personalService.addPersonal(personal, POSITION);
    }

    public static void addingMenuItem4Execute() {
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

    public static void addingMenuItem5Execute() {
        addingNewOrder("drink");
    }

    public static void addingMenuItem6Execute() {
        addingNewOrder("desert");
    }

    public static void addingMenuItem7Execute() {
        Calendar nextMonday = getCountOfDaysToSpecificDay(Calendar.MONDAY);
        Schedule schedule;
        ScheduleDao scheduleDao = new ScheduleDaoImp();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        System.out.println(" Closest monday is: " + dateFormat.format(nextMonday.getTime()) + "\n");

        schedule = getScheduleDataExceptDate();
        schedule.setWorkDate(new java.sql.Date(nextMonday.getTimeInMillis()));

        scheduleDao.save(schedule);
    }

    public static void changingMenuItem1Execute() {
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

    public static void changingMenuItem2Execute() {
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

    public static void changingMenuItem3Execute() {
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

    public static void  changingMenuItem4Execute() {
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

    public static void  changingMenuItem5Execute() {
        long id;
        Schedule schedule;
        Calendar nextTuesday = getCountOfDaysToSpecificDay(Calendar.TUESDAY);
        ScheduleDao scheduleDao = new ScheduleDaoImp();
        List<Schedule> scheduleList = scheduleDao.findAll();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        System.out.println(scheduleService.getAllSchedules());
        id = getValidIdFromUser(scheduleList.stream().map(Schedule::getId).collect(Collectors.toList()), "\n Enter schedule ID to change it to closest Tuesday (" + dateFormat.format(nextTuesday.getTime()) + ")");

        schedule = getScheduleDataExceptDate();
        schedule.setId(id);
        schedule.setWorkDate(new java.sql.Date(nextTuesday.getTimeInMillis()));

        scheduleDao.update(schedule);
    }

    public static void changingMenuItem6Execute() {
        System.out.println("\n Title has been " + (changeAssortmentTitle("drink", " Enter coffee ID to change title: ") ? "" : "not") + " changed!");
    }

    public static void changingMenuItem7Execute() {
        System.out.println("\n Title has been " + (changeAssortmentTitle("desert", " Enter desert ID to change title: ") ? "" : "not") + " changed!");
    }

    public static void changingMenuItem8Execute() {
        Scanner scanner = new Scanner(System.in);
        Order order;
        String price;
        String priceWithDiscount;
        String timestamp;
        OrderDao orderDao = new OrderDaoImp();
        List<Order> orders = orderDao.findAll();

        while (true) {
            try {
                System.out.println(orderService.getAllOrders());
                final long id = getValidIdFromUser(orders.stream().map(Order::getId).collect(Collectors.toList()), " Enter order ID to change data in it: ");
                order = orders.stream().filter((item) -> item.getId() == id).findFirst().orElseThrow();

                System.out.println("\n To skip, press enter");

                System.out.print("\n Enter price: ");
                price = scanner.nextLine();

                System.out.print("\n Enter price with discount: ");
                priceWithDiscount = scanner.nextLine();

                System.out.print("\n Enter timestamp (yyyy-MM-dd hh:mm): ");
                timestamp = scanner.nextLine();

                if(!price.isEmpty()) {
                    order.setPrice(new BigDecimal(price));
                }
                if(!priceWithDiscount.isEmpty()) {
                    order.setPriceWithDiscount(new BigDecimal(priceWithDiscount));
                }
                if(!timestamp.isEmpty()) {
                    order.setTimestamp(Timestamp.valueOf(timestamp));
                }

                break;
            }
            catch (NoSuchElementException e) {
                System.out.println(" There is no such element with such id!");
            }
            catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }

        orderDao.update(order);
    }

    public static void displayMenuItem1Execute() {
        String assortmentType = "drink";

        System.out.println(assortmentService.getAllAssortmentByType(assortmentType));
    }

    public static void displayMenuItem2Execute() {
        String assortmentType = "desert";

        System.out.println(assortmentService.getAllAssortmentByType(assortmentType));
    }

    public static void displayMenuItem3Execute() {
        String position = "barista";

        System.out.println(personalService.getPersonalByPosition(position));
    }

    public static void displayMenuItem4Execute() {
        String position = "waiter";

        System.out.println(personalService.getPersonalByPosition(position));
    }

    public static void displayMenuItem5Execute() {
        long id;
        Personal personal;
        List<Order> orders;
        OrderDao orderDao = new OrderDaoImp();
        PersonalDao personalDao = new PersonalDaoImp();
        List<Personal> personalList = personalDao.getPersonalByPosition("waiter");

        System.out.println(personalService.getPersonalByPosition("waiter"));
        id = getValidIdFromUser(personalList.stream().map(Personal::getId).collect(Collectors.toList()), " Enter personal id to display orders: ");
        personal = personalList.stream().filter((item) -> item.getId() == id).findFirst().get();
        orders = orderDao.findOrdersByPersonalId(id);

        showOrdersByPersonId(personal.getFirstName(), personal.getLastName(), orders, id);
    }

    public static void displayMenuItem6Execute() {
        long id;
        Client client;
        List<Order> orders;
        OrderDao orderDao = new OrderDaoImp();
        ClientDao clientDao = new ClientDaoImp();
        List<Client> clients = clientDao.findAll();

        System.out.println(clientService.getAllClients());
        id = getValidIdFromUser(clients.stream().map(Client::getId).collect(Collectors.toList()), " Enter personal id to display orders: ");
        client = clients.stream().filter((item) -> item.getId() == id).findFirst().get();
        orders = orderDao.findOrdersByClientId(id);

        showOrdersByPersonId(client.getFirstName(), client.getLastName(), orders, id);
    }

    public static void displayMenuItem7Execute() {
        long id;
        OrderDao orderDao = new OrderDaoImp();
        AssortmentDao assortmentDao = new AssortmentDaoImp();
        OrderAndAssortmentDao orderAndAssortmentDao = new OrderAndAssortmentDaoImp();
        List<Long> ordersId;
        List<Order> orders = orderDao.findAll();
        List<Assortment> deserts = assortmentDao.getAssortmentByType("desert");
        List<OrderAndAssortment> orderAndAssortments = orderAndAssortmentDao.findAll();

        System.out.println(assortmentService.getAllAssortmentByType("desert"));
        id = getValidIdFromUser(deserts.stream().map(Assortment::getId).collect(Collectors.toList()), " Enter id to display orders: ");

        System.out.println(" Orders for " + deserts.stream().filter((item) -> item.getId() == id).findFirst().get().getTitle() + ":\n");
        ordersId = orderAndAssortments.stream().filter((item) -> item.getAssortmentId() == id).map(OrderAndAssortment::getOrderId).collect(Collectors.toList());

        for (Long orderId : ordersId) {
            for (Order order : orders) {
                if(orderId.equals(order.getId())) {
                    System.out.println(" Timestamp: " + order.getTimestamp());
                    System.out.println(" Price: " + order.getPrice());
                    System.out.println(" Price With Discount: " + order.getPriceWithDiscount());
                    System.out.println("-".repeat(20));
                }
            }
        }
    }

    public static void displayMenuItem8Execute() {
        ScheduleDao scheduleDao = new ScheduleDaoImp();
        List<Schedule> scheduleList = scheduleDao.findAll();

        while (true) {
            try {
                final java.sql.Date date = getValidDateFromUser("\n Enter date to delete schedule (yyyy-MM-dd): ");

                for (Schedule currentSchedule : scheduleList) {
                    if(currentSchedule.getWorkDate().equals(date)) {
                        scheduleList.stream().filter((item) -> item.getWorkDate().equals(date)).collect(Collectors.toList()).forEach(System.out::println);
                        return;
                    }
                }

                throw new RuntimeException(" There is no such schedule!");
            }
            catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void deleteMenuItem1Execute() {
        Scanner scanner = new Scanner(System.in);
        AssortmentDao assortmentDao = new AssortmentDaoImp();
        final String assortmentType = "desert";
        String desertTitle;

        System.out.println(assortmentService.getAllAssortmentByType(assortmentType));

        System.out.print(" Enter name of desert to delete it: ");
        desertTitle = scanner.nextLine();

        assortmentDao.deleteAssortmentByTypeAndTitle(assortmentType, desertTitle);
    }

    public static void deleteMenuItem2Execute() {
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

    public static void deleteMenuItem3Execute() {
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

    public static void deleteMenuItem4Execute() {
        long id;
        Order order;
        OrderDao orderDao = new OrderDaoImp();
        List<Order> orders = orderDao.findAll();
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println(orderService.getAllOrders());
            id = getValidIdFromUser(orders.stream().map(Order::getId).collect(Collectors.toList()), " Enter ID of order to delete id: ");
            order = orders.stream().filter((item) -> item.getId() == id).findFirst().orElseThrow();

            orderDao.delete(order);
        }
        catch (NoSuchElementException e) {
            System.out.println(" Element with such id not exists!");
        }
        catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deleteMenuItem5Execute() {
        ScheduleDao scheduleDao = new ScheduleDaoImp();
        List<Schedule> deleteList = new ArrayList<>();
        List<Schedule> scheduleList = scheduleDao.findAll();

        while (true) {
            try {
                final java.sql.Date date = getValidDateFromUser("\n Enter date to delete schedule (yyyy-MM-dd): ");

                for (Schedule currentSchedule : scheduleList) {
                    if(currentSchedule.getWorkDate().equals(date)) {
                        deleteList.addAll(scheduleList.stream().filter((item) -> item.getWorkDate().equals(date)).collect(Collectors.toList()));
                        deleteList.forEach(scheduleDao::delete);
                        return;
                    }
                }

                throw new RuntimeException(" There is no such schedule!");
            }
            catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void deleteMenuItem6Execute() {
        java.sql.Date rangeBegin;
        java.sql.Date rangeEnd;
        Scanner scanner = new Scanner(System.in);
        ScheduleDao scheduleDao = new ScheduleDaoImp();
        List<Schedule> deleteList = new ArrayList<>();
        List<Schedule> scheduleList = scheduleDao.findAll();

        while (true) {
            try {
                System.out.println(scheduleService.getAllSchedules());

                System.out.print("\n Enter date begin range (yyyy-MM-dd): ");
                rangeBegin = java.sql.Date.valueOf(scanner.nextLine());

                System.out.print("\n Enter date end range (yyyy-MM-dd): ");
                rangeEnd = java.sql.Date.valueOf(scanner.nextLine());

                if (rangeBegin.getTime() >= rangeEnd.getTime()) {
                    throw new RuntimeException(" Incorrect range!");
                }

                for (Schedule currentSchedule : scheduleList) {
                    if(currentSchedule.getWorkDate().getTime() < rangeBegin.getTime() || currentSchedule.getWorkDate().getTime() > rangeEnd.getTime()) {
                        throw new RuntimeException(" This range incorrect for current schedules!");
                    }
                }

                break;
            }
            catch (IllegalArgumentException e) {
                System.out.println(" Please enter correct data!");
            }
            catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }

        scheduleDao.deleteInDateRange(rangeBegin, rangeEnd);
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

    private static void addingNewOrder(String assortmentType) {
        char confirm = ' ';
        long id;
        String position;
        Personal personal;
        Client client = new Client();
        BigDecimal price = BigDecimal.ZERO;
        Scanner scanner = new Scanner(System.in);
        OrderDao orderDao = new OrderDaoImp();
        ClientDao clientDao = new ClientDaoImp();
        PersonalDao personalDao = new PersonalDaoImp();
        AssortmentDao assortmentDao = new AssortmentDaoImp();
        List<Assortment> assortmentList = assortmentDao.getAssortmentByType(assortmentType);
        List<Assortment> orderAssortmentList = new ArrayList<>();

        do {
            boolean passed = false;

            System.out.println(assortmentService.getAllAssortmentByType(assortmentType));

            try {
                System.out.print(" Enter " + assortmentType + " id for adding to order: ");
                id = scanner.nextLong();
                scanner.nextLine();

                for (Assortment assortment : assortmentList) {
                    if(assortment.getId() == id) {
                        passed = true;
                        orderAssortmentList.add(assortment);
                        break;
                    }
                }

                if (!passed) {
                    throw new RuntimeException(" Entered ID is incorrect!");
                }
            }
            catch (InputMismatchException e) {
                System.out.println(" Please enter a correct number!");
                continue;
            }
            catch (RuntimeException e) {
                System.out.println(e.getMessage());
                continue;
            }

            System.out.print(" Would you like to add another " + assortmentType + "?(Y/N): ");
            confirm = scanner.nextLine().toLowerCase().charAt(0);
        } while (confirm != 'n');

        System.out.print("\n Enter personal position: ");
        position = scanner.nextLine();

        System.out.println(personalService.getPersonalByPosition(position));
        personal = getPersonalBio(position);

        System.out.println(clientService.getAllClients());
        System.out.print("\n Enter client first name: ");
        client.setFirstName(scanner.nextLine());
        System.out.print(" Enter client last name: ");
        client.setLastName(scanner.nextLine());

        for (Assortment assortment : orderAssortmentList) {
            price = price.add(assortment.getPrice());
        }

        orderDao.addNewOrder(orderAssortmentList, Order.builder()
                .timestamp(Timestamp.valueOf(LocalDateTime.now()))
                .price(price).priceWithDiscount(price)
                .clientId(clientDao.getIdByName(client))
                .personalId(personalDao.getIdByName(personal))
                .build());
    }

    private static Calendar getCountOfDaysToSpecificDay(int dayOfWeek) {
        Calendar calendar = new GregorianCalendar();
        final int DAYS_UNTIL_SPECIFIC_DAY = (dayOfWeek - calendar.get(Calendar.DAY_OF_WEEK) + 7) % 7;

        calendar.add(Calendar.DAY_OF_MONTH, DAYS_UNTIL_SPECIFIC_DAY);

        return calendar;
    }

    private static Schedule getScheduleDataExceptDate() {
        Schedule schedule = new Schedule();
        long personalId;
        Scanner scanner = new Scanner(System.in);
        String position;
        Personal personal;
        PersonalDao personalDao = new PersonalDaoImp();

        System.out.print("\n Enter personal position: ");
        position = scanner.nextLine();
        System.out.println(personalService.getPersonalByPosition(position));
        personal = getPersonalBio(position);
        personalId = personalDao.getIdByName(personal);

        while (true) {
            try {
                System.out.print("\n Enter work shift begin (hh:mm): ");
                schedule.setWorkHoursBegin(Time.valueOf(scanner.nextLine() + ":00"));

                System.out.print("\n Enter work shift ends (hh:mm): ");
                schedule.setWorkHoursEnd(Time.valueOf(scanner.nextLine() + ":00"));

                if(schedule.getWorkHoursBegin().getTime() > schedule.getWorkHoursEnd().getTime()) {
                    throw new RuntimeException(" Incorrect range from |" + schedule.getWorkHoursBegin() + "| to |" + schedule.getWorkHoursEnd() + "|");
                }

                break;
            }
            catch (IllegalArgumentException e) {
                System.out.println(" Incorrect format for time!");
            }
            catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }
        schedule.setPersonalId(personalId);

        return schedule;
    }

    private static long getValidIdFromUser(List<Long> longList, String inputMessage) {
        long id;
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                System.out.print(inputMessage);
                id = scanner.nextLong();
                scanner.nextLine();

                if (longList.contains(id)) {
                    return id;
                }
                else {
                    throw new RuntimeException(" You entered incorrect ID!");
                }
            }
            catch (InputMismatchException e) {
                System.out.println("Please enter a correct number!");
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static boolean changeAssortmentTitle(String assortmentType, String inputMessage) {
        long id;
        Scanner scanner = new Scanner(System.in);
        String newTitle;
        Assortment assortment;
        AssortmentDao assortmentDao = new AssortmentDaoImp();
        List<Assortment> assortmentList = assortmentDao.getAssortmentByType(assortmentType);

        System.out.println(assortmentService.getAllAssortmentByType(assortmentType));
        id = getValidIdFromUser(assortmentList.stream().map(Assortment::getId).collect(Collectors.toList()), inputMessage);

        try {
            assortment = assortmentList.stream().filter((item) -> item.getId() == id).findFirst().orElseThrow();

            System.out.print(" Enter new title: ");
            newTitle = scanner.nextLine();

            if(!newTitle.equalsIgnoreCase(assortment.getTitle())) {
                assortment.setTitle(newTitle);
                assortmentDao.update(assortment);

                return true;
            }
        }
        catch (NoSuchElementException e) {
            System.out.println(" Element with such id: " + id + " not exists!");
        }
        catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    private static void showOrdersByPersonId(String firstName, String lastName, List<Order> orders, long id) {
        System.out.println(" Orders for " + firstName + " " + lastName + ":\n");
        orders.forEach((order) -> {
            System.out.println(" Timestamp: " + order.getTimestamp());
            System.out.println(" Price: " + order.getPrice() + "$");
            System.out.println(" Price With Discount: " + order.getPriceWithDiscount() + "$\n");
        });
    }

    private static java.sql.Date getValidDateFromUser(String inputMessage) {
        Scanner scanner = new Scanner(System.in);
        ScheduleDao scheduleDao = new ScheduleDaoImp();

        try {
            System.out.println(scheduleService.getAllSchedules());

            System.out.print(inputMessage);
            return java.sql.Date.valueOf(scanner.nextLine());
        }
        catch (IllegalArgumentException e) {
            throw new RuntimeException(" Please enter correct data!");
        }
        catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
