package ru.nsu.fit.bd.g16203.hotelInformationSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.PersistException;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Building;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.service.IBuildingService;

@RestController
@RequestMapping("building{buildingId}")
public class BuildingController {
    @Autowired
    private IBuildingService buildingService;

    @RequestMapping("{name}")
    public Building getBuilding(@PathVariable int buildingId, @PathVariable String name) throws PersistException {
        return buildingService.getByPK(buildingId);
    }

    @DeleteMapping("{name}")
    public void deleteRoom(@PathVariable int buildingId, @PathVariable String name) throws PersistException {
        buildingService.delete(buildingId);
    }

    @PutMapping
    public void updateBuilding(@PathVariable int buildingId, @PathVariable String name, @RequestBody Building building) throws PersistException {
        building.setPK(buildingId);
        buildingService.update(building);
    }

    @PostMapping
    public Building createBuilding(@PathVariable int buildingId, @PathVariable String name, @RequestBody Building building) throws PersistException {
        building.setPK(buildingId);
        building = buildingService.create(building);
        return building;
    }
}
