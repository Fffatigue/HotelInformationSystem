package ru.nsu.fit.bd.g16203.hotelInformationSystem.dao;

import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Floor;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.FloorId;

import java.sql.SQLException;
import java.util.List;

public interface IFloorDao extends GenericDao<Floor, FloorId>  {
    public List<Floor> getAllByBuilding(int buildingId) throws SQLException;
}
