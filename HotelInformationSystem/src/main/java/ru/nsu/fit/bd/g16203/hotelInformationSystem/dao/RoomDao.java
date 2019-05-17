package ru.nsu.fit.bd.g16203.hotelInformationSystem.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.FloorId;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Room;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.RoomId;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Repository
public class RoomDao extends AbstractJDBCDao<Room, RoomId> implements IRoomDao {
    @Override
    public String getSelectQuery() {
        return "SELECT * FROM room ";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO room (room_num, price, capacity, building_id, floor_num) \n" +
                "VALUES (?, ?, ?, ?, ?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE room SET price = ? capacity = ? WHERE room_num = ? and floor_num = ? and building_id = ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM room WHERE room_num = ? and floor_num = ? and building_id = ?;";
    }

    @Override
    protected String getIdComparisionStatementPart() {
        return "WHERE room_num = ? AND building_id = ? AND floor_num = ?;";
    }

    @Override
    protected void prepareStatementForGetByPK(PreparedStatement statement, RoomId primaryKey) throws SQLException {
        statement.setInt( 1, primaryKey.getRoomNum() );
        statement.setInt( 2, primaryKey.getFloorId().getBuildingId() );
        statement.setInt( 3, primaryKey.getFloorId().getFloorNum() );
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Room obj) throws SQLException {
        statement.setInt( 1, obj.getPrice() );
        statement.setInt(
                2, obj.getCapacity() );
        statement.setInt( 3, obj.getPK().getRoomNum() );
        statement.setInt( 4, obj.getPK().getFloorId().getFloorNum() );
        statement.setInt( 5, obj.getPK().getFloorId().getBuildingId() );
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Room obj) throws SQLException {
        statement.setInt( 1, obj.getPK().getRoomNum() );
        statement.setInt( 2, obj.getPrice() );
        statement.setInt( 3, obj.getCapacity() );
        statement.setInt( 4, obj.getPK().getFloorId().getBuildingId() );
        statement.setInt( 5, obj.getPK().getFloorId().getFloorNum() );
    }

    @Override
    protected void prepareStatementForDelete(PreparedStatement statement, RoomId primaryKey) throws SQLException {
        statement.setInt( 1, primaryKey.getRoomNum() );
        statement.setInt( 2, primaryKey.getFloorId().getFloorNum() );
        statement.setInt( 3, primaryKey.getFloorId().getBuildingId() );
    }

    @Override
    protected List<Room> parseResultSet(ResultSet rs) throws SQLException {
        List<Room> rooms = new ArrayList<>();
        while (rs.next()) {
            Room room = new Room();
            RoomId roomId = new RoomId();
            FloorId floorId = new FloorId();
            roomId.setFloorId( floorId );
            room.setPK( roomId );
            rooms.add( room );
            floorId.setBuildingId( rs.getInt( "building_id" ) );
            floorId.setFloorNum( rs.getInt( "floor_num" ) );
            roomId.setRoomNum( rs.getInt( "room_num" ) );
            room.setPrice( rs.getInt( "price" ) );
            room.setCapacity( rs.getInt( "capacity" ) );
        }
        return rooms;
    }
}
