package ru.nsu.fit.bd.g16203.hotelInformationSystem.service;

import org.springframework.stereotype.Service;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.ClientDao;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.PersistException;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Client;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public interface IClientService extends GenericService<Client, Integer> {
    List<Client> getAllReservedRoomsInPeriodWithParams(int capacity, int price, LocalDate beginDate, LocalDate endDate) throws PersistException, SQLException;

    List<ClientDao.ClientComment> getAngryComments() throws PersistException, SQLException;
}
