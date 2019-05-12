package ru.nsu.fit.bd.g16203.hotelInformationSystem.dao;

import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Entity;

import java.util.List;

public interface GenericDao<T extends Entity, PK> {
    public T getByPK(PK primaryKey) throws PersistException;

    public void update(T obj) throws PersistException;

    public void delete(PK primaryKey) throws PersistException;

    public List<T> getAll() throws PersistException;

    public T create(T obj) throws PersistException;
}
