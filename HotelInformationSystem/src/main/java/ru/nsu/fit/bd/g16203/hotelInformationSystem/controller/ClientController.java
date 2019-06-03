package ru.nsu.fit.bd.g16203.hotelInformationSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.PersistException;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.WrongDataException;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Client;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.service.IClientService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {
    @Autowired
    private IClientService clientService;

    @GetMapping("/id/{clientId}")
    public Client getClient(@PathVariable int clientId) throws PersistException {
        return clientService.getByPK( clientId );
    }

    @DeleteMapping("/id/{clientId}")
    public void deleteClient(@PathVariable int clientId) throws PersistException, WrongDataException {
        clientService.delete( clientId );
    }

    @PutMapping("/id/{clientId}")
    public void updateClient(@PathVariable int clientId, @RequestBody Client client) throws PersistException, SQLException, WrongDataException {
        client.setPK( clientId );
        clientService.update( client );
    }

    @PostMapping
    public Client createClient(@RequestBody Client client) throws PersistException, SQLException, WrongDataException {
        clientService.create( client );
        return client;
    }

    @GetMapping("/page/{page}")
    public List<Client> getClients(@PathVariable int page) throws PersistException {
        return clientService.getAll( page );
    }

    @GetMapping("/report/1")
    public List<Client> getReport(@RequestParam int price, @RequestParam int capacity, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate beginDate, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) throws PersistException, SQLException {
        return clientService.getAllReservedRoomsInPeriodWithParams( capacity, price, beginDate, endDate );
    }
}
