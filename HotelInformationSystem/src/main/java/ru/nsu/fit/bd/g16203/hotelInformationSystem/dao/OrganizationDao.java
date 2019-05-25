package ru.nsu.fit.bd.g16203.hotelInformationSystem.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Organization;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
        return  "INSERT INTO client (client_id) \n" +
                "VALUES (?);\n" +
                "INSERT INTO entity (name, discount, client_id) \n" +
                "VALUES (?, ?, ?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE entity SET name = ? discount = ? WHERE client_id = ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM entity WHERE client_id = ?;\n" +
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
    protected void prepareStatementForUpdate(PreparedStatement statement, Organization obj) throws SQLException {
        statement.setString(1, obj.getName());
        statement.setInt(2, obj.getDiscount());
        statement.setInt(3, obj.getPK());
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Organization obj) throws SQLException {
        statement.setInt(1, obj.getPK());
        statement.setString(2, obj.getName());
        statement.setInt(3, obj.getDiscount());
        statement.setInt(4, obj.getPK());
    }

    @Override
    protected void prepareStatementForDelete(PreparedStatement statement, Integer primaryKey) throws SQLException {
        statement.setInt(1, primaryKey);
        statement.setInt(2, primaryKey);
    }

    @Override
    protected List<Organization> parseResultSet(ResultSet rs) throws SQLException {
        List<Organization> organizations = new ArrayList<>();
        while (rs.next()) {
            Organization organization = new Organization();
            organization.setName(rs.getString("name"));
            organization.setPK(rs.getInt("client_id"));
            organization.setDiscount(rs.getInt("discount"));
            organizations.add(organization);
        }
        return organizations;
    }

}
