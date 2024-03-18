package com.ua.project.dao.positionDAO;

import com.ua.project.dao.CRUDInterface;
import com.ua.project.exception.ConnectionDBException;
import com.ua.project.model.Position;

import java.sql.SQLException;
import java.util.List;

public interface PositionDao extends CRUDInterface<Position> {

    boolean isPositionAvailable(String title);
    Position getPositionByTitle(String title);
}
