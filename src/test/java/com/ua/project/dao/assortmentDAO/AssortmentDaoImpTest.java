package com.ua.project.dao.assortmentDAO;

import com.ua.project.model.Assortment;
import com.ua.project.service.CafeDbInitializer;
import com.ua.project.utils.DBTestData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.setProperty;
import static junit.framework.Assert.assertEquals;

public class AssortmentDaoImpTest {
    private static final AssortmentDao assortmentDao = new AssortmentDaoImp();

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
    void save_ShouldInsertAssortmentIntoTable_WhenCalled() {
        int actualSize;
        int expectedSize;
        List<Assortment> actualList;
        List<Assortment> expectedList = getExpectedAssortmentList();
        Assortment insertData = Assortment.builder().id(6L).title("drink3").quantity(42).price(new BigDecimal("11.90")).assortmentTypeId(2).build();

        expectedList.add(insertData);

        assortmentDao.save(insertData);
        actualList = assortmentDao.findAll();

        actualSize = actualList.size();
        expectedSize = expectedList.size();

        assertEquals(expectedSize, actualSize);
        assertEquals(expectedList, actualList);
    }

    @Test
    void findAll_ShouldReturnListOfAssortment_WhenCalled() {
        List<Assortment> expectedList = getExpectedAssortmentList();
        List<Assortment> actualList = assortmentDao.findAll();
        int actualSize = actualList.size();
        int expectedSize = expectedList.size();

        assertEquals(expectedSize, actualSize);
        assertEquals(expectedList, actualList);
    }

    @Test
    void saveMany_ShouldInsertListOfAssortmentIntoTable_WhenCalled() {
        int actualSize;
        int expectedSize;
        List<Assortment> actualList;
        List<Assortment> expectedList = getExpectedAssortmentList();
        List<Assortment> insertDataList = new ArrayList<Assortment>(List.of(
                Assortment.builder().id(6L).title("drink22").quantity(76).price(new BigDecimal("43.10")).assortmentTypeId(2).build(),
                Assortment.builder().id(7L).title("desert47").quantity(33).price(new BigDecimal("76.20")).assortmentTypeId(1).build(),
                Assortment.builder().id(8L).title("drink87").quantity(12).price(new BigDecimal("9.30")).assortmentTypeId(2).build()
        ));

        expectedList.addAll(insertDataList);
        assortmentDao.saveMany(insertDataList);
        actualList = assortmentDao.findAll();

        actualSize = actualList.size();
        expectedSize = expectedList.size();

        assertEquals(expectedSize, actualSize);
        assertEquals(expectedList, actualList);
    }

    @Test
    void update_ShouldUpdateAssortmentIntoTable_WhenCalled() {
        Assortment updateData = Assortment.builder().id(3L).title("drink3").quantity(7).price(new BigDecimal("27.40")).assortmentTypeId(2).build();
        List<Assortment> actualList = assortmentDao.findAll();
        List<Assortment> expectedList = getExpectedAssortmentList();

        assertEquals(expectedList, actualList);
        assortmentDao.update(updateData);
        actualList.clear();
        expectedList.remove(2);
        expectedList.add(updateData);
        actualList = assortmentDao.findAll();

        assertEquals(expectedList, actualList);
    }

    @Test
    void delete_ShouldDeleteAssortmentFromTable_WhenCalled() {
        List<Assortment> actualList = assortmentDao.findAll();
        List<Assortment> expectedList = getExpectedAssortmentList();

        assertEquals(expectedList, actualList);
        assortmentDao.delete(expectedList.get(1));
        actualList.clear();
        expectedList.remove(1);
        actualList = assortmentDao.findAll();

        assertEquals(expectedList, actualList);
    }

    @Test
    void deleteAll_ShouldDeleteAllAssortment_WhenCalled() {
        int actualSize;
        int expectedSize = 0;
        List<Assortment> actualList = assortmentDao.findAll();
        List<Assortment> expectedList = getExpectedAssortmentList();

        assertEquals(expectedList, actualList);
        assortmentDao.deleteAll();
        actualList.clear();
        expectedList.clear();
        actualList = assortmentDao.findAll();
        actualSize = actualList.size();

        assertEquals(expectedSize, actualSize);
        assertEquals(expectedList, actualList);
    }

    @Test
    void changePriceByTypeAndTitle_ShouldUpdatePriceByTypeAndTitleOfAssortment_WhenCalled() {
        Assortment updatedValue;
        List<Assortment> actualList = assortmentDao.findAll();
        List<Assortment> expectedList = getExpectedAssortmentList();

        assertEquals(expectedList, actualList);
        assortmentDao.changePriceByTypeAndTitle("desert", "desert1", new BigDecimal("13.20"));
        updatedValue = expectedList.get(1);
        updatedValue.setPrice(new BigDecimal("13.20"));
        expectedList.remove(1);
        expectedList.add(updatedValue);

        actualList.clear();
        actualList = assortmentDao.findAll();

        assertEquals(expectedList, actualList);
    }

    @Test
    void deleteAssortmentByTypeAndTitle_ShouldDeleteAssortmentByTypeAndTitleOfAssortment_WhenCalled() {
        int actualSize;
        int expectedSize;
        List<Assortment> actualList = assortmentDao.findAll();
        List<Assortment> expectedList = getExpectedAssortmentList();

        assertEquals(expectedList, actualList);
        assortmentDao.deleteAssortmentByTypeAndTitle("drink", "drink2");
        expectedList.remove(3);
        actualList.clear();
        actualList = assortmentDao.findAll();

        expectedSize = expectedList.size();
        actualSize = actualList.size();

        assertEquals(expectedSize, actualSize);
        assertEquals(expectedList, actualList);
    }

    @Test
    void getAssortmentByType_ShouldReturnAssortmentList_WhenTypeIsInTheTable() {
        int actualSize;
        int expectedSize;
        List<Assortment> actualList = assortmentDao.getAssortmentByType("desert");
        List<Assortment> expectedList = new ArrayList<Assortment>(List.of(
                Assortment.builder().id(2L).title("desert1").quantity(13).price(new BigDecimal("10.50")).assortmentTypeId(1).build(),
                Assortment.builder().id(3L).title("desert2").quantity(4).price(new BigDecimal("11.20")).assortmentTypeId(1).build(),
                Assortment.builder().id(5L).title("desert3").quantity(2).price(new BigDecimal("8.30")).assortmentTypeId(1).build()
        ));

        actualSize = actualList.size();
        expectedSize = expectedList.size();

        assertEquals(expectedSize, actualSize);
        assertEquals(expectedList, actualList);
    }

    private static List<Assortment> getExpectedAssortmentList() {
        return new ArrayList<Assortment>(List.of(
                Assortment.builder().id(1L).title("drink1").quantity(10).price(new BigDecimal("12.30")).assortmentTypeId(2).build(),
                Assortment.builder().id(2L).title("desert1").quantity(13).price(new BigDecimal("10.50")).assortmentTypeId(1).build(),
                Assortment.builder().id(3L).title("desert2").quantity(4).price(new BigDecimal("11.20")).assortmentTypeId(1).build(),
                Assortment.builder().id(4L).title("drink2").quantity(5).price(new BigDecimal("10.20")).assortmentTypeId(2).build(),
                Assortment.builder().id(5L).title("desert3").quantity(2).price(new BigDecimal("8.30")).assortmentTypeId(1).build()
        ));
    }
}
