package ru.nsu.fit.bd.g16203.hotelInformationSystem.service;

import org.springframework.stereotype.Service;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.PersistException;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Client;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Service
public interface IClientService extends GenericService<Client, Integer> {
    List<Client> getAllReservedRoomsInPeriodWithParams(int capacity, int price, Date beginDate, Date endDate) throws PersistException, SQLException;
}
