package com.ua.project.service.business.assortment;

import com.ua.project.model.Assortment;

public interface AssortmentService {
    void addAssortment(Assortment assortment, String assortmentType);
    String getAllAssortmentByType(String type);
}
