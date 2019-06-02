package ru.nsu.fit.bd.g16203.hotelInformationSystem.service;

import org.springframework.stereotype.Service;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.PersistException;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Room;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.RoomId;

import java.sql.SQLException;
import java.util.List;

@Service
public interface IRoomService extends GenericService<Room, RoomId> {
    public List<Room> getAllByFloor(int floorNum) throws SQLException;

    public int getFreeRoomsCount() throws SQLException;

    public int getFreeRoomsWithParams(int capacity, int price) throws SQLException;

    public List<Room> getAll(String filter) throws PersistException;
}
