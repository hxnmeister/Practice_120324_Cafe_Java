package com.ua.project.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderAndAssortment {

    private Long id;
    private long orderId;
    private long assortmentId;
}
