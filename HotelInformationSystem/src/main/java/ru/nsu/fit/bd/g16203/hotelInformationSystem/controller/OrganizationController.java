package ru.nsu.fit.bd.g16203.hotelInformationSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.PersistException;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.WrongDataException;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Organization;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.service.IOrganizationService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/organizations")
public class OrganizationController {
    @Autowired
    private IOrganizationService organizationService;

    @GetMapping("/id/{organizationId}")
    public Organization getOrganization(@PathVariable int organizationId) throws PersistException {
        return organizationService.getByPK( organizationId );
    }

    @DeleteMapping("/id/{organizationId}")
    public void deleteOrganization(@PathVariable int organizationId) throws PersistException, WrongDataException {
        organizationService.delete( organizationId );
    }

    @PutMapping("/id/{organizationId}")
    public void updateOrganization(@PathVariable int organizationId, @RequestBody Organization organization) throws PersistException, SQLException, WrongDataException {
        organization.setPK( organizationId );
        organizationService.update( organization );
    }

    @PostMapping
    public Organization createOrganization(@RequestBody Organization organization) throws PersistException, SQLException, WrongDataException {
        organizationService.create( organization );
        return organization;
    }

    @GetMapping("/report/1")
    public List<Organization> getReportWithDate(@RequestParam int count,
                                                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate beginDate,
                                                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) throws PersistException, SQLException {
        return organizationService.getOrganizationReservedMoreThenCountInPeriod( count, beginDate, endDate );
    }

    @GetMapping("/report/2")
    public List<Organization> getReportWithoutDate(@RequestParam int count) throws PersistException, SQLException {
        return organizationService.getOrganizationReservedMoreThenCount( count );
    }

    @GetMapping("/report/11")
    public List<Organization> getReportWithDate2(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate beginDate,
                                                 @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) throws PersistException, SQLException {
        return organizationService.getOrganizationReservedInPeriod(beginDate, endDate );
    }

    @GetMapping("/page/{page}")
    public List<Organization> getOrganizations(@PathVariable int page) throws PersistException {
        return organizationService.getAll( page );
    }

    @GetMapping("/page")
    public int getPageNum() throws SQLException {
        return organizationService.getPageNum();
    }
}
