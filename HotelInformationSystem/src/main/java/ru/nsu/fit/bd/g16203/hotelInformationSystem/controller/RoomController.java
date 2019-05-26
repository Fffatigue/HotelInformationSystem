package ru.nsu.fit.bd.g16203.hotelInformationSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.PersistException;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.FloorId;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Room;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.RoomId;

import ru.nsu.fit.bd.g16203.hotelInformationSystem.service.IRoomService;

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
    public void deleteRoom(@PathVariable int buildingId, @PathVariable int floorNum, @PathVariable int roomNum) throws PersistException {
        roomService.delete( new RoomId( new FloorId( buildingId, floorNum ), roomNum ) );
    }

    @PutMapping("/building/{buildingId}/floor/{floorNum}/room/{roomNum}")
    public void updateRoom(@PathVariable int buildingId, @PathVariable int floorNum, @PathVariable int roomNum, @RequestBody Room room) throws PersistException {
        room.setPK( new RoomId( new FloorId( buildingId, floorNum ), roomNum ) );
        roomService.update( room );
    }

    @PostMapping("/building/{buildingId}/floor/{floorNum}/room/{roomNum}")
    public Room createRoom(@PathVariable int buildingId, @PathVariable int floorNum, @PathVariable int roomNum, @RequestBody Room room) throws PersistException {
        room.setPK( new RoomId( new FloorId( buildingId, floorNum ), roomNum ) );
        roomService.create(room );
        return room;
    }

    @RequestMapping("/page/{page}")
    public List<Room> getServices(@PathVariable int page) throws PersistException {
        return roomService.getAll( page );
    }
}
