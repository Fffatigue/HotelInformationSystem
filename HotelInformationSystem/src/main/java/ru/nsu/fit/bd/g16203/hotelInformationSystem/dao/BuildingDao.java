package ru.nsu.fit.bd.g16203.hotelInformationSystem.dao;

import org.springframework.stereotype.Repository;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Building;

import javax.transaction.Transactional;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Transactional
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
        return "WHERE building_id = ?;";
    }

    @Override
    protected void prepareStatementForGetByPK(PreparedStatement statement, Integer primaryKey) throws SQLException {
        statement.setInt(1, primaryKey);
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Building obj) throws SQLException {
        statement.setString(1, obj.getName());
        statement.setInt(2, obj.getPK());
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Building obj) throws SQLException {
        statement.setString(1, obj.getName());
    }

    @Override
    protected void prepareStatementForDelete(PreparedStatement statement, Integer primaryKey) throws SQLException {
        statement.setNull(1, primaryKey);
    }

    @Override
    protected List<Building> parseResultSet(ResultSet rs) throws SQLException {
        List<Building> buildings = new ArrayList<>();
        while (rs.next()) {
            Building building = new Building();
            Integer buildingId = null;
            building.setPK(buildingId);
            buildings.add(building);
            building.setName(rs.getString("name"));
        }
        return buildings;
    }
}
