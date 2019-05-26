package ru.nsu.fit.bd.g16203.hotelInformationSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import ru.nsu.fit.bd.g16203.hotelInformationSystem.MyRoom;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.PersistException;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.FloorId;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Room;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.RoomId;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.service.IRoomService;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {
    @Autowired
    private IRoomService roomService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MyRoom> getRooms(){
        List<MyRoom> er = new LinkedList<>(  );
        er.add( new MyRoom() );
        er.add( new MyRoom() );
        return er;
        //return new LinkedList<>(  )MyRoom();
    }

    @GetMapping("building/{buildingName}/floor/{floorNum}/room/{roomNum}")
    public MyRoom getRoom(@PathVariable String buildingName, @PathVariable int floorNum, @PathVariable int roomNum) throws PersistException {
        return new MyRoom();
       // return roomService.getByPK( new RoomId( new FloorId( buildingId, floorNum ), roomNum ) );
    }


    @DeleteMapping
    public void deleteRoom(int buildingId, int floorNum, int roomNum) throws PersistException {
        roomService.delete( new RoomId( new FloorId( buildingId, floorNum ), roomNum ) );
    }

    @PutMapping
    public void updateRoom(int buildingId, int floorNum, int roomNum, @RequestBody Room room) throws PersistException {
        RoomId roomId = new RoomId( new FloorId( buildingId, floorNum ), roomNum );
        room.setPK( roomId );
        roomService.update( room );
    }

    @PostMapping
    public Room createRoom(@RequestBody Room room) throws PersistException {
        roomService.create( room );
        return room;
    }
}
