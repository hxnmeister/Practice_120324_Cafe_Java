package com.ua.project.dao.scheduleDAO;

import com.ua.project.dao.positionDAO.PositionDao;
import com.ua.project.dao.positionDAO.PositionDaoImp;
import com.ua.project.model.Position;
import com.ua.project.model.Schedule;
import com.ua.project.service.CafeDbInitializer;
import com.ua.project.utils.DBTestData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.setProperty;
import static junit.framework.Assert.*;
import static junit.framework.Assert.assertEquals;

public class ScheduleDaoImpTest {
    private static final ScheduleDao scheduleDao = new ScheduleDaoImp();

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
    void save_ShouldInsertScheduleIntoTable_WhenCalled() {
        int actualSize;
        int expectedSize;
        List<Schedule> actualList;
        List<Schedule> expectedList = getExpectedScheduleList();
        Schedule insertData = Schedule.builder().id(5L).workDate(Date.valueOf("2024-05-05")).workHoursBegin(Time.valueOf("09:32:00"))
                .workHoursEnd(Time.valueOf("19:42:00")).personalId(5).build();

        expectedList.add(insertData);

        scheduleDao.save(insertData);
        actualList = scheduleDao.findAll();

        actualSize = actualList.size();
        expectedSize = expectedList.size();

        assertEquals(expectedSize, actualSize);
        assertEquals(expectedList, actualList);
    }

    @Test
    void findAll_ShouldReturnListOfSchedules_WhenCalled() {
        List<Schedule> expectedList = getExpectedScheduleList();
        List<Schedule> actualList = scheduleDao.findAll();
        int actualSize = actualList.size();
        int expectedSize = expectedList.size();

        assertEquals(expectedSize, actualSize);
        assertEquals(expectedList, actualList);
    }

    @Test
    void saveMany_ShouldInsertListOfSchedulesIntoTable_WhenCalled() {
        int actualSize;
        int expectedSize;
        List<Schedule> actualList;
        List<Schedule> expectedList = getExpectedScheduleList();
        List<Schedule> insertDataList = new ArrayList<Schedule>(List.of(
                Schedule.builder().id(5L).workDate(Date.valueOf("2024-05-02")).workHoursBegin(Time.valueOf("12:44:00"))
                        .workHoursEnd(Time.valueOf("22:50:00")).personalId(4).build(),
                Schedule.builder().id(6L).workDate(Date.valueOf("2024-05-20")).workHoursBegin(Time.valueOf("19:05:00"))
                        .workHoursEnd(Time.valueOf("00:15:00")).personalId(5).build()
        ));

        expectedList.addAll(insertDataList);
        scheduleDao.saveMany(insertDataList);
        actualList = scheduleDao.findAll();

        actualSize = actualList.size();
        expectedSize = expectedList.size();

        assertEquals(expectedSize, actualSize);
        assertEquals(expectedList, actualList);
    }

    @Test
    void update_ShouldUpdateScheduleIntoTable_WhenCalled() {
        Schedule updateData = Schedule.builder().id(2L).workDate(Date.valueOf("2024-04-22")).workHoursBegin(Time.valueOf("11:15:00"))
                .workHoursEnd(Time.valueOf("21:48:00")).personalId(3).build();
        List<Schedule> actualList = scheduleDao.findAll();
        List<Schedule> expectedList = getExpectedScheduleList();

        assertEquals(expectedList, actualList);
        scheduleDao.update(updateData);
        actualList.clear();
        expectedList.remove(1);
        expectedList.add(updateData);
        actualList = scheduleDao.findAll();

        assertEquals(expectedList, actualList);
    }

    @Test
    void delete_ShouldDeleteScheduleFromTable_WhenCalled() {
        List<Schedule> actualList = scheduleDao.findAll();
        List<Schedule> expectedList = getExpectedScheduleList();

        assertEquals(expectedList, actualList);
        scheduleDao.delete(expectedList.get(1));
        actualList.clear();
        expectedList.remove(1);
        actualList = scheduleDao.findAll();

        assertEquals(expectedList, actualList);
    }

    @Test
    void deleteAll_ShouldDeleteSchedules_WhenCalled() {
        int actualSize;
        int expectedSize = 0;
        List<Schedule> actualList = scheduleDao.findAll();
        List<Schedule> expectedList = getExpectedScheduleList();

        assertEquals(expectedList, actualList);
        scheduleDao.deleteAll();
        actualList.clear();
        expectedList.clear();
        actualList = scheduleDao.findAll();
        actualSize = actualList.size();

        assertEquals(expectedSize, actualSize);
        assertEquals(expectedList, actualList);
    }

    private static List<Schedule> getExpectedScheduleList() {
        return new ArrayList<Schedule>(List.of(
                Schedule.builder().id(1L).workDate(Date.valueOf("2024-04-02")).workHoursBegin(Time.valueOf("13:00:00"))
                        .workHoursEnd(Time.valueOf("23:00:00")).personalId(1).build(),
                Schedule.builder().id(2L).workDate(Date.valueOf("2024-04-12")).workHoursBegin(Time.valueOf("10:20:00"))
                        .workHoursEnd(Time.valueOf("20:40:00")).personalId(3).build(),
                Schedule.builder().id(3L).workDate(Date.valueOf("2024-04-22")).workHoursBegin(Time.valueOf("12:11:00"))
                        .workHoursEnd(Time.valueOf("21:25:00")).personalId(2).build(),
                Schedule.builder().id(4L).workDate(Date.valueOf("2024-04-17")).workHoursBegin(Time.valueOf("09:40:00"))
                        .workHoursEnd(Time.valueOf("18:22:00")).personalId(4).build()
        ));
    }
}
