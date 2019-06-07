package ru.nsu.fit.bd.g16203.hotelInformationSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.PersistException;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.RoomDao;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.WrongDataException;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.FloorId;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Room;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.RoomId;

import ru.nsu.fit.bd.g16203.hotelInformationSystem.service.IRoomService;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {
    @Autowired
    private IRoomService roomService;

    @RequestMapping("/building/{buildingId}/floor/{floorNum}/room/{roomNum}")
    public Room getRoom(@PathVariable int buildingId, @PathVariable int floorNum, @PathVariable int roomNum) throws PersistException {
        return roomService.getByPK( new RoomId( new FloorId( buildingId, floorNum ), roomNum ) );
    }

    @DeleteMapping("/building/{buildingId}/floor/{floorNum}/room/{roomNum}")
    public int deleteRoom(@PathVariable int buildingId, @PathVariable int floorNum, @PathVariable int roomNum) throws PersistException, SQLException, WrongDataException {
        roomService.delete( new RoomId( new FloorId( buildingId, floorNum ), roomNum ) );
        return roomService.getPageNum();
    }

    @PutMapping("/building/{buildingId}/floor/{floorNum}/room/{roomNum}")
    public void updateRoom(@PathVariable int buildingId, @PathVariable int floorNum, @PathVariable int roomNum, @RequestBody Room room) throws PersistException, SQLException, WrongDataException {
        room.setPK( new RoomId( new FloorId( buildingId, floorNum ), roomNum ) );
        roomService.update( room );
    }

    @PostMapping("/building/{buildingId}/floor/{floorNum}/room/{roomNum}")
    public Room createRoom(@PathVariable int buildingId, @PathVariable int floorNum, @PathVariable int roomNum, @RequestBody Room room) throws PersistException, SQLException, WrongDataException {
        room.setPK( new RoomId( new FloorId( buildingId, floorNum ), roomNum ) );
        roomService.create( room );
        return room;
    }

    @RequestMapping("/page")
    public int getPageNum() throws SQLException {
        return roomService.getPageNum();
    }

    @RequestMapping("/page/{page}")
    public List<Room> getRooms(@PathVariable int page, @RequestParam String sortBy, @RequestParam boolean sortAsc) throws PersistException, WrongDataException {
        return roomService.getAll( page, sortBy, sortAsc );
    }

    @RequestMapping("/filter/{filter}")
    public List<Room> getRooms(@PathVariable String filter, @RequestParam String sortBy, @RequestParam boolean sortAsc) throws PersistException, WrongDataException {
        return roomService.getAll( filter, sortBy, sortAsc );
    }

    @RequestMapping("/report/1")
    public int getReport1() throws SQLException {
        return roomService.getFreeRoomsCount();
    }

    @RequestMapping("/report/2")
    public int getReport2(@RequestParam int price, @RequestParam int capacity) throws SQLException {
        return roomService.getFreeRoomsWithParams( capacity, price );
    }

    @RequestMapping("/report/3")
    public RoomDao.RoomInfo getReport2(@RequestParam int roomNum, @RequestParam int floorNum, @RequestParam int buildingId) throws SQLException, PersistException {
        FloorId floorId = new FloorId( buildingId, floorNum );
        return roomService.getRoomInfo( new RoomId( floorId, roomNum ) );
    }

    @RequestMapping("/my_report/1")
    public List<Room> getFutureRooms(@RequestParam Date date) throws SQLException {
        return roomService.getFutureFreeRooms(date);
    }

    @RequestMapping("/my_report/3")
    public  RoomDao.CurClientInfo getCurClientInfo(@RequestParam int roomNum, @RequestParam int floorNum, @RequestParam int buildingId) throws PersistException, SQLException {
        FloorId floorId = new FloorId(buildingId, floorNum);
        return roomService.getCurClientInfo(new RoomId(floorId, roomNum));
    }

    @RequestMapping("/my_report/2")
    public List<RoomDao.RoomProfitability> getRoomsProfitability() throws SQLException {
        return roomService.getRoomsProfitability();
    }

    @GetMapping("/building/{buildingId}/floor/{floorNum}")
    public List<Room> getRoomsByBuildingAndFloor(@PathVariable int buildingId, @PathVariable int floorNum) throws SQLException {
        return roomService.getRoomsByBuildingAndFloor(buildingId, floorNum);
    }
}
