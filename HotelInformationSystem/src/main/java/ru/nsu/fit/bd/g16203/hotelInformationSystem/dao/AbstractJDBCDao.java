package ru.nsu.fit.bd.g16203.hotelInformationSystem.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Entity;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Transactional
@Repository
public abstract class AbstractJDBCDao<T extends Entity, PK extends Serializable> implements GenericDao<T, PK> {
    @Autowired
    protected JdbcTemplate jdbcTemplate;

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

    private static final int ROWS_PER_PAGE = 10;

    @Override
    public T getByPK(PK primaryKey) throws PersistException {
        List<T> list;
        String sql = getSelectQuery();
        sql += getIdComparisionStatementPart();
        try (Connection c = jdbcTemplate.getDataSource().getConnection()) {
            try (PreparedStatement statement = c.prepareStatement( sql )) {
                prepareStatementForGetByPK( statement, primaryKey );
                ResultSet rs = statement.executeQuery();
                list = parseResultSet( rs );
            }
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
    public List<T> getAll(int page) throws PersistException {
        List<T> list;
        String sql = getSelectQuery() + " LIMIT " + ROWS_PER_PAGE + " OFFSET " + String.valueOf( (page - 1) * ROWS_PER_PAGE );
        try (Connection c = jdbcTemplate.getDataSource().getConnection()) {
            try (PreparedStatement statement = c.prepareStatement( sql )) {
                ResultSet rs = statement.executeQuery();
                list = parseResultSet( rs );
            }
        } catch (Exception e) {
            throw new PersistException( e );
        }
        return list;
    }

    @Override
    public int getPageNum() throws SQLException {
        String sql = "SELECT COUNT(*) AS count FROM(" + getSelectQuery() + ")p";
        try (Connection c = jdbcTemplate.getDataSource().getConnection()) {
            try (PreparedStatement statement = c.prepareStatement( sql )) {
                ResultSet rs = statement.executeQuery();
                rs.next();
                int count = (rs.getInt( "count" ) + ROWS_PER_PAGE - 1)/ROWS_PER_PAGE;
                return count == 0? 1 : count;
            }
        }
    }

    @Override
    public void update(T obj) throws PersistException {
        String sql = getUpdateQuery();

        try (Connection c = jdbcTemplate.getDataSource().getConnection()) {
            try (PreparedStatement statement = c.prepareStatement( sql )) {
                prepareStatementForUpdate( statement, obj );
                int count = statement.executeUpdate();
                if (count != 1) {
                    throw new PersistException( "On update modify more then 1 record: " + count );
                }
            }
        } catch (Exception e) {
            throw new PersistException( e );
        }
    }

    @Override
    public void delete(PK primaryKey) throws PersistException {
        String sql = getDeleteQuery();
        System.out.println( sql );
        try (Connection c = jdbcTemplate.getDataSource().getConnection()) {
            try (PreparedStatement statement = c.prepareStatement( sql )) {
                prepareStatementForDelete( statement, primaryKey );
                int count = statement.executeUpdate();
                if (count != 1) {
                    throw new PersistException( "On delete modify more then 1 record: " + count );
                }
            }
        } catch (Exception e) {
            System.out.println( "msg" );
            throw new PersistException( e );
        }
    }

    @Override
    public void create(T object) throws PersistException {
        String sql = getCreateQuery();
        try (Connection c = jdbcTemplate.getDataSource().getConnection()) {
            try (PreparedStatement statement = c.prepareStatement( sql )) {
                prepareStatementForInsert( statement, object );
                int count = statement.executeUpdate();
                if (count != 1) {
                    throw new PersistException( "On create modify more then 1 record: " + count );
                }
            }
        } catch (Exception e) {
            throw new PersistException( e );
        }
    }

    @Override
    public void createTransaction(T object) throws PersistException {
        String sql = getCreateQuery();
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            connection.setAutoCommit( false );
            try (PreparedStatement statement = connection.prepareStatement( sql )) {
                prepareStatementForInsert( statement, object );
                int count = statement.executeUpdate();
                if (count != 1) {
                    connection.rollback();
                    connection.setAutoCommit( true );
                    throw new PersistException( "On create modify more then 1 record: " + count );
                }
                connection.commit();
                connection.setAutoCommit( true );
            }
        } catch (Exception e) {
            throw new PersistException( e );
        }
    }

    @Override
    public void deleteTransaction(PK primaryKey) throws PersistException {
        String sql = getDeleteQuery();
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            connection.setAutoCommit( false );
            try (PreparedStatement statement = connection.prepareStatement( sql )) {
                prepareStatementForDelete( statement, primaryKey );
                int count = statement.executeUpdate();
                if (count != 1) {
                    connection.rollback();
                    connection.setAutoCommit( true );
                    throw new PersistException( "On delete modify more then 1 record: " + count );
                }
                connection.commit();
                connection.setAutoCommit( true );
            }
        } catch (Exception e) {
            throw new PersistException( e );
        }

    }
}
