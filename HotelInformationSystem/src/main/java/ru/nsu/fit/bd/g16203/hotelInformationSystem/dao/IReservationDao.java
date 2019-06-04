package ru.nsu.fit.bd.g16203.hotelInformationSystem.dao;

import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Reservation;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public interface IReservationDao extends GenericDao <Reservation, Integer> {
    public void insertAvailableService(Integer buildingId, Integer serviceId) throws SQLException;

    public void deleteAvailableService(Integer buildingId, Integer serviceId) throws SQLException;

    public List<Service> getAvailableServices(Integer buildingId) throws SQLException;
}
