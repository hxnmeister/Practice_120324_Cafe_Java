package com.ua.project.dao.scheduleDAO;

import com.ua.project.dao.ConnectionFactory;
import com.ua.project.exception.ConnectionDBException;
import com.ua.project.model.AssortmentType;
import com.ua.project.model.Schedule;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDaoImp implements ScheduleDao {
    private static final String INSERT_SCHEDULE = """
        INSERT INTO schedule(work_date, work_hours_begin, work_hours_end, personal_id)
        VALUES (?, ?, ?, ?)
    """;
    private static final String UPDATE_SCHEDULE = """
        UPDATE schedule
        SET work_date=?, work_hours_begin=?, work_hours_end=?
        WHERE id=?
    """;
    private static final String DELETE_SCHEDULE = """
        DELETE FROM schedule
        WHERE id=?
    """;
    private static final String DELETE_EVERY_SCHEDULE = """
        DELETE FROM schedule
    """;
    private static final String GET_EVERY_SCHEDULE = """
        SELECT *
        FROM schedule
    """;

    @Override
    public void save(Schedule item) {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_SCHEDULE)) {

            statement.setDate(1, item.getWorkDate());
            statement.setTime(2, item.getWorkHoursBegin());
            statement.setTime(3, item.getWorkHoursEnd());
            statement.setLong(4, item.getPersonalId());

            statement.execute();
        }
        catch (ConnectionDBException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void saveMany(List<Schedule> items) {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_SCHEDULE)) {

            for (Schedule item : items) {
                statement.setDate(1, item.getWorkDate());
                statement.setTime(2, item.getWorkHoursBegin());
                statement.setTime(3, item.getWorkHoursEnd());
                statement.setLong(4, item.getPersonalId());

                statement.addBatch();
            }

            statement.executeBatch();
        }
        catch (ConnectionDBException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Schedule item) {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_SCHEDULE)) {

            statement.setDate(1, item.getWorkDate());
            statement.setTime(2, item.getWorkHoursBegin());
            statement.setTime(3, item.getWorkHoursEnd());
            statement.setLong(4, item.getId());

            statement.execute();
        }
        catch (ConnectionDBException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Schedule item) {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_SCHEDULE)) {

            statement.setLong(1, item.getId());
            statement.execute();
        }
        catch (ConnectionDBException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Schedule> findAll() {
        List<Schedule> assortmentTypes = new ArrayList<Schedule>();

        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             Statement statement = connection.createStatement()) {

            try (ResultSet queryResult = statement.executeQuery(GET_EVERY_SCHEDULE)) {
                while (queryResult.next()) {
                    assortmentTypes.add(Schedule.builder()
                            .id(queryResult.getLong("id"))
                            .workDate(queryResult.getDate("work_date"))
                            .workHoursBegin(queryResult.getTime("work_hours_begin"))
                            .workHoursEnd(queryResult.getTime("work_hours_end"))
                            .personalId(queryResult.getLong("personal_id"))
                            .build());
                }
            }
        }
        catch (ConnectionDBException | SQLException e) {
            System.out.println(e.getMessage());
        }

        return assortmentTypes;
    }

    @Override
    public void deleteAll() {
        try (Connection connection = ConnectionFactory.getInstance().makeConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(DELETE_EVERY_SCHEDULE);
        }
        catch (ConnectionDBException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
