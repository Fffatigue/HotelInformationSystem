package ru.nsu.fit.bd.g16203.hotelInformationSystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.PersistException;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.FloorId;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Room;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.RoomId;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.service.IRoomService;

@RestController
public class MainController {
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

    @PutMapping("/building/{buildingId}/floor/{floorNum}/room/")
    public void updateRoom(@PathVariable int buildingId, @PathVariable int floorNum, @RequestBody Room room) throws PersistException {
        RoomId roomId = new RoomId( new FloorId( buildingId, floorNum ), room.getPK().getRoomNum() );
        room.setPK( roomId );
        roomService.update( room );
    }

    @PostMapping("/building/{buildingId}/floor/{floorNum}/room/")
    public Room createRoom(@PathVariable int buildingId, @PathVariable int floorNum, @RequestBody Room room) throws PersistException {
        RoomId roomId = new RoomId( new FloorId( buildingId, floorNum ), room.getPK().getRoomNum() );
        room.setPK( roomId );
        room = roomService.create( room );
        return room;
    }
}
