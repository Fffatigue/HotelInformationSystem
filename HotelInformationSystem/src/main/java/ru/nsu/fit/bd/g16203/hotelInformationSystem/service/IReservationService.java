package ru.nsu.fit.bd.g16203.hotelInformationSystem.service;

import org.springframework.stereotype.Service;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Reservation;

import java.sql.SQLException;
import java.util.List;

@Service
public interface IReservationService extends GenericService<Reservation, Integer>{
    public void insertAvailableService(Integer buildingId, Integer serviceId) throws SQLException;

    public void deleteAvailableService(Integer buildingId, Integer serviceId) throws SQLException;

    public List<ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Service> getAvailableServices(Integer buildingId) throws SQLException;
}
