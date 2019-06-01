package ru.nsu.fit.bd.g16203.hotelInformationSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.PersistException;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.WrongDataException;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Building;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.service.IBuildingService;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/buildings")
public class BuildingController {
    @Autowired
    private IBuildingService buildingService;

    @GetMapping("/id/{buildingId}")
    public Building getBuilding(@PathVariable int buildingId) throws PersistException {
        return buildingService.getByPK(buildingId);
    }

    @DeleteMapping("/id/{buildingId}")
    public void deleteRoom(@PathVariable int buildingId) throws PersistException, WrongDataException {
        buildingService.delete(buildingId);
    }

    @PutMapping("/id/{buildingId}")
    public void updateBuilding(@PathVariable int buildingId, @RequestBody Building building) throws PersistException, SQLException, WrongDataException {
        building.setPK(buildingId);
        buildingService.update(building);
    }

    @PostMapping
    public Building createBuilding(@RequestBody Building building) throws PersistException, SQLException, WrongDataException {
        buildingService.create(building);
        return building;
    }

    @GetMapping("/page/{page}")
    public List<Building> getBuildings(@PathVariable int page) throws PersistException {
        return buildingService.getAll(page);
    }
}
