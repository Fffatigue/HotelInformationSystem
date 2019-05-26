package ru.nsu.fit.bd.g16203.hotelInformationSystem.dao;

import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Organization;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface IOrganizationDao extends GenericDao<Organization, Integer> {
    List<Organization> getOrganizationReservedMoreThenCountInPeriod(int count, Date beginDate, Date endDate) throws PersistException, SQLException;
    List<Organization> getOrganizationReservedMoreThenCount(int count) throws PersistException, SQLException;
}
