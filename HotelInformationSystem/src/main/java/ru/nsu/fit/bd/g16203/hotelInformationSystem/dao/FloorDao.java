package ru.nsu.fit.bd.g16203.hotelInformationSystem.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Floor;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.FloorId;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Transactional
@Repository
public class FloorDao extends AbstractJDBCDao<Floor, FloorId> implements IFloorDao {
    @Override
    public String getSelectQuery() {
        return "SELECT * FROM floor f join building b on b.building_id = f.building_id";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO floor (floor_num, building_id) \n" +
                "VALUES (?, ?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE floor SET building_id = ?, floor_num = ? WHERE floor_num = ? and building_id = ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM floor WHERE floor_num = ? AND building_id = ?;";
    }

    @Override
    protected String getIdComparisionStatementPart() {
        return " WHERE floor_num = ? AND building_id = ?;";
    }

    @Override
    protected void prepareStatementForGetByPK(PreparedStatement statement, FloorId primaryKey) throws SQLException {
        statement.setInt( 1, primaryKey.getFloorNum() );
        statement.setInt( 2, primaryKey.getBuildingId() );
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Floor obj) throws SQLException {
        statement.setInt( 1, obj.getPK().getFloorNum() );
        statement.setInt( 2, obj.getPK().getBuildingId() );
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Floor obj) throws SQLException {
        statement.setInt( 1, obj.getPK().getFloorNum() );
        statement.setInt( 2, obj.getPK().getBuildingId() );
    }

    @Override
    protected void prepareStatementForDelete(PreparedStatement statement, FloorId primaryKey) throws SQLException {
        statement.setInt( 1, primaryKey.getFloorNum() );
        statement.setInt( 2, primaryKey.getBuildingId() );
    }

    @Override
    protected List<Floor> parseResultSet(ResultSet rs) throws SQLException {
        List<Floor> floors = new ArrayList<>();
        while (rs.next()) {
            Floor floor = new Floor();
            FloorId floorId = new FloorId();
            floor.setPK( floorId );
            floors.add( floor );
            floorId.setBuildingId( rs.getInt( "building_id" ) );
            floorId.setFloorNum( rs.getInt( "floor_num" ) );
            floorId.setBuildingName( rs.getString( "name" ) );
        }
        return floors;
    }

    @Override
    public List<Floor> getAllByBuilding(int buildingId) throws SQLException {
        String sql = getSelectQuery() + " WHERE f.building_id = ?";
        try (Connection c = jdbcTemplate.getDataSource().getConnection()) {
            try (PreparedStatement statement = c.prepareStatement( sql )) {
                statement.setInt( 1, buildingId );
                ResultSet rs = statement.executeQuery();
                return parseResultSet( rs );
            }
        }
    }
}
