package ru.nsu.fit.bd.g16203.hotelInformationSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.IFloorDao;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.PersistException;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Floor;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.FloorId;

import java.util.List;

public class FloorService implements IFloorService {
    @Autowired
    private IFloorDao floorDao;

    @Override
    public Floor getByPK(FloorId primaryKey) throws PersistException {
        return floorDao.getByPK(primaryKey);
    }

    @Override
    public void update(Floor obj) throws PersistException {
        floorDao.update(obj);
    }

    @Override
    public void delete(FloorId primaryKey) throws PersistException {
        floorDao.delete(primaryKey);
    }

    @Override
    public Floor create(Floor obj) throws PersistException {
        return floorDao.create(obj);
    }

    @Override
    public List<Floor> getAll() throws PersistException {
        return floorDao.getAll();
    }
}
