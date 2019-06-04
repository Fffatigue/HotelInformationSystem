package ru.nsu.fit.bd.g16203.hotelInformationSystem.service;

import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Building;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Service;

import java.sql.SQLException;
import java.util.List;

public interface IBuildingService extends GenericService<Building, Integer>{
    public void insertAvailableService(Integer buildingId, Integer serviceId) throws SQLException;

    public void deleteAvailableService(Integer buildingId, Integer serviceId) throws SQLException;

    public List<Service> getAvailableServices(Integer buildingId) throws SQLException;
}
