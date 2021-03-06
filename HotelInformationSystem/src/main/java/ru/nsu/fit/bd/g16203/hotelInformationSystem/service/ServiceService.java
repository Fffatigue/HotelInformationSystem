package ru.nsu.fit.bd.g16203.hotelInformationSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.IServiceDao;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.PersistException;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.WrongDataException;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Service;

import java.sql.SQLException;
import java.util.List;

@org.springframework.stereotype.Service
public class ServiceService implements IServiceService {
    @Autowired
    private IServiceDao serviceDao;

    @Override
    public Service getByPK(Integer primaryKey) throws PersistException {
        return serviceDao.getByPK( primaryKey );
    }

    @Override
    public int getPageNum() throws SQLException {
        return serviceDao.getPageNum();
    }

    @Override
    public void update(Service obj) throws PersistException, SQLException, WrongDataException {
        serviceDao.update( obj );
    }

    @Override
    public void delete(Integer primaryKey) throws PersistException, WrongDataException {
        serviceDao.delete( primaryKey );
    }

    @Override
    public void create(Service obj) throws PersistException, SQLException, WrongDataException {
        serviceDao.create( obj );
    }

    @Override
    public List<Service> getAll(int page) throws PersistException {
        return serviceDao.getAll( page );
    }
}
