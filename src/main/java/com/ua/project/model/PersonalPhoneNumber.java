package com.ua.project.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonalPhoneNumber {

    private Long id;
    private String phoneNumber;
    private long personalId;
}
