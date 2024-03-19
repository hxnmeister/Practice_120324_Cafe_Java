package com.ua.project.service.business.personal;

import com.ua.project.model.Personal;

public interface PersonalService {
    void addPersonal(Personal personal, String position);
    String getPersonalByPosition(String position);
}
