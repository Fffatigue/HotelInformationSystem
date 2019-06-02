package ru.nsu.fit.bd.g16203.hotelInformationSystem.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.FloorId;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Room;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.RoomId;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class RoomDao extends AbstractJDBCDao<Room, RoomId> implements IRoomDao {
    @Override
    public String getSelectQuery() {
        return "SELECT * FROM room r join building b on b.building_id = r.building_id";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO room (room_num, price, capacity, building_id, floor_num) \n" +
                "VALUES (?, ?, ?, ?, ?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE room SET price = ?, capacity = ? WHERE room_num = ? and floor_num = ? and building_id = ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM room WHERE room_num = ? and floor_num = ? and building_id = ?;";
    }

    @Override
    protected String getIdComparisionStatementPart() {
        return " WHERE r.room_num = ? AND r.building_id = ? AND r.floor_num = ?;";
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
    protected void checkDataCreate(Room obj) throws SQLException, WrongDataException {
        if (obj.getCapacity() < 1) {
            throw new WrongDataException( "Capacity must be >0" );
        }
        if (obj.getPrice() < 0) {
            throw new WrongDataException( "Price must be positive" );
        }
    }

    @Override
    protected void checkDataUpdate(Room obj) throws SQLException, WrongDataException {
        checkDataCreate( obj );
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
            floorId.setBuildingName( rs.getString( "name" ) );
            floorId.setFloorNum( rs.getInt( "floor_num" ) );
            roomId.setRoomNum( rs.getInt( "room_num" ) );
            room.setPrice( rs.getInt( "price" ) );
            room.setCapacity( rs.getInt( "capacity" ) );
        }
        return rooms;
    }

    @Override
    public List<Room> getAllByFloor(int floorNum) throws SQLException {
        String sql = getSelectQuery() + " WHERE floor_num = ?";
        try (Connection c = jdbcTemplate.getDataSource().getConnection()) {
            try (PreparedStatement statement = c.prepareStatement( sql )) {
                statement.setInt( 1, floorNum );
                ResultSet rs = statement.executeQuery();
                return parseResultSet( rs );
            }
        }
    }

    @Override
    public int getFreeRoomsCount() throws SQLException {
        String sql = "select count(1) as count from(\n" +
                "    select building_id,floor_num,room_num from room\n" +
                "    except(\n" +
                "        select r.building_id, r.floor_num, r.room_num from room r\n" +
                "        join reservation re\n" +
                "        on(\n" +
                "            r.room_num = re.room_num and\n" +
                "            r.floor_num = re.floor_num and\n" +
                "            r.building_id = re.building_id\n" +
                "        )\n" +
                "        where arrival_date<=? and departure_date>=?\n" +
                "    )\n" +
                ")s;";
        try (Connection c = jdbcTemplate.getDataSource().getConnection()) {
            try (PreparedStatement statement = c.prepareStatement( sql )) {
                Date curDate = new Date();
                statement.setDate( 1, java.sql.Date.valueOf( curDate.toString() ) );
                statement.setDate( 2, java.sql.Date.valueOf( curDate.toString() ) );
                ResultSet resultSet = statement.executeQuery();
                resultSet.next();
                return resultSet.getInt( "count" );
            }
        }
    }

    @Override
    public int getFreeRoomsWithParams(int capacity, int price) throws SQLException {
        String sql = "select count(1) as count from(\n" +
                "    select  building_id, floor_num, room_num from room t\n" +
                "    where capacity = ? and price = ?\n" +
                "    except(\n" +
                "        select r.building_id, r.floor_num, r.room_num from room r\n" +
                "        join reservation re\n" +
                "        on(\n" +
                "            r.room_num = re.room_num and\n" +
                "            r.floor_num = re.floor_num and\n" +
                "            r.building_id = re.building_id\n" +
                "        )\n" +
                "        where arrival_date<=? and departure_date>=?\n" +
                "        group by r.room_num, r.floor_num, r.building_id\n" +
                "    );";
        try (Connection c = jdbcTemplate.getDataSource().getConnection()) {
            try (PreparedStatement statement = c.prepareStatement( sql )) {
                Date curDate = new Date();
                statement.setInt( 1, 1 );
                statement.setInt( 2, 2 );
                statement.setDate( 3, java.sql.Date.valueOf( curDate.toString() ) );
                statement.setDate( 4, java.sql.Date.valueOf( curDate.toString() ) );
                ResultSet resultSet = statement.executeQuery();
                resultSet.next();
                return resultSet.getInt( "count" );
            }
        }

    }

    @Override
    public List<Room> getAll(String filter) throws PersistException {
        List<Room> list;
        String sql = getSelectQuery() +
                " WHERE CAST(room_num as VARCHAR(9)) LIKE ? or CAST(floor_num as VARCHAR(9)) LIKE ? or name LIKE ? " +
                "or CAST(capacity as VARCHAR(9)) LIKE ? or CAST(price as VARCHAR(9)) LIKE ?";
        try (Connection c = jdbcTemplate.getDataSource().getConnection()) {
            try (PreparedStatement statement = c.prepareStatement( sql )) {
                statement.setString( 1, filter );
                statement.setString( 2, filter );
                statement.setString( 3, filter );
                statement.setString( 4, filter );
                statement.setString( 5, filter );
                ResultSet rs = statement.executeQuery();
                list = parseResultSet( rs );
            }
        } catch (Exception e) {
            throw new PersistException( e );
        }
        return list;
    }

}
