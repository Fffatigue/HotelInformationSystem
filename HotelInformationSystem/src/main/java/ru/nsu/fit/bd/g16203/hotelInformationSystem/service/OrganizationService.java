package ru.nsu.fit.bd.g16203.hotelInformationSystem.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.IOrganizationDao;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.PersistException;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.WrongDataException;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Organization;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class OrganizationService implements IOrganizationService{
    @Autowired
    private IOrganizationDao organizationDao;

    @Override
    public Organization getByPK(Integer primaryKey) throws PersistException {
        return organizationDao.getByPK(primaryKey);
    }

    @Override
    public int getPageNum() throws SQLException {
        return organizationDao.getPageNum();
    }

    @Override
    public void update(Organization obj) throws PersistException, SQLException, WrongDataException {
        organizationDao.update(obj);
    }

    @Override
    public void delete(Integer primaryKey) throws PersistException, WrongDataException {
        organizationDao.delete(primaryKey);
    }

    @Override
    public void create(Organization obj) throws PersistException, SQLException, WrongDataException {
        organizationDao.create(obj);
    }

    @Override
    public List<Organization> getAll(int page) throws PersistException {
        return organizationDao.getAll(page);
    }

    @Override
    public List<Organization> getOrganizationReservedMoreThenCountInPeriod(int count, LocalDate beginDate, LocalDate endDate) throws PersistException, SQLException {
        return organizationDao.getOrganizationReservedMoreThenCountInPeriod( count,beginDate,endDate );
    }

    @Override
    public List<Organization> getOrganizationReservedMoreThenCount(int count) throws PersistException, SQLException {
        return organizationDao.getOrganizationReservedMoreThenCount( count );
    }
}
