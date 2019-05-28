package ru.nsu.fit.bd.g16203.hotelInformationSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.IUsedServiceDao;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.PersistException;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.UsedService;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.UsedServiceId;

import java.sql.SQLException;
import java.util.List;

@Service
public class UsedServiceService implements IUsedServiceService{
    @Autowired
    private IUsedServiceDao serviceDao;


    @Override
    public UsedService getByPK(UsedServiceId primaryKey) throws PersistException {
        return serviceDao.getByPK( primaryKey );
    }

    @Override
    public int getPageNum() throws SQLException {
        return serviceDao.getPageNum();
    }

    @Override
    public void update(UsedService obj) throws PersistException {
        serviceDao.update(obj);
    }

    @Override
    public void delete(UsedServiceId primaryKey) throws PersistException {
        serviceDao.delete(primaryKey);
    }

    @Override
    public void create(UsedService obj) throws PersistException {
        serviceDao.create(obj);
    }

    @Override
    public List<UsedService> getAll(int page) throws PersistException {
        return serviceDao.getAll(page);
    }
}
