package ru.nsu.fit.bd.g16203.hotelInformationSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.IRoomDao;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.PersistException;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Room;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.RoomId;

import java.util.List;

@Service
public class RoomService implements IRoomService {
    @Autowired
    private IRoomDao roomDao;

    @Override
    public Room getByPK(RoomId primaryKey) throws PersistException {
        return roomDao.getByPK( primaryKey );
    }

    @Override
    public void update(Room obj) throws PersistException {
        roomDao.update( obj );
    }

    @Override
    public void delete(RoomId primaryKey) throws PersistException {
        roomDao.delete( primaryKey );
    }

    @Override
    public void create(Room obj) throws PersistException {
        roomDao.create( obj );
    }

    @Override
    public List<Room> getAll(int page) throws PersistException {
        return roomDao.getAll(page);
    }
}
