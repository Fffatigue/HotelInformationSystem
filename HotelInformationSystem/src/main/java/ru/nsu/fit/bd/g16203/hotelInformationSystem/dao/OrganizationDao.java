package ru.nsu.fit.bd.g16203.hotelInformationSystem.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Organization;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Transactional
@Repository
public class OrganizationDao extends AbstractJDBCDao<Organization, Integer> implements IOrganizationDao {
    @Override
    public String getSelectQuery() {
        return "SELECT * FROM entity";
    }

    @Override
    public String getCreateQuery() {
        return "WITH rowss AS (INSERT INTO client (client_id) VALUES (DEFAULT) returning client_id) \n" +
                "INSERT INTO entity (name, discount, client_id) values (?, ?, (SELECT client_id FROM rowss));";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE entity SET name = ?, discount = ? WHERE client_id = ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM entity WHERE client_id = ?;\n" +
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
    protected void prepareStatementForUpdate(PreparedStatement statement, Organization obj) throws SQLException {
        statement.setString( 1, obj.getName() );
        statement.setInt( 2, obj.getDiscount() );
        statement.setInt( 3, obj.getPK() );
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Organization obj) throws SQLException {
        statement.setString( 1, obj.getName() );
        statement.setInt( 2, obj.getDiscount() );
    }

    @Override
    protected void prepareStatementForDelete(PreparedStatement statement, Integer primaryKey) throws SQLException {
        statement.setInt( 1, primaryKey );
        statement.setInt( 2, primaryKey );
    }

    @Override
    protected List<Organization> parseResultSet(ResultSet rs) throws SQLException {
        List<Organization> organizations = new ArrayList<>();
        while (rs.next()) {
            Organization organization = new Organization();
            organization.setName( rs.getString( "name" ) );
            organization.setPK( rs.getInt( "client_id" ) );
            organization.setDiscount( rs.getInt( "discount" ) );
            organizations.add( organization );
        }
        return organizations;
    }

    @Override
    public List<Organization> getOrganizationReservedMoreThenCountInPeriod(int count, Date beginDate, Date endDate) throws PersistException, SQLException {
        String sql = "select name, discount, client_id from(\n" +
                "    select name, discount, r.client_id, count(*) from\n" +
                "        reservation r\n" +
                "        join entity e on e.client_id = r.client_id\n" +
                "\t\twhere ? <=arrival_date and ? >=departure_date\n" +
                "    \tgroup by name, discount, r.client_id\n" +
                "    )t\n" +
                "where count>= ?;";

        try (Connection c = jdbcTemplate.getDataSource().getConnection()) {
            try (PreparedStatement statement = c.prepareStatement( sql )) {
                statement.setDate( 1, java.sql.Date.valueOf( beginDate.toString() ) );
                statement.setDate( 2, java.sql.Date.valueOf( endDate.toString() ) );
                statement.setInt( 3, count );
                return parseResultSet( statement.executeQuery() );
            }
        }
    }

    @Override
    public List<Organization> getOrganizationReservedMoreThenCount(int count) throws PersistException, SQLException {
        String sql = "select name, discount, client_id from(\n" +
                "    select name, discount, r.client_id, count(*) from\n" +
                "        reservation r\n" +
                "        join entity e on e.client_id = r.client_id\n" +
                "    \tgroup by name, discount, r.client_id\n" +
                "    )t\n" +
                "where count>=?;";

        try (Connection c = jdbcTemplate.getDataSource().getConnection()) {
            try (PreparedStatement statement = c.prepareStatement( sql )) {
                statement.setInt( 1, count );
                return parseResultSet( statement.executeQuery() );
            }
        }
    }
}
