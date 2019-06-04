package ru.nsu.fit.bd.g16203.hotelInformationSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.IReservationDao;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.PersistException;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.WrongDataException;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Reservation;

import java.sql.SQLException;
import java.util.List;

@Service
public class ReservationService implements IReservationService {
    @Autowired
    private IReservationDao reservationDao;

    @Override
    public Reservation getByPK(Integer primaryKey) throws PersistException {
        return reservationDao.getByPK(primaryKey);
    }

    @Override
    public int getPageNum() throws SQLException {
        return reservationDao.getPageNum();
    }

    @Override
    public void update(Reservation obj) throws PersistException, SQLException, WrongDataException {
        reservationDao.update(obj);
    }

    @Override
    public void delete(Integer primaryKey) throws PersistException, WrongDataException {
        reservationDao.delete(primaryKey);
    }

    @Override
    public void create(Reservation obj) throws PersistException, SQLException, WrongDataException {
        reservationDao.create(obj);
    }

    @Override
    public List<Reservation> getAll(int page) throws PersistException {
        return reservationDao.getAll(page);
    }

    @Override
    public void insertAvailableService(Integer reservationId, Integer serviceId) throws SQLException {
        reservationDao.insertAvailableService( reservationId, serviceId );
    }

    @Override
    public void deleteAvailableService(Integer buildingId, Integer serviceId) throws SQLException {
        reservationDao.deleteAvailableService( buildingId, serviceId );
    }

    @Override
    public List<ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Service> getAvailableServices(Integer reservationId) throws SQLException {
        return reservationDao.getAvailableServices( reservationId );
    }
}
