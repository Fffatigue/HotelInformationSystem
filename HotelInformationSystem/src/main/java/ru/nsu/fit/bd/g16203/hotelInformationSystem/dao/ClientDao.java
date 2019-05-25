package ru.nsu.fit.bd.g16203.hotelInformationSystem.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Repository
public class ClientDao extends AbstractJDBCDao<Client, Integer> implements IClientDao {
    @Override
    public String getSelectQuery() {
        return "SELECT * FROM individual";
    }

    @Override
    public String getCreateQuery() {
        return  "INSERT INTO client (client_id) \n" +
                "VALUES (?);\n" +
                "INSERT INTO individual (full_name, client_id) \n" +
                "VALUES (?, ?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE individual SET full_name = ? WHERE client_id = ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM individual WHERE client_id = ?;\n" +
                "DELETE FROM client WHERE client_id = ?;";
    }

    @Override
    protected String getIdComparisionStatementPart() {
        return "WHERE client_id = ?;";
    }

    @Override
    protected void prepareStatementForGetByPK(PreparedStatement statement, Integer primaryKey) throws SQLException {
        statement.setInt(1, primaryKey);
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Client obj) throws SQLException {
        statement.setString(1, obj.getName());
        statement.setInt(2, obj.getPK());
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Client obj) throws SQLException {
        statement.setInt(1, obj.getPK());
        statement.setString(2, obj.getName());
        statement.setInt(3, obj.getPK());
    }

    @Override
    protected void prepareStatementForDelete(PreparedStatement statement, Integer primaryKey) throws SQLException {
        statement.setInt(1, primaryKey);
        statement.setInt(2, primaryKey);
    }

    @Override
    protected List<Client> parseResultSet(ResultSet rs) throws SQLException {
        List<Client> clients = new ArrayList<>();
        while (rs.next()) {
            Client client = new Client();
            client.setName(rs.getString("full_name"));
            client.setPK(rs.getInt("client_id"));
            clients.add(client);
        }
        return clients;
    }

    @Override
    public Client create(Client object) throws PersistException {
        Client persistInstance;
        String sql = getCreateQuery();
        try (Connection connection = super.getJdbcTemplate().getDataSource().getConnection()) {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement( sql );
            prepareStatementForInsert( statement, object );
            int count = statement.executeUpdate();
            if (count != 1) {
                connection.rollback();
                connection.setAutoCommit(true);
                throw new PersistException( "On create modify more then 1 record: " + count );
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (Exception e) {
            throw new PersistException( e );
        }

        sql = getSelectQuery() + "WHERE id = last_insert_id();";

        try (PreparedStatement statement = super.getJdbcTemplate().getDataSource().getConnection().prepareStatement( sql )) {
            ResultSet rs = statement.executeQuery();
            List<Client> list = parseResultSet( rs );
            if ((list == null) || (list.size() != 1)) {
                throw new PersistException( "Exception on findByPK new create dao." );
            }
            persistInstance = list.iterator().next();
        } catch (Exception e) {
            throw new PersistException( e );
        }
        return persistInstance;
    }

    @Override
    public void delete(Integer primaryKey) throws PersistException {
        String sql = getDeleteQuery();
        try (Connection connection = super.getJdbcTemplate().getDataSource().getConnection()) {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement( sql );
            prepareStatementForDelete( statement,  primaryKey);
            int count = statement.executeUpdate();
            if (count != 1) {
                connection.rollback();
                connection.setAutoCommit(true);
                throw new PersistException( "On delete modify more then 1 record: " + count );
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (Exception e) {
            throw new PersistException( e );
        }
    }
}
