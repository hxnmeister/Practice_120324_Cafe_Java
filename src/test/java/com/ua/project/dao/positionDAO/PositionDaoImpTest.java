package com.ua.project.dao.positionDAO;

import com.ua.project.model.Position;
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

public class PositionDaoImpTest {
    private static final PositionDao positionDao = new PositionDaoImp();

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
    void save_ShouldInsertPositionIntoTable_WhenCalled() {
        int actualSize;
        int expectedSize;
        List<Position> actualList;
        List<Position> expectedList = getExpectedPositionsList();
        Position insertData = Position.builder().id(4L).title("cleaner").build();

        expectedList.add(insertData);

        positionDao.save(insertData);
        actualList = positionDao.findAll();

        actualSize = actualList.size();
        expectedSize = expectedList.size();

        assertEquals(expectedSize, actualSize);
        assertEquals(expectedList, actualList);
    }

    @Test
    void findAll_ShouldReturnListOfPositions_WhenCalled() {
        List<Position> expectedList = getExpectedPositionsList();
        List<Position> actualList = positionDao.findAll();
        int actualSize = actualList.size();
        int expectedSize = expectedList.size();

        assertEquals(expectedSize, actualSize);
        assertEquals(expectedList, actualList);
    }

    @Test
    void saveMany_ShouldInsertListOfPositionsIntoTable_WhenCalled() {
        int actualSize;
        int expectedSize;
        List<Position> actualList;
        List<Position> expectedList = getExpectedPositionsList();
        List<Position> insertDataList = new ArrayList<Position>(List.of(
                Position.builder().id(4L).title("director").build(),
                Position.builder().id(5L).title("cleaner").build()
        ));

        expectedList.addAll(insertDataList);
        positionDao.saveMany(insertDataList);
        actualList = positionDao.findAll();

        actualSize = actualList.size();
        expectedSize = expectedList.size();

        assertEquals(expectedSize, actualSize);
        assertEquals(expectedList, actualList);
    }

    @Test
    void update_ShouldUpdatePositionsIntoTable_WhenCalled() {
        Position updateData = Position.builder().id(2L).title("conditer").build();
        List<Position> actualList = positionDao.findAll();
        List<Position> expectedList = getExpectedPositionsList();

        assertEquals(expectedList, actualList);
        positionDao.update(updateData);
        actualList.clear();
        expectedList.remove(1);
        expectedList.add(updateData);
        actualList = positionDao.findAll();

        assertEquals(expectedList, actualList);
    }

    @Test
    void delete_ShouldDeletePositionFromTable_WhenCalled() {
        List<Position> actualList = positionDao.findAll();
        List<Position> expectedList = getExpectedPositionsList();

        assertEquals(expectedList, actualList);
        positionDao.delete(expectedList.get(1));
        actualList.clear();
        expectedList.remove(1);
        actualList = positionDao.findAll();

        assertEquals(expectedList, actualList);
    }

    @Test
    void deleteAll_ShouldDeletePositions_WhenCalled() {
        int actualSize;
        int expectedSize = 0;
        List<Position> actualList = positionDao.findAll();
        List<Position> expectedList = getExpectedPositionsList();

        assertEquals(expectedList, actualList);
        positionDao.deleteAll();
        actualList.clear();
        expectedList.clear();
        actualList = positionDao.findAll();
        actualSize = actualList.size();

        assertEquals(expectedSize, actualSize);
        assertEquals(expectedList, actualList);
    }

    @Test
    void isPositionAvailable_ShouldReturnTrue_WhenPositionIsAvailable() {
        assertTrue(positionDao.isPositionAvailable("barista"));
    }

    @Test
    void isPositionAvailable_ShouldReturnFalse_WhenPositionIsNotAvailable() {
        assertFalse(positionDao.isPositionAvailable("cleaner"));
    }

    @Test
    void getPositionByTitle_ShouldReturnPosition_WhenTitleIsInTheTable() {
        Position actual = positionDao.getPositionByTitle("confectioner");
        Position expected = Position.builder().id(2L).title("confectioner").build();

        assertEquals(expected, actual);
    }

    private static List<Position> getExpectedPositionsList() {
        return new ArrayList<Position>(List.of(
                Position.builder().id(1L).title("waiter").build(),
                Position.builder().id(2L).title("confectioner").build(),
                Position.builder().id(3L).title("barista").build()
        ));
    }
}
