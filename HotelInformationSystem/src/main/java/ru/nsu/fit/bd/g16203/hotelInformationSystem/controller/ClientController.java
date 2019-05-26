package ru.nsu.fit.bd.g16203.hotelInformationSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.PersistException;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Client;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.service.IClientService;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {
    @Autowired
    private IClientService clientService;

    @GetMapping("/{clientId}")
    public Client getClient(@PathVariable int clientId) throws PersistException {
        return clientService.getByPK(clientId);
    }

    @DeleteMapping("/{clientId}")
    public void deleteClient(@PathVariable int clientId) throws PersistException {
        clientService.delete(clientId);
    }

    @PutMapping("/{clientId}")
    public void updateClient(@PathVariable int clientId, @RequestBody Client client) throws PersistException {
        client.setPK( clientId );
        clientService.update( client );
    }

    @PostMapping
    public Client createClient(@RequestBody Client client) throws PersistException {
        clientService.create( client );
        return client;
    }

    @GetMapping
    public List<Client> getClient() throws PersistException {
        return clientService.getAll();
    }
}
