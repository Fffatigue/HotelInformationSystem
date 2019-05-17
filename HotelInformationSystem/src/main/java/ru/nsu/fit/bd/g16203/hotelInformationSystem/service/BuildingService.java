package ru.nsu.fit.bd.g16203.hotelInformationSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.IBuildingDao;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.IRoomDao;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.PersistException;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Building;

import java.util.List;

@Service
public class BuildingService implements IBuildingService{
    @Autowired
    private IBuildingDao buildingDao;

    @Override
    public Building getByPK(Integer primaryKey) throws PersistException {
        return buildingDao.getByPK(primaryKey);
    }

    @Override
    public void update(Building obj) throws PersistException {
        buildingDao.update(obj);
    }

    @Override
    public void delete(Integer primaryKey) throws PersistException {
        buildingDao.delete(primaryKey);
    }

    @Override
    public Building create(Building obj) throws PersistException {
        return buildingDao.create(obj);
    }

    @Override
    public List<Building> getAll() throws PersistException {
        return buildingDao.getAll();
    }
}
