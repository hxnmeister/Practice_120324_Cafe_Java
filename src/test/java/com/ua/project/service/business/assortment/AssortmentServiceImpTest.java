package com.ua.project.service.business.assortment;

import com.ua.project.dao.assortmentDAO.AssortmentDao;
import com.ua.project.dao.assortmentDAO.AssortmentDaoImp;
import com.ua.project.model.Assortment;
import com.ua.project.service.CafeDbInitializer;
import com.ua.project.utils.DBTestData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.setProperty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class AssortmentServiceImpTest {
    @BeforeAll
    static void initCafeTestDB() {
        setProperty("test", "true");
        CafeDbInitializer.createTables();
    }

    @BeforeEach
    void initData() {
        DBTestData.insert();
    }

    @AfterEach
    void deleteData() {
        CafeDbInitializer.createTables();
    }

    @Test
    void getAllAssortmentByType_ShouldReturnStringWithFormattedTextToShow_WhenCalled() {
        List<Assortment> assortment = new ArrayList<>(List.of(
                Assortment.builder().title("drink1").quantity(11).price(new BigDecimal("12.3")).build(),
                Assortment.builder().title("drink2").quantity(12).price(new BigDecimal("13.5")).build(),
                Assortment.builder().title("drink3").quantity(13).price(new BigDecimal("14.6")).build()
        ));
        StringBuilder actual = new StringBuilder();
        StringBuilder expected = new StringBuilder();
        AssortmentDao assortmentDao = Mockito.mock(AssortmentDaoImp.class);
        when(assortmentDao.getAssortmentByType("drink")).thenReturn(assortment);

        expected.append("\n All ").append("drink").append("`s in menu:\n");
        assortment.forEach((item) -> expected.append(" id:").append(item.getId()).append("| ").append(item.getTitle()).append(" | ").append(item.getQuantity()).append(" | ").append(item.getPrice()).append("$"));

        actual.append("\n All ").append("drink").append("`s in menu:\n");
        assortmentDao.getAssortmentByType("drink").forEach((item) -> actual.append(" id:").append(item.getId()).append("| ").append(item.getTitle()).append(" | ").append(item.getQuantity()).append(" | ").append(item.getPrice()).append("$"));

        assertEquals(expected.toString(), actual.toString());
    }
}
