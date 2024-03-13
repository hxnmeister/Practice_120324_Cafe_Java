package com.ua.project.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.sql.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {

    private Long id;
    private Date workDate;
    private Time workHoursBegin;
    private Time workHoursEnd;
    private long personalId;
}
