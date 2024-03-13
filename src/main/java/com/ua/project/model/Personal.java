package com.ua.project.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Personal {

    private Long id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private long positionId;
}
