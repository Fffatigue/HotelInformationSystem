package ru.nsu.fit.bd.g16203.hotelInformationSystem.service;

import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.PersistException;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Organization;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public interface IOrganizationService extends GenericService<Organization, Integer> {
    List<Organization> getOrganizationReservedMoreThenCountInPeriod(int count, LocalDate beginDate, LocalDate endDate) throws PersistException, SQLException;
    List<Organization> getOrganizationReservedMoreThenCount(int count) throws PersistException, SQLException;
}
