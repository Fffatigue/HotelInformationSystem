package ru.nsu.fit.bd.g16203.hotelInformationSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.PersistException;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.WrongDataException;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Floor;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.FloorId;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Room;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.service.IFloorService;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/floors")
public class FloorController {
    @Autowired
    private IFloorService floorService;

    @RequestMapping("building/{buildingId}/floor/{floorNum}")
    public Floor getFloor(@PathVariable int buildingId, @PathVariable int floorNum) throws PersistException {
        return floorService.getByPK( new FloorId( buildingId, floorNum ) );
    }

    @DeleteMapping("building/{buildingId}/floor/{floorNum}")
    public int deleteFloor(@PathVariable int buildingId, @PathVariable int floorNum) throws PersistException, SQLException, WrongDataException {
        floorService.delete( new FloorId( buildingId, floorNum ) );
        return floorService.getPageNum();
    }

    @PutMapping("/building/{buildingId}/floor/{floorNum}")
    public void updateFloor(@PathVariable int buildingId, @PathVariable int floorNum, @RequestBody Floor floor) throws PersistException, SQLException, WrongDataException {
        FloorId floorId = new FloorId( buildingId, floorNum );
        floor.setPK( floorId );
        floorService.update( floor );
    }

    @PostMapping("/building/{buildingId}/floor/{floorNum}")
    public Floor createFloor(@PathVariable int buildingId, @PathVariable int floorNum, @RequestBody Floor floor) throws PersistException, SQLException, WrongDataException {
        FloorId floorId = new FloorId( buildingId, floorNum );
        floor.setPK( floorId );
        floorService.create( floor );
        return floor;
    }

    @RequestMapping("/page")
    public int getPageNum() throws SQLException {
        return floorService.getPageNum();
    }

    @RequestMapping("/page/{page}")
    public List<Floor> getFLoors(@PathVariable int page) throws PersistException {
        return floorService.getAll( page );
    }

    @RequestMapping("building/{buildingId}")
    public List<Floor> getFloorsByBuilding(@PathVariable int buildingId) throws PersistException, SQLException {
        return floorService.getAllByBuilding( buildingId );
    }
}
