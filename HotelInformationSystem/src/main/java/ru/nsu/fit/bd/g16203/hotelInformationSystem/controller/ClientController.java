package ru.nsu.fit.bd.g16203.hotelInformationSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.PersistException;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Client;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.service.IClientService;

@RestController
@RequestMapping("client{clientId}")
public class ClientController {
    @Autowired
    private IClientService clientService;

    @RequestMapping
    public Client getClient(@PathVariable int clientId) throws PersistException {
        return clientService.getByPK(clientId);
    }

    @DeleteMapping
    public void deleteClient(@PathVariable int clientId) throws PersistException {
        clientService.delete(clientId);
    }

    @PutMapping
    public void updateClient(@PathVariable int clientId, @RequestBody Client client) throws PersistException {
        client.setPK( clientId );
        clientService.update( client );
    }

    @PostMapping
    public Client createClient(@PathVariable int clientId, @RequestBody Client client) throws PersistException {
        client.setPK( clientId );
        client = clientService.create( client );
        return client;
    }

    //TODO сделоть
}
