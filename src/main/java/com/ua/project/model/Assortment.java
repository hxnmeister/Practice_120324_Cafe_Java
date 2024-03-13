package com.ua.project.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Assortment {

    private Long id;
    private String title;
    private int quantity;
    private BigDecimal price;
    private long assortmentTypeId;
}
