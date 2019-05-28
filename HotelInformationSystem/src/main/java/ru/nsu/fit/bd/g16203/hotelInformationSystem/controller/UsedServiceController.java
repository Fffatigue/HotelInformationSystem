package ru.nsu.fit.bd.g16203.hotelInformationSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.PersistException;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.UsedService;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.UsedServiceId;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.service.IUsedServiceService;

import java.util.List;

@RestController
@RequestMapping("/services/used")
public class UsedServiceController {
    @Autowired
    private IUsedServiceService usedServiceService;

    @RequestMapping("/id/{usedServiceId}")
    public UsedService getService(@PathVariable UsedServiceId usedServiceId) throws PersistException {
        return usedServiceService.getByPK(usedServiceId);
    }

    @DeleteMapping("/id/{usedServiceId}")
    public void deleteService(@PathVariable UsedServiceId serviceId) throws PersistException {
        usedServiceService.delete(serviceId);
    }

    @PutMapping("/id/{usedServiceId}")
    public void updateService(@PathVariable UsedServiceId serviceId, @RequestBody UsedService service) throws PersistException {
        service.setPK(serviceId);
        usedServiceService.update(service);
    }

    @PostMapping("/id/{usedServiceId}")
    public UsedService createRoom(@PathVariable UsedServiceId serviceId, @RequestBody UsedService service) throws PersistException {
        service.setPK(serviceId);
        usedServiceService.create(service);
        return service;
    }

    @RequestMapping("/page/{page}")
    public List<UsedService> getServices(@PathVariable int page) throws PersistException {
        return usedServiceService.getAll(page);
    }
}
