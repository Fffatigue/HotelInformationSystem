package ru.nsu.fit.bd.g16203.hotelInformationSystem.service;

import org.springframework.stereotype.Service;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.PersistException;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.RoomDao;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.WrongDataException;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Room;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.RoomId;

import java.sql.SQLException;
import java.sql.Date;
import java.util.List;

@Service
public interface IRoomService extends GenericService<Room, RoomId> {
    public List<Room> getAllByFloor(int floorNum) throws SQLException;

    public int getFreeRoomsCount() throws SQLException;

    public List<Room> getFutureFreeRooms(Date date) throws SQLException;

    public int getFreeRoomsWithParams(int capacity, int price) throws SQLException;

    public List<Room> getAll(String filter, String sortBy, boolean sortAsc) throws PersistException, WrongDataException;

    public List<Room> getAll(int page, String sortBy, boolean sortAsc) throws PersistException, WrongDataException;

    public RoomDao.RoomInfo getRoomInfo(RoomId roomId) throws PersistException;

    public RoomDao.CurClientInfo getCurClientInfo(RoomId roomId) throws PersistException, SQLException;

    public List<RoomDao.RoomProfitability> getRoomsProfitability() throws SQLException;

    public List<Room> getRoomsByBuildingAndFloor(int buildingId, int floorNum) throws SQLException;
}
