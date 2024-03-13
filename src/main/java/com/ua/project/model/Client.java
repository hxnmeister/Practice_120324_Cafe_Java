package com.ua.project.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    private Long id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private Date birthDate;
    private String contactPhone;
    private String contactEmail;
    private int discount;
}
