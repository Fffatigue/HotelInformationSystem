package ru.nsu.fit.bd.g16203.hotelInformationSystem.dao;

import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Floor;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Room;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.RoomId;

import java.sql.SQLException;
import java.util.List;

public interface IRoomDao extends GenericDao<Room, RoomId> {
    public List<Room> getAllByFloor(int floorNum) throws SQLException;

    public int getFreeRoomsCount() throws SQLException;

    public int getFreeRoomsWithParams(int capacity, int price) throws SQLException;
}
