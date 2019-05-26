package ru.nsu.fit.bd.g16203.hotelInformationSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.PersistException;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Building;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.service.IBuildingService;

@RestController
@RequestMapping("/buildings")
public class BuildingController {
    @Autowired
    private IBuildingService buildingService;

    @GetMapping("/{buildingId}")
    public Building getBuilding(@PathVariable int buildingId) throws PersistException {
        return buildingService.getByPK(buildingId);
    }

    @DeleteMapping("/{buildingId}")
    public void deleteRoom(@PathVariable int buildingId) throws PersistException {
        buildingService.delete(buildingId);
    }

    @PutMapping("/{buildingId}")
    public void updateBuilding(@PathVariable int buildingId, @RequestBody Building building) throws PersistException {
        building.setPK(buildingId);
        buildingService.update(building);
    }

    @PostMapping("/{buildingId}")
    public Building createBuilding(@PathVariable int buildingId, @RequestBody Building building) throws PersistException {
        building.setPK(null);//buildingId);
        building = buildingService.create(building);
        return building;
    }
}
