package ru.nsu.fit.bd.g16203.hotelInformationSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.PersistException;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Service;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.service.IServiceService;

@RestController
@RequestMapping("service{serviceId}/")
public class ServiceController {
    @Autowired
    private IServiceService serviceService;

    @RequestMapping("{serviceId}")
    public Service getService(@PathVariable int serviceId) throws PersistException {
        return serviceService.getByPK(serviceId);
    }

    @DeleteMapping("{serviceId}")
    public void deleteService(@PathVariable int serviceId) throws PersistException {
        serviceService.delete(serviceId);
    }

    @PutMapping
    public void updateService(@PathVariable int serviceId, @RequestBody Service service) throws PersistException {
        service.setPK(serviceId);
        serviceService.update(service);
    }

    @PostMapping
    public Service createRoom(@PathVariable int serviceId, @RequestBody Service service) throws PersistException {
        service.setPK(serviceId);
        serviceService.create(service);
        return service;
    }
}
