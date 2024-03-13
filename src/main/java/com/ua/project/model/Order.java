package com.ua.project.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private Long id;
    private BigDecimal price;
    private BigDecimal priceWithDiscount;
    private Timestamp timestamp;
    private long personalId;
    private long clientId;
}
