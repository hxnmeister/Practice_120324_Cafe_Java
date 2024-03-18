package com.ua.project.dao.assortment_typeDAO;

import com.ua.project.model.AssortmentType;
import com.ua.project.service.CafeDbInitializer;
import com.ua.project.utils.DBTestData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.setProperty;
import static junit.framework.Assert.*;

public class AssortmentTypeDaoImpTest {
    private static final AssortmentTypeDao assortmentTypeDao = new AssortmentTypeDaoImpl();

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
    void save_ShouldInsertAssortmentTypeIntoTable_WhenCalled() {
        int actualSize;
        int expectedSize;
        List<AssortmentType> actualList;
        List<AssortmentType> expectedList = getExpectedAssortmentTypeList();
        AssortmentType insertData = AssortmentType.builder().id(3L).title("type1").build();

        expectedList.add(insertData);

        assortmentTypeDao.save(insertData);
        actualList = assortmentTypeDao.findAll();

        actualSize = actualList.size();
        expectedSize = expectedList.size();

        assertEquals(expectedSize, actualSize);
        assertEquals(expectedList, actualList);
    }

    @Test
    void findAll_ShouldReturnListOfAssortmentTypes_WhenCalled() {
        List<AssortmentType> expectedList = getExpectedAssortmentTypeList();
        List<AssortmentType> actualList = assortmentTypeDao.findAll();
        int actualSize = actualList.size();
        int expectedSize = expectedList.size();

        assertEquals(expectedSize, actualSize);
        assertEquals(expectedList, actualList);
    }

    @Test
    void saveMany_ShouldInsertListOfAssortmentTypeIntoTable_WhenCalled() {
        int actualSize;
        int expectedSize;
        List<AssortmentType> actualList;
        List<AssortmentType> expectedList = getExpectedAssortmentTypeList();
        List<AssortmentType> insertDataList = new ArrayList<AssortmentType>(List.of(
                AssortmentType.builder().id(3L).title("type1").build(),
                AssortmentType.builder().id(4L).title("type2").build(),
                AssortmentType.builder().id(5L).title("type3").build()
        ));

        expectedList.addAll(insertDataList);
        assortmentTypeDao.saveMany(insertDataList);
        actualList = assortmentTypeDao.findAll();

        actualSize = actualList.size();
        expectedSize = expectedList.size();

        assertEquals(expectedSize, actualSize);
        assertEquals(expectedList, actualList);
    }

    @Test
    void update_ShouldUpdateAssortmentTypeIntoTable_WhenCalled() {
        AssortmentType updateData = AssortmentType.builder().id(1L).title("typeOfDrink1").build();
        List<AssortmentType> actualList = assortmentTypeDao.findAll();
        List<AssortmentType> expectedList = getExpectedAssortmentTypeList();

        assertEquals(expectedList, actualList);
        assortmentTypeDao.update(updateData);
        actualList.clear();
        expectedList.remove(0);
        expectedList.add(updateData);
        actualList = assortmentTypeDao.findAll();

        assertEquals(expectedList, actualList);
    }

    @Test
    void delete_ShouldDeleteAssortmentTypeFromTable_WhenCalled() {
        List<AssortmentType> actualList = assortmentTypeDao.findAll();
        List<AssortmentType> expectedList = getExpectedAssortmentTypeList();

        assertEquals(expectedList, actualList);
        assortmentTypeDao.delete(expectedList.get(1));
        actualList.clear();
        expectedList.remove(1);
        actualList = assortmentTypeDao.findAll();

        assertEquals(expectedList, actualList);
    }

    @Test
    void deleteAll_ShouldDeleteAllAssortmentTypes_WhenCalled() {
        int actualSize;
        int expectedSize = 0;
        List<AssortmentType> actualList = assortmentTypeDao.findAll();
        List<AssortmentType> expectedList = getExpectedAssortmentTypeList();

        assertEquals(expectedList, actualList);
        assortmentTypeDao.deleteAll();
        actualList.clear();
        expectedList.clear();
        actualList = assortmentTypeDao.findAll();
        actualSize = actualList.size();

        assertEquals(expectedSize, actualSize);
        assertEquals(expectedList, actualList);
    }

    @Test
    void isAssortmentTypeAvailable_ShouldReturnTrue_WhenAssortmentTypeIsInTheTable() {
        assertTrue(assortmentTypeDao.isAssortmentTypeAvailable("drink"));
    }

    @Test
    void isAssortmentTypeAvailable_ShouldReturnFalse_WhenAssortmentTypeIsNotInTheTable() {
        assertFalse(assortmentTypeDao.isAssortmentTypeAvailable("randomType123"));
    }

    @Test
    void getAssortmentTypeByTitle_ShouldReturnAssortmentType_WhenRequiredTitleIsInTheTable() {
        AssortmentType expected = AssortmentType.builder().id(2L).title("drink").build();
        AssortmentType actual = assortmentTypeDao.getAssortmentTypeByTitle("drink");

        assertEquals(expected, actual);
    }

    @Test
    void getAssortmentTypeByTitle_ShouldReturnEmptyAssortmentType_WhenRequiredTitleIsNotInTheTable() {
        AssortmentType expected = AssortmentType.builder().build();
        AssortmentType actual = assortmentTypeDao.getAssortmentTypeByTitle("randomType123");

        assertEquals(expected, actual);
    }

    private static List<AssortmentType> getExpectedAssortmentTypeList() {
        return new ArrayList<AssortmentType>(List.of(
                AssortmentType.builder().id(1L).title("desert").build(),
                AssortmentType.builder().id(2L).title("drink").build()
        ));
    }
}
