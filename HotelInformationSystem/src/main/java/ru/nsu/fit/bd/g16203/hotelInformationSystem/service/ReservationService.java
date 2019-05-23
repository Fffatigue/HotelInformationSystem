package ru.nsu.fit.bd.g16203.hotelInformationSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.IReservationDao;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.PersistException;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Reservation;

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
    public void update(Reservation obj) throws PersistException {
        reservationDao.update(obj);
    }

    @Override
    public void delete(Integer primaryKey) throws PersistException {
        reservationDao.delete(primaryKey);
    }

    @Override
    public Reservation create(Reservation obj) throws PersistException {
        return reservationDao.create(obj);
    }

    @Override
    public List<Reservation> getAll() throws PersistException {
        return reservationDao.getAll();
    }
}
