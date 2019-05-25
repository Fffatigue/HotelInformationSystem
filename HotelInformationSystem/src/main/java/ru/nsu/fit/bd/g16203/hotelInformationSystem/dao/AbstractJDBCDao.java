package ru.nsu.fit.bd.g16203.hotelInformationSystem.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Entity;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Transactional
@Repository
public abstract class AbstractJDBCDao<T extends Entity, PK extends Serializable> implements GenericDao<T, PK> {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public abstract String getSelectQuery();

    public abstract String getCreateQuery();

    public abstract String getUpdateQuery();

    public abstract String getDeleteQuery();

    protected abstract String getIdComparisionStatementPart();

    protected abstract void prepareStatementForGetByPK(PreparedStatement statement, PK primaryKey) throws SQLException;

    protected abstract void prepareStatementForUpdate(PreparedStatement statement, T obj) throws SQLException;

    protected abstract void prepareStatementForInsert(PreparedStatement statement, T obj) throws SQLException;

    protected abstract void prepareStatementForDelete(PreparedStatement statement, PK primaryKey) throws SQLException;

    protected abstract List<T> parseResultSet(ResultSet rs) throws SQLException;

    @Override
    public T getByPK(PK primaryKey) throws PersistException {
        List<T> list;
        String sql = getSelectQuery();
        sql += getIdComparisionStatementPart();
        try (PreparedStatement statement = jdbcTemplate.getDataSource().getConnection().prepareStatement( sql )) {
            prepareStatementForGetByPK( statement, primaryKey );
            ResultSet rs = statement.executeQuery();
            list = parseResultSet( rs );
        } catch (Exception e) {
            throw new PersistException( e );
        }
        if (list == null || list.size() == 0) {
            return null;
        }
        if (list.size() > 1) {
            throw new PersistException( "Received more than one record." );
        }
        return list.iterator().next();
    }

    @Override
    public List<T> getAll() throws PersistException {
        List<T> list;
        String sql = getSelectQuery();
        try (PreparedStatement statement = jdbcTemplate.getDataSource().getConnection().prepareStatement( sql )) {
            ResultSet rs = statement.executeQuery();
            list = parseResultSet( rs );
        } catch (Exception e) {
            throw new PersistException( e );
        }
        return list;
    }

    @Override
    public void update(T obj) throws PersistException {
        String sql = getUpdateQuery();

        try (PreparedStatement statement = jdbcTemplate.getDataSource().getConnection().prepareStatement( sql )) {
            prepareStatementForUpdate( statement, obj );
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new PersistException( "On update modify more then 1 record: " + count );
            }
        } catch (Exception e) {
            throw new PersistException( e );
        }
    }

    @Override
    public void delete(PK primaryKey) throws PersistException {
        String sql = getDeleteQuery();
        try (PreparedStatement statement = jdbcTemplate.getDataSource().getConnection().prepareStatement( sql )) {
            prepareStatementForDelete( statement,  primaryKey);
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new PersistException( "On delete modify more then 1 record: " + count );
            }
        } catch (Exception e) {
            throw new PersistException( e );
        }
    }

    @Override
    public T create(T object) throws PersistException {
        T persistInstance;

        String sql = getCreateQuery();
        try (PreparedStatement statement = jdbcTemplate.getDataSource().getConnection().prepareStatement( sql )) {
            prepareStatementForInsert( statement, object );
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new PersistException( "On create modify more then 1 record: " + count );
            }
        } catch (Exception e) {
            throw new PersistException( e );
        }

        sql = getSelectQuery() + "WHERE id = last_insert_id();";
        try (PreparedStatement statement = jdbcTemplate.getDataSource().getConnection().prepareStatement( sql )) {
            ResultSet rs = statement.executeQuery();
            List<T> list = parseResultSet( rs );
            if ((list == null) || (list.size() != 1)) {
                throw new PersistException( "Exception on findByPK new create dao." );
            }
            persistInstance = list.iterator().next();
        } catch (Exception e) {
            throw new PersistException( e );
        }
        return persistInstance;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
}
