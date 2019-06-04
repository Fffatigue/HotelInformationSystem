package ru.nsu.fit.bd.g16203.hotelInformationSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.IBuildingDao;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.IRoomDao;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.PersistException;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.WrongDataException;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Building;

import java.sql.SQLException;
import java.util.List;

@Service
public class BuildingService implements IBuildingService {
    @Autowired
    private IBuildingDao buildingDao;

    @Override
    public Building getByPK(Integer primaryKey) throws PersistException {
        return buildingDao.getByPK( primaryKey );
    }

    @Override
    public int getPageNum() throws SQLException {
        return buildingDao.getPageNum();
    }

    @Override
    public void update(Building obj) throws PersistException, SQLException, WrongDataException {
        buildingDao.update( obj );
    }

    @Override
    public void delete(Integer primaryKey) throws PersistException, WrongDataException {
        buildingDao.delete( primaryKey );
    }

    @Override
    public void create(Building obj) throws PersistException, SQLException, WrongDataException {
        buildingDao.create( obj );
    }

    @Override
    public List<Building> getAll(int page) throws PersistException {
        return buildingDao.getAll( page );
    }

    @Override
    public void insertAvailableService(Integer buildingId, Integer serviceId) throws SQLException {
        buildingDao.insertAvailableService( buildingId, serviceId );
    }

    @Override
    public void deleteAvailableService(Integer buildingId, Integer serviceId) throws SQLException {
        buildingDao.deleteAvailableService( buildingId, serviceId );
    }

    @Override
    public List<ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Service> getAvailableServices(Integer buildingId) throws SQLException {
        return buildingDao.getAvailableServices( buildingId );
    }
}
