package ru.nsu.fit.bd.g16203.hotelInformationSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.PersistException;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Organization;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.service.IOrganizationService;

@RestController
@RequestMapping("organization{organizationId}")
public class OrganizationController {
    @Autowired
    private IOrganizationService organizationService;

    @RequestMapping
    public Organization getOrganization(@PathVariable int organizationId) throws PersistException {
        return organizationService.getByPK(organizationId);
    }

    @DeleteMapping
    public void deleteOrganization(@PathVariable int organizationId) throws PersistException {
        organizationService.delete(organizationId);
    }

    @PutMapping
    public void updateOrganization(@PathVariable int organizationId, @RequestBody Organization organization) throws PersistException {
        organization.setPK( organizationId );
        organizationService.update( organization );
    }

    @PostMapping
    public Organization createOrganization(@PathVariable int organizationId, @RequestBody Organization organization) throws PersistException {
        organization.setPK( organizationId );
        organization = organizationService.create( organization );
        return organization;
    }

    //TODO сделоть
}
