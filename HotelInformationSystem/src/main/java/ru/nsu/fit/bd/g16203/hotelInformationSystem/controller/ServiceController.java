package ru.nsu.fit.bd.g16203.hotelInformationSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.PersistException;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.WrongDataException;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Service;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.service.IServiceService;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/services")
public class ServiceController {
    @Autowired
    private IServiceService serviceService;

    @RequestMapping("/id/{serviceId}")
    public Service getService(@PathVariable int serviceId) throws PersistException {
        return serviceService.getByPK(serviceId);
    }

    @DeleteMapping("/id/{serviceId}")
    public void deleteService(@PathVariable int serviceId) throws PersistException, WrongDataException {
        serviceService.delete(serviceId);
    }

    @PutMapping("/id/{serviceId}")
    public void updateService(@PathVariable int serviceId, @RequestBody Service service) throws PersistException, SQLException, WrongDataException {
        service.setPK(serviceId);
        serviceService.update(service);
    }

    @PostMapping("/id/{serviceId}")
    public Service createRoom(@PathVariable int serviceId, @RequestBody Service service) throws PersistException, SQLException, WrongDataException {
        service.setPK(serviceId);
        serviceService.create(service);
        return service;
    }

    @RequestMapping("/page/{page}")
    public List<Service> getServices(@PathVariable int page) throws PersistException {
        return serviceService.getAll(page);
    }
}
