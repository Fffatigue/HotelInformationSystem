package ru.nsu.fit.bd.g16203.hotelInformationSystem.dao;

import org.springframework.stereotype.Repository;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Organization;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Service;

import javax.transaction.Transactional;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ServiceDao extends AbstractJDBCDao<Service, Integer> implements IServiceDao {
    @Override
    public String getSelectQuery() {
        return "SELECT * FROM service ";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO service (service_id, name, price) \n" +
                "VALUES (?, ?, ?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE service SET price = ?, name = ? WHERE service_id = ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM service WHERE service_id = ?;";
    }

    @Override
    protected String getIdComparisionStatementPart() {
        return " WHERE service_id = ?;";
    }

    @Override
    protected void prepareStatementForGetByPK(PreparedStatement statement, Integer primaryKey) throws SQLException {
        statement.setInt( 1, primaryKey );
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Service obj) throws SQLException {
        statement.setInt( 1, obj.getPrice() );
        statement.setString( 2, obj.getName() );
        statement.setInt( 3, obj.getPK() );
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Service obj) throws SQLException {
        statement.setInt( 1, obj.getPK() );
        statement.setString( 2, obj.getName() );
        statement.setInt( 3, obj.getPrice() );
    }

    @Override
    protected void prepareStatementForDelete(PreparedStatement statement, Integer primaryKey) throws SQLException {
        statement.setInt( 1, primaryKey );
    }

    @Override
    protected void checkDataCreate(Service obj) throws SQLException, WrongDataException {
        if (obj.getPrice() < 0) {
            throw new WrongDataException( "Price must be positive" );
        }
        String sql = "SELECT * FROM service WHERE name = ?;";
        try (Connection c = jdbcTemplate.getDataSource().getConnection()) {
            try (PreparedStatement statement = c.prepareStatement( sql )) {
                statement.setString( 1, obj.getName() );
                List<Service> services = parseResultSet( statement.executeQuery() );
                if (!services.isEmpty()) {
                    throw new WrongDataException( "name is already used" );
                }
            }
        }
    }

    @Override
    protected void checkDataUpdate(Service obj) throws SQLException, WrongDataException {
        if (obj.getPrice() < 0) {
            throw new WrongDataException( "Price must be positive" );
        }
        String sql = "SELECT * FROM service WHERE name = ?;";
        try (Connection c = jdbcTemplate.getDataSource().getConnection()) {
            try (PreparedStatement statement = c.prepareStatement( sql )) {
                statement.setString( 1, obj.getName() );
                List<Service> services = parseResultSet( statement.executeQuery() );
                for (Service service : services) {
                    if (service.getName().equals( obj.getName() ) && !service.getPK().equals( obj.getPK() )) {
                        throw new WrongDataException( "name is already used" );
                    }
                }
            }
        }
    }

    @Override
    protected List<Service> parseResultSet(ResultSet rs) throws SQLException {
        List<Service> services = new ArrayList<>();
        while (rs.next()) {
            Service service = new Service();
            Integer serviceId = rs.getInt( "service_id" );
            service.setPK( serviceId );
            service.setPrice( rs.getInt( "price" ) );
            service.setName( rs.getString( "name" ) );
            services.add( service );
        }
        return services;
    }
}
