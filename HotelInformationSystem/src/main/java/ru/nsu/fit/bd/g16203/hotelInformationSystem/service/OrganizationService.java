package ru.nsu.fit.bd.g16203.hotelInformationSystem.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.IOrganizationDao;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.PersistException;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Organization;

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
    public void update(Organization obj) throws PersistException {
        organizationDao.update(obj);
    }

    @Override
    public void delete(Integer primaryKey) throws PersistException {
        organizationDao.delete(primaryKey);
    }

    @Override
    public void create(Organization obj) throws PersistException {
        organizationDao.create(obj);
    }

    @Override
    public List<Organization> getAll() throws PersistException {
        return organizationDao.getAll();
    }
}
