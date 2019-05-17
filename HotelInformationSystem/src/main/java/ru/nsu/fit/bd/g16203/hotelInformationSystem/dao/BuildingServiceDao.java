package ru.nsu.fit.bd.g16203.hotelInformationSystem.dao;

import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.BuildingService;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.BuildingServiceId;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class BuildingServiceDao extends AbstractJDBCDao<BuildingService, BuildingServiceId> implements IBuildingServiceDao {
    @Override
    public String getSelectQuery() {
         return "SELECT * FROM building_service";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO building_service (building_id, service_id) \n" +
                "VALUES (?, ?)";
    }

    @Override
    public String getUpdateQuery() {
        return null;
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM building_service WHERE building_id = ?and service_id = ?;";
    }

    @Override
    protected String getIdComparisionStatementPart() {
        return "WHERE service_id = ? AND building_id = ?;";
    }

    @Override
    protected void prepareStatementForGetByPK(PreparedStatement statement, BuildingServiceId primaryKey) throws SQLException {

    }

    @Override
    protected void prepareStatementForGetByPK(PreparedStatement statement, Integer primaryKey) throws SQLException {

    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, BuildingService obj) throws SQLException {

    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, BuildingService obj) throws SQLException {

    }

    @Override
    protected void prepareStatementForDelete(PreparedStatement statement, BuildingServiceId primaryKey) throws SQLException {

    }

    @Override
    protected void prepareStatementForDelete(PreparedStatement statement, Integer primaryKey) throws SQLException {

    }

    @Override
    protected List<BuildingService> parseResultSet(ResultSet rs) throws SQLException {
        return null;
    }

    @Override
    public BuildingService getByPK(Integer primaryKey) throws PersistException {
        return null;
    }

    @Override
    public void delete(Integer primaryKey) throws PersistException {

    }
}
