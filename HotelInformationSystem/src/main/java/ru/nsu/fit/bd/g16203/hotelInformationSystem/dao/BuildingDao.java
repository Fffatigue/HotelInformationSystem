package ru.nsu.fit.bd.g16203.hotelInformationSystem.dao;

import org.springframework.stereotype.Repository;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Building;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Service;

import javax.transaction.Transactional;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BuildingDao extends AbstractJDBCDao<Building, Integer> implements IBuildingDao {
    @Override
    public String getSelectQuery() {
        return "SELECT * FROM building";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO building (name) \n" +
                "VALUES (?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE building SET name = ? WHERE building_id = ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM building WHERE building_id = ?;";
    }

    @Override
    protected String getIdComparisionStatementPart() {
        return " WHERE building_id = ?;";
    }

    @Override
    protected void prepareStatementForGetByPK(PreparedStatement statement, Integer primaryKey) throws SQLException {
        statement.setInt( 1, primaryKey );
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Building obj) throws SQLException {
        statement.setString( 1, obj.getName() );
        statement.setInt( 2, obj.getPK() );
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Building obj) throws SQLException {
        statement.setString( 1, obj.getName() );
    }

    @Override
    protected void prepareStatementForDelete(PreparedStatement statement, Integer primaryKey) throws SQLException {
        statement.setInt( 1, primaryKey );
    }

    @Override
    protected void checkDataCreate(Building obj) throws SQLException, WrongDataException {
        String sql = "SELECT * FROM building WHERE name = ?;";
        try (Connection c = jdbcTemplate.getDataSource().getConnection()) {
            try (PreparedStatement statement = c.prepareStatement( sql )) {
                statement.setString( 1, obj.getName() );
                if (statement.executeQuery().next()) {
                    throw new WrongDataException( "name is already used" );
                }
            }
        }
    }

    @Override
    protected void checkDataUpdate(Building obj) throws SQLException, WrongDataException {
        String sql = "SELECT * FROM building WHERE name = ?;";
        try (Connection c = jdbcTemplate.getDataSource().getConnection()) {
            try (PreparedStatement statement = c.prepareStatement( sql )) {
                statement.setString( 1, obj.getName() );
                List<Building> buildings = parseResultSet( statement.executeQuery() );
                for (Building building : buildings) {
                    if (building.getName().equals( obj.getName() ) && !building.getPK().equals( obj.getPK() )) {
                        throw new WrongDataException( "name is already used" );
                    }
                }
            }
        }
    }

    @Override
    protected List<Building> parseResultSet(ResultSet rs) throws SQLException {
        List<Building> buildings = new ArrayList<>();
        while (rs.next()) {
            Building building = new Building();
            Integer buildingId = rs.getInt( "building_id" );
            building.setPK( buildingId );
            buildings.add( building );
            building.setName( rs.getString( "name" ) );
        }
        return buildings;
    }

    @Override
    public void insertAvailableService(Integer buildingId, Integer serviceId) throws SQLException {
        String sql = "insert into building_service values (?,?)";
        try (Connection c = jdbcTemplate.getDataSource().getConnection()) {
            try (PreparedStatement statement = c.prepareStatement( sql )) {
                statement.setInt( 1, buildingId );
                statement.setInt( 2, serviceId );
                statement.executeUpdate();
            }
        }
    }

    @Override
    public void deleteAvailableService(Integer buildingId, Integer serviceId) throws SQLException {
        String sql = "delete from building_service where building_id = ? and service_id = ?";
        try (Connection c = jdbcTemplate.getDataSource().getConnection()) {
            try (PreparedStatement statement = c.prepareStatement( sql )) {
                statement.setInt( 1, buildingId );
                statement.setInt( 2, serviceId );
                statement.executeUpdate();
            }
        }
    }

    @Override
    public List<Service> getAvailableServices(Integer buildingId) throws SQLException {
        String sql = "select from building_service where building_id = ?";
        try (Connection c = jdbcTemplate.getDataSource().getConnection()) {
            try (PreparedStatement statement = c.prepareStatement( sql )) {
                statement.setInt( 1, buildingId );
                return parseServiceResultSet( statement.executeQuery() );
            }
        }
    }

    private List<Service> parseServiceResultSet(ResultSet rs) throws SQLException {
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
