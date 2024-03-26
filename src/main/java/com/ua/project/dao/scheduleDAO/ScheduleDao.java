package com.ua.project.dao.scheduleDAO;

import com.ua.project.dao.CRUDInterface;
import com.ua.project.model.Schedule;

import java.sql.Date;
import java.util.List;

public interface ScheduleDao extends CRUDInterface<Schedule> {
    void deleteInDateRange(Date rangeBegin, Date rangeEnd);
    List<Schedule> findScheduleForWeekByPersonalId(int personalId);
    List<Schedule> findSchedulesForWeekByPosition(String position);
    List<Schedule> findSchedulesForWeek();
}
