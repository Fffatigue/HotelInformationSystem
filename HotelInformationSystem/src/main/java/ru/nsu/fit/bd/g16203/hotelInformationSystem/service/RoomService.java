package ru.nsu.fit.bd.g16203.hotelInformationSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.IRoomDao;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.PersistException;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.WrongDataException;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Room;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.RoomId;

import java.sql.SQLException;
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
    public int getPageNum() throws SQLException {
        return roomDao.getPageNum();
    }

    @Override
    public void update(Room obj) throws PersistException, SQLException, WrongDataException {
        roomDao.update( obj );
    }

    @Override
    public void delete(RoomId primaryKey) throws PersistException, WrongDataException {
        roomDao.delete( primaryKey );
    }

    @Override
    public void create(Room obj) throws PersistException, SQLException, WrongDataException {
        roomDao.create( obj );
    }

    @Override
    public List<Room> getAll(int page) throws PersistException {
        return roomDao.getAll( page );
    }

    @Override
    public List<Room> getAllByFloor(int floorNum) throws SQLException {
        return roomDao.getAllByFloor( floorNum );
    }

    @Override
    public int getFreeRoomsCount() throws SQLException {
        return roomDao.getFreeRoomsCount();
    }

    @Override
    public int getFreeRoomsWithParams(int capacity, int price) throws SQLException {
        return roomDao.getFreeRoomsWithParams( capacity, price );
    }
}
