package com.ua.project.service;

import com.ua.project.dao.ConnectionFactory;
import com.ua.project.dao.assortmentDAO.AssortmentDao;
import com.ua.project.dao.assortmentDAO.AssortmentDaoImp;
import com.ua.project.dao.assortment_typeDAO.AssortmentTypeDao;
import com.ua.project.dao.assortment_typeDAO.AssortmentTypeDaoImpl;
import com.ua.project.dao.clientDAO.ClientDao;
import com.ua.project.dao.clientDAO.ClientDaoImp;
import com.ua.project.dao.order_and_assortmentDAO.OrderAndAssortmentDao;
import com.ua.project.dao.order_and_assortmentDAO.OrderAndAssortmentDaoImp;
import com.ua.project.dao.orderDAO.OrderDao;
import com.ua.project.dao.orderDAO.OrderDaoImp;
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
import com.ua.project.exception.FileException;
import com.ua.project.model.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.TemporalField;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CafeDbInitializer {
    private static final Random RANDOM = new Random();
    private static final List<String> TABLES_NAME_ARRAY;
    private static final String SQL_CREATE_TABLES;
    private static final String SQL_DROP_TABLES;

    static {
        final String TABLES_NAMES = PropertyFactory.getInstance().getProperty().getProperty("db.tablesNames");

        SQL_CREATE_TABLES = PropertyFactory.getInstance().getProperty().getProperty("db.sqlCreateTables");
        SQL_DROP_TABLES = PropertyFactory.getInstance().getProperty().getProperty("db.sqlDropTables");
        TABLES_NAME_ARRAY = Arrays.stream(TABLES_NAMES.split(",")).collect(Collectors.toList());
    }

    public static void createTables() {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection()) {
            for (String tableName : TABLES_NAME_ARRAY) {
                try (Stream<String> stringStream = Files.lines(Paths.get(SQL_CREATE_TABLES))) {
                    StringBuilder createTablesQuery = new StringBuilder();

                    for (String currentString : stringStream.collect(Collectors.toList())) {
                        createTablesQuery.append(currentString).append(" ");
                    }

                    try (Statement statement = connection.createStatement()) {
                        statement.execute(createTablesQuery.toString());
                    }
                }
            }
        }
        catch (ConnectionDBException e) {
            System.out.println(" Unable connect to DB!");
        }
        catch (IOException e) {
            System.out.println(" Error with creating table script!");
        }
        catch (SQLException e) {
            System.out.println(" During connection to DB an error occurred!");
        }
    }

    public static void deleteAllRowsInDB() {
        OrderDao orderDao = new OrderDaoImp();
        ClientDao clientDao = new ClientDaoImp();
        PersonalDao personalDao = new PersonalDaoImp();
        PositionDao positionDao = new PositionDaoImp();
        ScheduleDao scheduleDao = new ScheduleDaoImp();
        AssortmentDao assortmentDao = new AssortmentDaoImp();
        AssortmentTypeDao assortmentTypeDao = new AssortmentTypeDaoImpl();
        OrderAndAssortmentDao orderAndAssortmentDao = new OrderAndAssortmentDaoImp();
        PersonalPhoneNumberDao personalPhoneNumberDao = new PersonalPhoneNumberDaoImp();
        PersonalEmailAddressDao personalEmailAddressDao = new PersonalEmailAddressDaoImp();

        orderDao.deleteAll();
        clientDao.deleteAll();
        personalDao.deleteAll();
        positionDao.deleteAll();
        scheduleDao.deleteAll();
        assortmentDao.deleteAll();
        assortmentTypeDao.deleteAll();
        orderAndAssortmentDao.deleteAll();
        personalPhoneNumberDao.deleteAll();
        personalEmailAddressDao.deleteAll();
    }

    public static void dropAllTablesInDB() {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             Stream<String> stringStream = Files.lines(Paths.get(SQL_DROP_TABLES))) {

            StringBuilder dropTablesQuery = new StringBuilder();

            for (String currentString : stringStream.collect(Collectors.toList())) {
                dropTablesQuery.append(currentString).append(" ");
            }

            try (Statement statement = connection.createStatement()) {
                statement.execute(dropTablesQuery.toString());
            }
        }
        catch (ConnectionDBException e) {
            System.out.println(" Unable connect to DB!");
        }
        catch (IOException e) {
            System.out.println(" Error with creating table script!");
        }
        catch (SQLException e) {
            System.out.println(" During connection to DB an error occurred!");
        }
    }

    public static void createPositions() {
        PositionDao positionDao = new PositionDaoImp();
        List<Position> positions = new ArrayList<Position>();

        if(!positionDao.isPositionAvailable("waiter")) {
            positions.add(Position.builder().title("waiter").build());
        }
        if(!positionDao.isPositionAvailable("confectioner")) {
            positions.add(Position.builder().title("confectioner").build());
        }
        if(!positionDao.isPositionAvailable("barista")) {
            positions.add(Position.builder().title("barista").build());
        }
        if(!positions.isEmpty()) {
            positionDao.saveMany(positions);
        }
    }

    public static void createAssortmentTypes() {
        AssortmentTypeDao assortmentTypeDao = new AssortmentTypeDaoImpl();
        List<AssortmentType> assortmentTypeList = new ArrayList<AssortmentType>();

        if(!assortmentTypeDao.isAssortmentTypeAvailable("desert")) {
            assortmentTypeList.add(AssortmentType.builder().title("desert").build());
        }
        if(!assortmentTypeDao.isAssortmentTypeAvailable("drink")) {
            assortmentTypeList.add(AssortmentType.builder().title("drink").build());
        }
        if(!assortmentTypeList.isEmpty()) {
            assortmentTypeDao.saveMany(assortmentTypeList);
        }
    }

    public static void createRandomAssortment() {
        final int COUNT_OF_RECORDS = 5;
        AssortmentDao assortmentDao = new AssortmentDaoImp();
        AssortmentTypeDao assortmentTypeDao = new AssortmentTypeDaoImpl();
        List<Assortment> assortmentList = new ArrayList<Assortment>();
        List<AssortmentType> assortmentTypeList = assortmentTypeDao.findAll();

        for (int i = 0; i < COUNT_OF_RECORDS; i++) {
            int quantity = RANDOM.nextInt(100);
            long assortmentTypeId = assortmentTypeList.get(RANDOM.nextInt(assortmentTypeList.size())).getId();
            String title = "Assortment " + RANDOM.nextInt(200);
            BigDecimal price = BigDecimal.valueOf(RANDOM.nextDouble() * 10);

            assortmentList.add(Assortment.builder()
                    .title(title)
                    .quantity(quantity)
                    .price(price)
                    .assortmentTypeId(assortmentTypeId)
                    .build());
        }

        assortmentDao.saveMany(assortmentList);
    }

    public static void createRandomClients() {
        try {
            TxtFileReader firstNamesFileReader = new TxtFileReader("data.first_names");
            TxtFileReader lastNamesFileReader = new TxtFileReader("data.last_names");
            TxtFileReader patronymicsFileReader = new TxtFileReader("data.patronymics");
            List<String> firstNames = firstNamesFileReader.readFile();
            List<String> lastNames = lastNamesFileReader.readFile();
            List<String> patronymics = patronymicsFileReader.readFile();
            ClientDao clientDao = new ClientDaoImp();
            List<Client> clients = new ArrayList<Client>(List.of(
                    Client.builder().firstName(firstNames.get(RANDOM.nextInt(firstNames.size())))
                            .lastName(lastNames.get(RANDOM.nextInt(lastNames.size())))
                            .patronymic(patronymics.get(RANDOM.nextInt(patronymics.size())))
                            .birthDate(Date.valueOf("2012-03-25"))
                            .contactPhone(String.valueOf(
                                    RANDOM.nextInt(10) +
                                    RANDOM.nextInt(10) +
                                    "-" +
                                    RANDOM.nextInt(10) +
                                    RANDOM.nextInt(10)))
                            .contactEmail("simplemail@mail.com" + RANDOM.nextInt(100))
                            .discount(RANDOM.nextInt(100))
                            .build(),
                    Client.builder().firstName(firstNames.get(RANDOM.nextInt(firstNames.size())))
                            .lastName(lastNames.get(RANDOM.nextInt(lastNames.size())))
                            .patronymic(patronymics.get(RANDOM.nextInt(patronymics.size())))
                            .birthDate(Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date())))
                            .contactPhone(String.valueOf(
                                    RANDOM.nextInt(10) +
                                    RANDOM.nextInt(10) +
                                    "-" +
                                    RANDOM.nextInt(10) +
                                    RANDOM.nextInt(10)))
                            .contactEmail("")
                            .discount(RANDOM.nextInt(100))
                            .build(),
                    Client.builder().firstName(firstNames.get(RANDOM.nextInt(firstNames.size())))
                            .lastName(lastNames.get(RANDOM.nextInt(lastNames.size())))
                            .patronymic(patronymics.get(RANDOM.nextInt(patronymics.size())))
                            .birthDate(Date.valueOf("2000-01-21"))
                            .contactPhone(String.valueOf(
                                    RANDOM.nextInt(10) +
                                    RANDOM.nextInt(10) +
                                    "-" +
                                    RANDOM.nextInt(10) +
                                    RANDOM.nextInt(10)))
                            .contactEmail("simplemail@mail.com" + RANDOM.nextInt(100))
                            .discount(RANDOM.nextInt(100))
                            .build(),
                    Client.builder().firstName(firstNames.get(RANDOM.nextInt(firstNames.size())))
                            .lastName(lastNames.get(RANDOM.nextInt(lastNames.size())))
                            .patronymic(patronymics.get(RANDOM.nextInt(patronymics.size())))
                            .birthDate(Date.valueOf("1998-10-04"))
                            .contactPhone(String.valueOf(
                                    RANDOM.nextInt(10) +
                                    RANDOM.nextInt(10) +
                                    "-" +
                                    RANDOM.nextInt(10) +
                                    RANDOM.nextInt(10)))
                            .contactEmail("")
                            .discount(RANDOM.nextInt(100))
                            .build(),
                    Client.builder().firstName(firstNames.get(RANDOM.nextInt(firstNames.size())))
                            .lastName(lastNames.get(RANDOM.nextInt(lastNames.size())))
                            .patronymic(patronymics.get(RANDOM.nextInt(patronymics.size())))
                            .birthDate(Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date())))
                            .contactPhone(String.valueOf(
                                    RANDOM.nextInt(10) +
                                    RANDOM.nextInt(10) +
                                    "-" +
                                    RANDOM.nextInt(10) +
                                    RANDOM.nextInt(10)))
                            .contactEmail("simplemail@mail.com" + RANDOM.nextInt(100))
                            .discount(RANDOM.nextInt(100))
                            .build()

            ));

            clientDao.saveMany(clients);
        }
        catch (FileException | RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createRandomPersonal() {
        try {
            final int COUNT_OF_RECORDS = 5;
            TxtFileReader firstNamesFileReader = new TxtFileReader("data.first_names");
            TxtFileReader lastNamesFileReader = new TxtFileReader("data.last_names");
            TxtFileReader patronymicsFileReader = new TxtFileReader("data.patronymics");
            PersonalDao personalDao = new PersonalDaoImp();
            PositionDao positionDao = new PositionDaoImp();
            List<String> firstNames = firstNamesFileReader.readFile();
            List<String> lastNames = lastNamesFileReader.readFile();
            List<String> patronymics = patronymicsFileReader.readFile();
            List<Personal> personalList = new ArrayList<Personal>();
            List<Position> positions = positionDao.findAll();

            for (int i = 0; i < COUNT_OF_RECORDS; i++) {

                personalList.add(Personal.builder().firstName(firstNames.get(RANDOM.nextInt(firstNames.size())))
                        .lastName(lastNames.get(RANDOM.nextInt(lastNames.size())))
                        .patronymic(patronymics.get(RANDOM.nextInt(patronymics.size())))
                        .positionId(positions.get(RANDOM.nextInt(positions.size())).getId())
                        .build());
            }

            personalList.add(Personal.builder().firstName(firstNames.get(RANDOM.nextInt(firstNames.size())))
                    .lastName(lastNames.get(RANDOM.nextInt(lastNames.size())))
                    .patronymic(patronymics.get(RANDOM.nextInt(patronymics.size())))
                    .positionId(1)
                    .build());
            personalList.add(Personal.builder().firstName(firstNames.get(RANDOM.nextInt(firstNames.size())))
                    .lastName(lastNames.get(RANDOM.nextInt(lastNames.size())))
                    .patronymic(patronymics.get(RANDOM.nextInt(patronymics.size())))
                    .positionId(2)
                    .build());
            personalList.add(Personal.builder().firstName(firstNames.get(RANDOM.nextInt(firstNames.size())))
                    .lastName(lastNames.get(RANDOM.nextInt(lastNames.size())))
                    .patronymic(patronymics.get(RANDOM.nextInt(patronymics.size())))
                    .positionId(3)
                    .build());

            personalDao.saveMany(personalList);
        }
        catch (FileException | RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createRandomPersonalEmailAddresses() {
        PersonalDao personalDao = new PersonalDaoImp();
        PersonalEmailAddressDao personalEmailAddressDao = new PersonalEmailAddressDaoImp();
        List<Personal> personalList = personalDao.findAll();
        List<PersonalEmailAddress> personalEmailAddressList = new ArrayList<PersonalEmailAddress>();

        for (Personal personal : personalList) {
            StringBuilder emailAddress = new StringBuilder("Email address for personal " + personal.getId());
            int randomCount = RANDOM.nextInt(2) + 1;

            for (int j = 0; j < randomCount; j++) {
                emailAddress.append(j);

                personalEmailAddressList.add(PersonalEmailAddress.builder()
                        .emailAddress(emailAddress.toString())
                        .personalId(personal.getId())
                        .build());
            }
        }

        personalEmailAddressDao.saveMany(personalEmailAddressList);
    }

    public static void createRandomPersonalPhoneNumber() {
        PersonalDao personalDao = new PersonalDaoImp();
        PersonalPhoneNumberDao personalPhoneNumberDao = new PersonalPhoneNumberDaoImp();
        List<Personal> personalList = personalDao.findAll();
        List<PersonalPhoneNumber> personalPhoneNumberList = new ArrayList<PersonalPhoneNumber>();

        for (Personal personal : personalList) {
            StringBuilder phoneNumber = new StringBuilder("Phone " + personal.getId());
            int randomCount = RANDOM.nextInt(2) + 1;

            for (int j = 0; j < randomCount; j++) {
                phoneNumber.append(j);

                personalPhoneNumberList.add(PersonalPhoneNumber.builder()
                        .phoneNumber(phoneNumber.toString())
                        .personalId(personal.getId())
                        .build());
            }
        }

        personalPhoneNumberDao.saveMany(personalPhoneNumberList);
    }

    public static void createRandomSchedules() {
        Random random = new Random();
        ScheduleDao scheduleDao = new ScheduleDaoImp();
        PersonalDao personalDao = new PersonalDaoImp();
        Calendar calendar = new GregorianCalendar();
        List<Personal> personalList = personalDao.findAll();
        List<Schedule> scheduleList = new ArrayList<>(List.of(
                Schedule.builder().workDate(new Date(calendar.getTimeInMillis())).workHoursBegin(Time.valueOf("11:15:00")).workHoursEnd(Time.valueOf("19:44:00")).personalId(personalList.get(random.nextInt(personalList.size())).getId()).build(),
                Schedule.builder().workDate(new Date(calendar.getTimeInMillis())).workHoursBegin(Time.valueOf("11:15:00")).workHoursEnd(Time.valueOf("19:44:00")).personalId(personalList.get(random.nextInt(personalList.size())).getId()).build(),
                Schedule.builder().workDate(new Date(calendar.getTimeInMillis())).workHoursBegin(Time.valueOf("11:15:00")).workHoursEnd(Time.valueOf("19:44:00")).personalId(personalList.get(random.nextInt(personalList.size())).getId()).build(),
                Schedule.builder().workDate(new Date(calendar.getTimeInMillis())).workHoursBegin(Time.valueOf("11:15:00")).workHoursEnd(Time.valueOf("19:44:00")).personalId(personalList.get(random.nextInt(personalList.size())).getId()).build()
        ));

        scheduleList.forEach(scheduleDao::save);
    }

    public static void createRandomOrders() {
        Random random = new Random();
        ClientDao clientDao = new ClientDaoImp();
        PersonalDao personalDao = new PersonalDaoImp();
        OrderDao orderDao = new OrderDaoImp();
        List<Client> clients = clientDao.findAll();
        List<Personal> personalList = personalDao.findAll();
        List<Order> orders = new ArrayList<>(List.of(
                Order.builder().price(BigDecimal.valueOf(random.nextDouble() * 100)).priceWithDiscount(BigDecimal.valueOf(random.nextDouble() * 10)).timestamp(Timestamp.valueOf(LocalDateTime.now())).personalId(personalList.get(random.nextInt(personalList.size())).getId()).clientId(clients.get(random.nextInt(clients.size())).getId()).build(),
                Order.builder().price(BigDecimal.valueOf(random.nextDouble() * 100)).priceWithDiscount(BigDecimal.valueOf(random.nextDouble() * 10)).timestamp(Timestamp.valueOf("2024-02-01 10:00:00")).personalId(personalList.get(random.nextInt(personalList.size())).getId()).clientId(clients.get(random.nextInt(clients.size())).getId()).build(),
                Order.builder().price(BigDecimal.valueOf(random.nextDouble() * 100)).priceWithDiscount(BigDecimal.valueOf(random.nextDouble() * 10)).timestamp(Timestamp.valueOf(LocalDateTime.now())).personalId(personalList.get(random.nextInt(personalList.size())).getId()).clientId(clients.get(random.nextInt(clients.size())).getId()).build(),
                Order.builder().price(BigDecimal.valueOf(random.nextDouble() * 100)).priceWithDiscount(BigDecimal.valueOf(random.nextDouble() * 10)).timestamp(Timestamp.valueOf("2024-01-21 19:33:00")).personalId(personalList.get(random.nextInt(personalList.size())).getId()).clientId(clients.get(random.nextInt(clients.size())).getId()).build(),
                Order.builder().price(BigDecimal.valueOf(random.nextDouble() * 100)).priceWithDiscount(BigDecimal.valueOf(random.nextDouble() * 10)).timestamp(Timestamp.valueOf("2024-02-05 15:11:00")).personalId(personalList.get(random.nextInt(personalList.size())).getId()).clientId(clients.get(random.nextInt(clients.size())).getId()).build()
        ));

        orderDao.saveMany(orders);
    }

    public static void createRandomAssortmentAndOrders() {
        Random random = new Random();
        OrderAndAssortmentDao orderAndAssortmentDao = new OrderAndAssortmentDaoImp();
        AssortmentDao assortmentDao = new AssortmentDaoImp();
        OrderDao orderDao = new OrderDaoImp();
        List<Order> orders = orderDao.findAll();
        List<Assortment> assortmentList = assortmentDao.findAll();
        List<OrderAndAssortment> orderAndAssortments = new ArrayList<>(List.of(
                OrderAndAssortment.builder().orderId(orders.get(random.nextInt(orders.size())).getId()).assortmentId(assortmentList.get(random.nextInt(assortmentList.size())).getId()).build(),
                OrderAndAssortment.builder().orderId(orders.get(random.nextInt(orders.size())).getId()).assortmentId(assortmentList.get(random.nextInt(assortmentList.size())).getId()).build(),
                OrderAndAssortment.builder().orderId(orders.get(random.nextInt(orders.size())).getId()).assortmentId(assortmentList.get(random.nextInt(assortmentList.size())).getId()).build(),
                OrderAndAssortment.builder().orderId(orders.get(random.nextInt(orders.size())).getId()).assortmentId(assortmentList.get(random.nextInt(assortmentList.size())).getId()).build(),
                OrderAndAssortment.builder().orderId(orders.get(random.nextInt(orders.size())).getId()).assortmentId(assortmentList.get(random.nextInt(assortmentList.size())).getId()).build(),
                OrderAndAssortment.builder().orderId(orders.get(random.nextInt(orders.size())).getId()).assortmentId(assortmentList.get(random.nextInt(assortmentList.size())).getId()).build(),
                OrderAndAssortment.builder().orderId(orders.get(random.nextInt(orders.size())).getId()).assortmentId(assortmentList.get(random.nextInt(assortmentList.size())).getId()).build(),
                OrderAndAssortment.builder().orderId(orders.get(random.nextInt(orders.size())).getId()).assortmentId(assortmentList.get(random.nextInt(assortmentList.size())).getId()).build(),
                OrderAndAssortment.builder().orderId(orders.get(random.nextInt(orders.size())).getId()).assortmentId(assortmentList.get(random.nextInt(assortmentList.size())).getId()).build(),
                OrderAndAssortment.builder().orderId(orders.get(random.nextInt(orders.size())).getId()).assortmentId(assortmentList.get(random.nextInt(assortmentList.size())).getId()).build(),
                OrderAndAssortment.builder().orderId(orders.get(random.nextInt(orders.size())).getId()).assortmentId(assortmentList.get(random.nextInt(assortmentList.size())).getId()).build()
        ));

        orderAndAssortments.forEach(orderAndAssortmentDao::save);
    }
}
