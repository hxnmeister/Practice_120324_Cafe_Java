package com.ua.project.dao.orderDAO;

import com.ua.project.dao.clientDAO.ClientDao;
import com.ua.project.dao.clientDAO.ClientDaoImp;
import com.ua.project.model.Client;
import com.ua.project.model.Order;
import com.ua.project.service.CafeDbInitializer;
import com.ua.project.utils.DBTestData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.setProperty;
import static junit.framework.Assert.assertEquals;

public class OrderDaoImpTest {
    private static final OrderDao orderDao = new OrderDaoImp();

    @BeforeAll
    static void initCafeTestDB() {
        setProperty("test", "true");
        CafeDbInitializer.createTables();
    }

    @BeforeEach
    void initData() {
        DBTestData.insert();
    }

    @AfterEach
    void deleteData() {
        CafeDbInitializer.createTables();
    }

    @Test
    void save_ShouldInsertOrderIntoTable_WhenCalled() {
        int actualSize;
        int expectedSize;
        List<Order> actualList;
        List<Order> expectedList = getExpectedOrdersList();
        Order insertData = Order.builder().id(4L).price(new BigDecimal("887.10")).priceWithDiscount(new BigDecimal("880.50"))
                .timestamp(Timestamp.valueOf("2024-01-11 10:15:00")).personalId(4).clientId(2).build();

        expectedList.add(insertData);

        orderDao.save(insertData);
        actualList = orderDao.findAll();

        actualSize = actualList.size();
        expectedSize = expectedList.size();

        assertEquals(expectedSize, actualSize);
        assertEquals(expectedList, actualList);
    }

    @Test
    void findAll_ShouldReturnListOfOrders_WhenCalled() {
        List<Order> expectedList = getExpectedOrdersList();
        List<Order> actualList = orderDao.findAll();
        int actualSize = actualList.size();
        int expectedSize = expectedList.size();

        assertEquals(expectedSize, actualSize);
        assertEquals(expectedList, actualList);
    }

    @Test
    void saveMany_ShouldInsertListOfOrdersIntoTable_WhenCalled() {
        int actualSize;
        int expectedSize;
        List<Order> actualList;
        List<Order> expectedList = getExpectedOrdersList();
        List<Order> insertDataList = new ArrayList<Order>(List.of(
                Order.builder().id(4L).price(new BigDecimal("673.15")).priceWithDiscount(new BigDecimal("673.10"))
                        .timestamp(Timestamp.valueOf("2024-01-01 09:25:00")).personalId(1).clientId(2).build(),
                Order.builder().id(5L).price(new BigDecimal("115.20")).priceWithDiscount(new BigDecimal("100.85"))
                        .timestamp(Timestamp.valueOf("2024-02-09 16:18:00")).personalId(4).clientId(2).build()
        ));

        expectedList.addAll(insertDataList);
        orderDao.saveMany(insertDataList);
        actualList = orderDao.findAll();

        actualSize = actualList.size();
        expectedSize = expectedList.size();

        assertEquals(expectedSize, actualSize);
        assertEquals(expectedList, actualList);
    }

    @Test
    void update_ShouldUpdateOrdersIntoTable_WhenCalled() {
        Order updateData = Order.builder().id(2L).price(new BigDecimal("999.99")).priceWithDiscount(new BigDecimal("888.88"))
                .timestamp(Timestamp.valueOf("2024-02-10 12:44:00")).personalId(5).clientId(4).build();
        List<Order> actualList = orderDao.findAll();
        List<Order> expectedList = getExpectedOrdersList();

        assertEquals(expectedList, actualList);
        orderDao.update(updateData);
        actualList.clear();
        expectedList.remove(1);
        expectedList.add(updateData);
        actualList = orderDao.findAll();

        assertEquals(expectedList, actualList);
    }

    @Test
    void delete_ShouldDeleteOrderFromTable_WhenCalled() {
        List<Order> actualList = orderDao.findAll();
        List<Order> expectedList = getExpectedOrdersList();

        assertEquals(expectedList, actualList);
        orderDao.delete(expectedList.get(1));
        actualList.clear();
        expectedList.remove(1);
        actualList = orderDao.findAll();

        assertEquals(expectedList, actualList);
    }

    @Test
    void deleteAll_ShouldDeleteOrders_WhenCalled() {
        int actualSize;
        int expectedSize = 0;
        List<Order> actualList = orderDao.findAll();
        List<Order> expectedList = getExpectedOrdersList();

        assertEquals(expectedList, actualList);
        orderDao.deleteAll();
        actualList.clear();
        expectedList.clear();
        actualList = orderDao.findAll();
        actualSize = actualList.size();

        assertEquals(expectedSize, actualSize);
        assertEquals(expectedList, actualList);
    }

    private static List<Order> getExpectedOrdersList() {
        return new ArrayList<Order>(List.of(
                Order.builder().id(1L).price(new BigDecimal("123.30")).priceWithDiscount(new BigDecimal("123.30"))
                        .timestamp(Timestamp.valueOf("2024-02-12 12:30:00")).personalId(2).clientId(1).build(),
                Order.builder().id(2L).price(new BigDecimal("267.30")).priceWithDiscount(new BigDecimal("260.80"))
                        .timestamp(Timestamp.valueOf("2024-02-10 11:42:00")).personalId(5).clientId(4).build(),
                Order.builder().id(3L).price(new BigDecimal("542.90")).priceWithDiscount(new BigDecimal("500.50"))
                        .timestamp(Timestamp.valueOf("2024-03-16 16:21:00")).personalId(3).clientId(2).build()
        ));
    }
}
