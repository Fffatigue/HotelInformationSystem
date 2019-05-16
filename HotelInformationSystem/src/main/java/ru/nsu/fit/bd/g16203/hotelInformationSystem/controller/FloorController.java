package ru.nsu.fit.bd.g16203.hotelInformationSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.PersistException;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Floor;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.FloorId;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.service.IFloorService;

@RestController
@RequestMapping("building{buildingId}/floor")
public class FloorController {
    @Autowired
    private IFloorService floorService;

    @RequestMapping("{floorNum}")
    public Floor getFloor(@PathVariable int buildingId, @PathVariable int floorNum) throws PersistException {
        return floorService.getByPK(new FloorId(buildingId, floorNum));
    }

    @DeleteMapping("{roomNum}")
    public void deleteFloor(@PathVariable int buildingId, @PathVariable int floorNum) throws PersistException {
        floorService.delete(new FloorId(buildingId, floorNum));
    }

    @PutMapping
    public void updateFloor(@PathVariable int buildingId, @PathVariable int floorNum, @RequestBody Floor floor) throws PersistException {
        FloorId floorId = new FloorId(buildingId, floorNum);
        floor.setPK(floorId);
        floorService.update(floor);
    }

    @PostMapping
    public Floor createRoom(@PathVariable int buildingId, @PathVariable int floorNum, @RequestBody Floor floor) throws PersistException {
        FloorId floorId = new FloorId(buildingId, floorNum);
        floor.setPK(floorId);
        floor = floorService.create(floor);
        return floor;
    }
}
