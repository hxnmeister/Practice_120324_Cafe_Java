package com.ua.project.service.business.assortment;

import com.ua.project.dao.assortmentDAO.AssortmentDao;
import com.ua.project.dao.assortmentDAO.AssortmentDaoImp;
import com.ua.project.dao.assortment_typeDAO.AssortmentTypeDao;
import com.ua.project.dao.assortment_typeDAO.AssortmentTypeDaoImpl;
import com.ua.project.model.Assortment;
import com.ua.project.model.AssortmentType;

import java.util.List;

public class AssortmentServiceImp implements AssortmentService {
    private static final AssortmentDao assortmentDao = new AssortmentDaoImp();

    @Override
    public void addAssortment(Assortment assortment, String assortmentType) {
        AssortmentTypeDao assortmentTypeDao = new AssortmentTypeDaoImpl();

        if (!assortmentTypeDao.isAssortmentTypeAvailable(assortmentType)) {
            assortmentTypeDao.save(AssortmentType.builder()
                    .title(assortmentType)
                    .build());
        }

        assortmentDao.save(Assortment.builder()
                .title(assortment.getTitle())
                .quantity(assortment.getQuantity())
                .price(assortment.getPrice())
                .assortmentTypeId(assortmentTypeDao.getAssortmentTypeByTitle(assortmentType).getId())
                .build());
    }

    @Override
    public String getAllAssortmentByType(String type) {
        StringBuilder assortmentByTypeBuilder = new StringBuilder();
        AssortmentDao assortmentDao = new AssortmentDaoImp();
        List<Assortment> assortmentList = assortmentDao.getAssortmentByType(type);

        assortmentByTypeBuilder.append("\n All ").append(type).append("`s in menu:\n");
        assortmentList.forEach((item) -> assortmentByTypeBuilder.append(" ").append(item.getTitle()).append(" | ").append(item.getQuantity()).append(" | ").append(item.getPrice()).append("$\n"));

        return assortmentByTypeBuilder.toString();
    }
}
