package ru.nsu.fit.bd.g16203.hotelInformationSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.IFloorDao;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.PersistException;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.WrongDataException;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Floor;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.FloorId;

import java.sql.SQLException;
import java.util.List;

@Service
public class FloorService implements IFloorService {
    @Autowired
    private IFloorDao floorDao;

    @Override
    public Floor getByPK(FloorId primaryKey) throws PersistException {
        return floorDao.getByPK(primaryKey);
    }

    @Override
    public int getPageNum() throws SQLException {
        return floorDao.getPageNum();
    }

    @Override
    public void update(Floor obj) throws PersistException, SQLException, WrongDataException {
        floorDao.update(obj);
    }

    @Override
    public void delete(FloorId primaryKey) throws PersistException, WrongDataException {
        floorDao.delete(primaryKey);
    }

    @Override
    public void create(Floor obj) throws PersistException, SQLException, WrongDataException {
        floorDao.create(obj);
    }

    @Override
    public List<Floor> getAll(int page) throws PersistException {
        return floorDao.getAll(page);
    }

    @Override
    public List<Floor> getAllByBuilding(int buildingId) throws SQLException {
        return floorDao.getAllByBuilding(buildingId);
    }
}
