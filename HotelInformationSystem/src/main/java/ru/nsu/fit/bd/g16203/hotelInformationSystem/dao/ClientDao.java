package ru.nsu.fit.bd.g16203.hotelInformationSystem.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class ClientDao extends AbstractJDBCDao<Client, Integer> implements IClientDao {
    @Override
    public String getSelectQuery() {
        return "SELECT * FROM individual";
    }

    @Override
    public String getCreateQuery() {
        return "WITH rowss AS (INSERT INTO client (client_id) VALUES (DEFAULT) returning client_id) \n" +
                "INSERT INTO individual (full_name, client_id) values (?, (SELECT client_id FROM rowss));";
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
        return " WHERE client_id = ?;";
    }

    @Override
    protected void prepareStatementForGetByPK(PreparedStatement statement, Integer primaryKey) throws SQLException {
        statement.setInt( 1, primaryKey );
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Client obj) throws SQLException {
        statement.setString( 1, obj.getName() );
        statement.setInt( 2, obj.getPK() );
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Client obj) throws SQLException {
        statement.setString( 1, obj.getName() );
    }

    @Override
    protected void prepareStatementForDelete(PreparedStatement statement, Integer primaryKey) throws SQLException {
        statement.setInt( 1, primaryKey );
        statement.setInt( 2, primaryKey );
    }

    @Override
    protected void checkDataCreate(Client obj) throws SQLException, WrongDataException {
        //not used
    }

    @Override
    protected void checkDataUpdate(Client obj) throws SQLException, WrongDataException {
        //not used
    }

    @Override
    protected List<Client> parseResultSet(ResultSet rs) throws SQLException {
        List<Client> clients = new ArrayList<>();
        while (rs.next()) {
            Client client = new Client();
            client.setName( rs.getString( "full_name" ) );
            client.setPK( rs.getInt( "client_id" ) );
            clients.add( client );
        }
        return clients;
    }

    @Override
    public List<Client> getAllReservedRoomsInPeriodWithParams(int capacity, int price, LocalDate beginDate, LocalDate endDate) throws PersistException, SQLException {
        String sql = "select full_name, re.client_id from\n" +
                "    reservation re\n" +
                "    join room r\n" +
                "    on(\n" +
                "        r.room_num = re.room_num and\n" +
                "        r.floor_num = re.floor_num and\n" +
                "        r.building_id = re.building_id\n" +
                "    )\n" +
                "    join individual on re.client_id = individual.client_id"+
                "    where capacity=? and price=? and arrival_date>=? and\n" +
                "    arrival_date<=?\n";

        try (Connection c = jdbcTemplate.getDataSource().getConnection()) {
            try (PreparedStatement statement = c.prepareStatement( sql )) {
                statement.setInt( 1, capacity );
                statement.setInt( 2, price );
                statement.setDate( 3, java.sql.Date.valueOf( beginDate.toString() ) );
                statement.setDate( 4, java.sql.Date.valueOf( endDate.toString() ) );
                return parseResultSet( statement.executeQuery() );
            }
        }
    }

    @Override
    public List<Client> getMostFrequentClient() throws SQLException {
        String sql = "select full_name, r.client_id, count(full_name) as value_occurrence\n" +
                    "from reservation r\n" +
                    "join individual e on e.client_id = r.client_id\n" +
                    "group by full_name, r.client_id\n" +
                    "order by value_occurrence DESC";

        try (Connection c = jdbcTemplate.getDataSource().getConnection()) {
            try (PreparedStatement statement = c.prepareStatement( sql )) {
                return parseResultSet( statement.executeQuery() );
            }
        }
    }

    @Override
    public List<Client> getNewClients(LocalDate beginDate, LocalDate endDate) throws SQLException {
        String sql = "SELECT client_id FROM client\n" +
                "    EXCEPT(\n" +
                "        SELECT c.client_id FROM client c\n" +
                "        JOIN reservation re\n" +
                "        ON(\n" +
                "            c.client_id = re.client_id            \n" +
                "        )\n" +
                "        WHERE arrival_date<=? AND departure_date>=?\n" +
                "    )";

        try (Connection c = jdbcTemplate.getDataSource().getConnection()) {
            try (PreparedStatement statement = c.prepareStatement( sql )) {
                statement.setDate( 1, java.sql.Date.valueOf( beginDate.toString() ) );
                statement.setDate( 2, java.sql.Date.valueOf( endDate.toString() ) );
                return parseResultSet( statement.executeQuery() );
            }
        }
    }

    @Override
    public List<Client> getClientInfo(Integer clientId) throws SQLException {
        String sql = "select distinct cl.full_name, count(cl.full_name) from (\n" +
                "        select * from reservation r\n" +
                "        join individual i on i.client_id = r.client_id\n" +
                "\t\twhere i.client_id = ?\n" +
                ") as cl\n" +
                "group by cl.full_name \n" +
                "having count(full_name)>=1\n" +
                "order by full_name;";

        try (Connection c = jdbcTemplate.getDataSource().getConnection()) {
            try (PreparedStatement statement = c.prepareStatement( sql )) {
                statement.setInt( 1, clientId);
                return parseResultSet( statement.executeQuery() );
            }
        }
    }
}
