package ru.nsu.fit.bd.g16203.hotelInformationSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.IServiceDao;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.PersistException;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Service;

import java.util.List;

@org.springframework.stereotype.Service
public class ServiceService implements IServiceDao {
    @Autowired
    private IServiceDao serviceDao;

    @Override
    public Service getByPK(Integer primaryKey) throws PersistException {
        return serviceDao.getByPK(primaryKey);
    }

    @Override
    public void update(Service obj) throws PersistException {
        serviceDao.update(obj);
    }

    @Override
    public void delete(Integer primaryKey) throws PersistException {
        serviceDao.delete(primaryKey);
    }

    @Override
    public Service create(Service obj) throws PersistException {
        return serviceDao.create(obj);
    }

    @Override
    public List<Service> getAll() throws PersistException {
        return serviceDao.getAll();
    }
}
