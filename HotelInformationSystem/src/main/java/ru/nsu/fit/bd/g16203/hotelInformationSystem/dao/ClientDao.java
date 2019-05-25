package ru.nsu.fit.bd.g16203.hotelInformationSystem.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Client;

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
}
