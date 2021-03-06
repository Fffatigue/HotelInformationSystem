package ru.nsu.fit.bd.g16203.hotelInformationSystem.dao;

import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Entity;

import java.sql.SQLException;
import java.util.List;

public interface GenericDao<T extends Entity, PK> {
    public T getByPK(PK primaryKey) throws PersistException;

    public void update(T obj) throws PersistException, WrongDataException, SQLException;

    public void delete(PK primaryKey) throws PersistException, WrongDataException;

    public List<T> getAll(int page) throws PersistException;

    public int getPageNum() throws SQLException;

    public void create(T obj) throws PersistException, WrongDataException, SQLException;

    public void createTransaction(T object) throws PersistException, WrongDataException, SQLException;

    public void deleteTransaction(PK primaryKey) throws PersistException, WrongDataException;
}
