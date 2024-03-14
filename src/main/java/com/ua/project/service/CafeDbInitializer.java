package com.ua.project.service;

import com.ua.project.dao.assortmentDAO.AssortmentDao;
import com.ua.project.dao.assortmentDAO.AssortmentDaoImp;
import com.ua.project.dao.assortment_typeDAO.AssortmentTypeDao;
import com.ua.project.dao.assortment_typeDAO.AssortmentTypeDaoImpl;
import com.ua.project.dao.clientDAO.ClientDao;
import com.ua.project.dao.clientDAO.ClientDaoImp;
import com.ua.project.dao.order_and_assortmentDAO.OrderAndAssortmentDao;
import com.ua.project.dao.order_and_assortmentDAO.OrderAndAssortmentDaoImp;
import com.ua.project.dao.ordersDAO.OrderDao;
import com.ua.project.dao.ordersDAO.OrderDaoImp;
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

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CafeDbInitializer {
    private static final Random RANDOM = new Random();

    public static void deleteAllRowsInDB() throws ConnectionDBException, SQLException {
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

    public static void createPositions() throws ConnectionDBException, SQLException {
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

    public static void createAssortmentTypes() throws ConnectionDBException, SQLException {
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

    public static void createRandomAssortment() throws ConnectionDBException, SQLException {
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

    public static void createRandomClients() throws FileException, ConnectionDBException, SQLException {
        final int COUNT_OF_RECORDS = 7;
        TxtFileReader firstNamesFileReader = new TxtFileReader("data.first_names");
        TxtFileReader lastNamesFileReader = new TxtFileReader("data.last_names");
        TxtFileReader patronymicsFileReader = new TxtFileReader("data.patronymics");
        List<String> firstNames = firstNamesFileReader.readFile();
        List<String> lastNames = lastNamesFileReader.readFile();
        List<String> patronymics = patronymicsFileReader.readFile();
        List<Client> clients = new ArrayList<Client>();
        ClientDao clientDao = new ClientDaoImp();

        for (int i = 0; i < COUNT_OF_RECORDS; i++) {
            int discount = RANDOM.nextInt(100);
            String contactPhone = String.valueOf(
                    RANDOM.nextInt(10) +
                    RANDOM.nextInt(10) +
                    "-" +
                    RANDOM.nextInt(10) +
                    RANDOM.nextInt(10));
            String contactEmail = "simplemail@mail.com " + RANDOM.nextInt(100);

            clients.add(Client.builder().firstName(firstNames.get(RANDOM.nextInt(firstNames.size())))
                    .lastName(lastNames.get(RANDOM.nextInt(lastNames.size())))
                    .patronymic(patronymics.get(RANDOM.nextInt(patronymics.size())))
                    .birthDate(Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date())))
                    .contactPhone(contactPhone)
                    .contactEmail(contactEmail)
                    .discount(discount)
                    .build());
        }

        clientDao.saveMany(clients);
    }

    public static void createRandomPersonal() throws FileException, ConnectionDBException, SQLException {
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

        personalDao.saveMany(personalList);
    }

    public static void createRandomPersonalEmailAddresses() throws ConnectionDBException, SQLException {
        PersonalDao personalDao = new PersonalDaoImp();
        PersonalEmailAddressDao personalEmailAddressDao = new PersonalEmailAddressDaoImp();
        List<Personal> personalList = personalDao.findAll();
        List<PersonalEmailAddress> personalEmailAddressList = new ArrayList<PersonalEmailAddress>();

        for (int i = 0; i < personalList.size(); i++) {
            StringBuilder emailAddress = new StringBuilder("Email address for personal " + personalList.get(i).getId());
            int randomCount = RANDOM.nextInt(2) + 1;

            for(int j = 0; j < randomCount; j++) {
                emailAddress.append(j);

                personalEmailAddressList.add(PersonalEmailAddress.builder()
                        .emailAddress(emailAddress.toString())
                        .personalId(personalList.get(i).getId())
                        .build());
            }
        }

        personalEmailAddressDao.saveMany(personalEmailAddressList);
    }

    public static void createRandomPersonalPhoneNumber() throws ConnectionDBException, SQLException {
        PersonalDao personalDao = new PersonalDaoImp();
        PersonalPhoneNumberDao personalPhoneNumberDao = new PersonalPhoneNumberDaoImp();
        List<Personal> personalList = personalDao.findAll();
        List<PersonalPhoneNumber> personalPhoneNumberList = new ArrayList<PersonalPhoneNumber>();

        for (int i = 0; i < personalList.size(); i++) {
            StringBuilder phoneNumber = new StringBuilder("Phone " + personalList.get(i).getId());
            int randomCount = RANDOM.nextInt(2) + 1;

            for(int j = 0; j < randomCount; j++) {
                phoneNumber.append(j);

                personalPhoneNumberList.add(PersonalPhoneNumber.builder()
                        .phoneNumber(phoneNumber.toString())
                        .personalId(personalList.get(i).getId())
                        .build());
            }
        }

        personalPhoneNumberDao.saveMany(personalPhoneNumberList);
    }
}
