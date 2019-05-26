package ru.nsu.fit.bd.g16203.hotelInformationSystem.dao;

import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Client;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface IClientDao extends  GenericDao<Client, Integer>{

    List<Client> getAllReservedRoomsInPeriodWithParams(int capacity, int price, Date beginDate, Date endDate) throws PersistException, SQLException;
}
