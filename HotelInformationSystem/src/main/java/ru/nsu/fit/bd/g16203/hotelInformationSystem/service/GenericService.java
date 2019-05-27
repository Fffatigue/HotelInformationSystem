package ru.nsu.fit.bd.g16203.hotelInformationSystem.service;

import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.PersistException;

import java.sql.SQLException;
import java.util.List;

public interface GenericService<T, PK> {
    public T getByPK(PK primaryKey) throws PersistException;

    public int getPageNum() throws SQLException;

    public void update(T obj) throws PersistException;

    public void delete(PK primaryKey) throws PersistException;

    public void create(T obj) throws PersistException;

    public List<T> getAll(int page) throws PersistException;
}
