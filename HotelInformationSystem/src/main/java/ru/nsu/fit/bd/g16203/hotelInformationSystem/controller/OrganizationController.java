package ru.nsu.fit.bd.g16203.hotelInformationSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.PersistException;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Organization;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.service.IOrganizationService;

import java.util.List;

@RestController
@RequestMapping("/organizations")
public class OrganizationController {
    @Autowired
    private IOrganizationService organizationService;

    @GetMapping("/id/{organizationId}")
    public Organization getOrganization(@PathVariable int organizationId) throws PersistException {
        return organizationService.getByPK(organizationId);
    }

    @DeleteMapping("/id/{organizationId}")
    public void deleteOrganization(@PathVariable int organizationId) throws PersistException {
        organizationService.delete(organizationId);
    }

    @PutMapping("/id/{organizationId}")
    public void updateOrganization(@PathVariable int organizationId, @RequestBody Organization organization) throws PersistException {
        organization.setPK( organizationId );
        organizationService.update( organization );
    }

    @PostMapping
    public Organization createOrganization(@RequestBody Organization organization) throws PersistException {
        organizationService.create( organization );
        return organization;
    }

    @GetMapping("/page/{page}")
    public List<Organization> getOrganizations(@PathVariable int page) throws PersistException {
        return organizationService.getAll(page);
    }
}
