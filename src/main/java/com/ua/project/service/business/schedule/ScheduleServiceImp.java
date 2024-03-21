package com.ua.project.service.business.schedule;

import com.ua.project.dao.personalDAO.PersonalDao;
import com.ua.project.dao.personalDAO.PersonalDaoImp;
import com.ua.project.dao.scheduleDAO.ScheduleDao;
import com.ua.project.dao.scheduleDAO.ScheduleDaoImp;
import com.ua.project.model.Personal;
import com.ua.project.model.Schedule;

import java.util.List;

public class ScheduleServiceImp implements ScheduleService {
    @Override
    public String getAllSchedules() {
        StringBuilder builder = new StringBuilder();
        ScheduleDao scheduleDao = new ScheduleDaoImp();
        PersonalDao personalDao = new PersonalDaoImp();
        List<Personal> personalList = personalDao.findAll();
        List<Schedule> scheduleList = scheduleDao.findAll();

        for (Schedule schedule : scheduleList) {
            Personal personal = new Personal();

            for (Personal item : personalList) {
                if(item.getId() == schedule.getPersonalId()) {
                    personal = item;
                    break;
                }
            }

            builder.append("\n  id:")
                    .append(schedule.getId())
                    .append("| ").append(schedule.getWorkDate())
                    .append(" ||").append(schedule.getWorkHoursBegin())
                    .append(" - ").append(schedule.getWorkHoursEnd())
                    .append("|| ").append(personal.getFirstName())
                    .append(" ").append(personal.getLastName());
        }

        return builder.toString();
    }
}
