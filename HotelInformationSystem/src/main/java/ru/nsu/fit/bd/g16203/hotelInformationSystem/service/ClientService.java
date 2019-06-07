package ru.nsu.fit.bd.g16203.hotelInformationSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.ClientDao;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.IClientDao;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.PersistException;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.WrongDataException;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Client;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class ClientService implements IClientService {
    @Autowired
    private IClientDao clientDao;

    @Override
    public Client getByPK(Integer primaryKey) throws PersistException {
        return clientDao.getByPK( primaryKey );
    }

    @Override
    public int getPageNum() throws SQLException {
        return clientDao.getPageNum();
    }

    @Override
    public void update(Client obj) throws PersistException, SQLException, WrongDataException {
        clientDao.update( obj );
    }

    @Override
    public void delete(Integer primaryKey) throws PersistException, WrongDataException {
        clientDao.deleteTransaction( primaryKey );
    }

    @Override
    public void create(Client obj) throws PersistException, SQLException, WrongDataException {
        clientDao.createTransaction( obj );
    }

    @Override
    public List<Client> getAll(int page) throws PersistException {
        return clientDao.getAll( page );
    }

    @Override
    public List<Client> getAllReservedRoomsInPeriodWithParams(int capacity, int price, LocalDate beginDate, LocalDate endDate) throws PersistException, SQLException {
        return clientDao.getAllReservedRoomsInPeriodWithParams( capacity, price, beginDate, endDate );
    }

    @Override
    public List<ClientDao.ClientComment> getAngryComments() throws PersistException, SQLException{
        return clientDao.getAngryComments();
    }
}
