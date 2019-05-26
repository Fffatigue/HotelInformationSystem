package ru.nsu.fit.bd.g16203.hotelInformationSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.IClientDao;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.PersistException;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Client;

import java.util.List;

@Service
public class ClientService implements IClientService {
    @Autowired
    private IClientDao clientDao;

    @Override
    public Client getByPK(Integer primaryKey) throws PersistException {
        return clientDao.getByPK(primaryKey);
    }

    @Override
    public void update(Client obj) throws PersistException {
        clientDao.update(obj);
    }

    @Override
    public void delete(Integer primaryKey) throws PersistException {
        clientDao.deleteTransaction(primaryKey);
    }

    @Override
    public void create(Client obj) throws PersistException {
        clientDao.createTransaction(obj);
    }

    @Override
    public List<Client> getAll(int page) throws PersistException {
        return clientDao.getAll(page);
    }
}
