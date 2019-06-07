package ru.nsu.fit.bd.g16203.hotelInformationSystem.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.FloorId;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Room;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.RoomId;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Service;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
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
    public List<Room> getFutureFreeRooms(Date date) throws SQLException{
        String sql = "select rm.building_id, b.name, rm.floor_num, rm.room_num, rm.price, rm.capacity from reservation rs\n" +
                "                 inner join room rm on (rm.room_num = rs.room_num and\n" +
                "                   rm.floor_num = rs.floor_num and \n" +
                "                   rm.building_id = rs.building_id)\n" +
                "                 inner join building b on (rm.building_id = b.building_id)\n" +
                "                 where rs.arrival_date <= ? and\n" +
                "                  ? <= rs.departure_date and\n" +
                "                  ? >= rs.departure_date\n" +
                "                 group by rm.building_id, b.name, rm.floor_num, rm.room_num, rm.price, rm.capacity;";

        Date curDate = new Date(System.currentTimeMillis());
        try (Connection c = jdbcTemplate.getDataSource().getConnection()) {
            try (PreparedStatement statement = c.prepareStatement( sql )) {
                statement.setDate(1, curDate);
                statement.setDate(2, curDate);
                statement.setDate(3, date);
                ResultSet resultSet = statement.executeQuery();
                resultSet.next();
                return parseResultSet(resultSet);
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
                LocalDate curDate = LocalDate.now();
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
                "    )\n" +
                ")l;";
        try (Connection c = jdbcTemplate.getDataSource().getConnection()) {
            try (PreparedStatement statement = c.prepareStatement( sql )) {
                LocalDate curDate = LocalDate.now();
                statement.setInt( 1, capacity );
                statement.setInt( 2, price );
                statement.setDate( 3, java.sql.Date.valueOf( curDate.toString() ) );
                statement.setDate( 4, java.sql.Date.valueOf( curDate.toString() ) );
                ResultSet resultSet = statement.executeQuery();
                resultSet.next();
                return resultSet.getInt( "count" );
            }
        }

    }

    @Override
    public RoomInfo getRoomInfo(RoomId roomId) throws PersistException {
        String sql = "\tselect capacity, price, arrival_date from room r\n" +
                "\tleft outer join reservation re  \n" +
                "\ton(\n" +
                "            r.room_num = re.room_num and\n" +
                "            r.floor_num = re.floor_num and\n" +
                "            r.building_id = re.building_id\n" +
                "    )\n" +
                "\twhere r.room_num = ? and r.building_id = ? and r.floor_num = ? and arrival_date>?\n" +
                "\torder by arrival_date asc\n" +
                "limit 1;";
        try (Connection c = jdbcTemplate.getDataSource().getConnection()) {
            try (PreparedStatement statement = c.prepareStatement( sql )) {
                statement.setInt( 1, roomId.getRoomNum() );
                statement.setInt( 2, roomId.getFloorId().getBuildingId() );
                statement.setInt( 3, roomId.getFloorId().getFloorNum() );
                statement.setDate( 4, Date.valueOf( LocalDate.now() ) );
                ResultSet rs = statement.executeQuery();
                List<RoomInfo> list = parseRoomInfoSet( rs );
                if (list == null || list.isEmpty()) {
                    return null;
                }
                if (list.size() > 1) {
                    throw new PersistException( "Received more than one record." );
                }
                return list.iterator().next();
            }
        } catch (Exception e) {
            throw new PersistException( e );
        }
    }

    @Override
    public RoomDao.CurClientInfo getCurClientInfo(RoomId roomId) throws PersistException, SQLException {
        String sql = "select re.client_id, sr.service_id, sr.name, sr.price from reservation re\n" +
                " join used_service us on (re.reservation_id = us.reservation_id)\n" +
                " join service sr on (us.service_id = sr.service_id)\n" +
                " where re.room_num = ? and\n" +
                "      re.building_id = ? and\n" +
                "      re.floor_num = ? and\n" +
                "      re.arrival_date <= ?  and\n" +
                "      re.departure_date >= ?;";
        Date curDate = new Date(System.currentTimeMillis());
        Integer extraPrice = 0;
        List<Service> services = new ArrayList<>();
        try (Connection c = jdbcTemplate.getDataSource().getConnection()) {
            try (PreparedStatement statement = c.prepareStatement( sql )) {
                statement.setInt(1, roomId.getRoomNum() );
                statement.setInt(2, roomId.getFloorId().getBuildingId() );
                statement.setInt(3, roomId.getFloorId().getFloorNum() );
                statement.setDate(4, curDate);
                statement.setDate(5, curDate);
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    Service service = new Service();
                    Integer price = rs.getInt("price");
                    extraPrice += price;
                    service.setPK(rs.getInt("service_id"));
                    service.setName(rs.getString("name"));
                    service.setPrice(price);
                    services.add(service);
                }
            }
        }

        sql = "select rs.client_id, rv.comment from reservation rs\n" +
                " join review rv on (rv.reservation_id = rs.reservation_id)\n" +
                " where rs.room_num = ? and\n" +
                "      rs.building_id = ? and\n" +
                "      rs.floor_num = ? and\n" +
                "      rs.arrival_date <= ?  and\n" +
                "      rs.departure_date >= ?";
        List<String> complains = new ArrayList<>();
        try (Connection c = jdbcTemplate.getDataSource().getConnection()) {
            try (PreparedStatement statement = c.prepareStatement( sql )) {
                statement.setInt(1, roomId.getRoomNum() );
                statement.setInt(2, roomId.getFloorId().getBuildingId() );
                statement.setInt(3, roomId.getFloorId().getFloorNum() );
                statement.setDate(4, curDate);
                statement.setDate(5, curDate);
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    complains.add(rs.getString("comment"));
                }
            }
        }
        return new CurClientInfo(extraPrice, services, complains);
    }

    private List<RoomInfo> parseRoomInfoSet(ResultSet rs) throws SQLException {
        List<RoomInfo> roomInfos = new ArrayList<>();
        while (rs.next()) {
            RoomInfo roomInfo = new RoomInfo();
            roomInfos.add( roomInfo );
            roomInfo.setArrivalDate( rs.getDate( "arrival_date" ).toLocalDate() );
            roomInfo.setCapacity( rs.getInt( "capacity" ) );
            roomInfo.setPrice( rs.getInt( "price" ) );
        }
        return roomInfos;
    }

    public List<RoomDao.RoomProfitability> getRoomsProfitability() throws SQLException {
        String sql = "select tb1.name, tb1.room_num, tb1.building_id, tb1.floor_num, room_sum*100/service_sum as profitability from (\n" +
                " select buil.name, rs.room_num, rs.building_id, rs.floor_num, sum(sr.price) as service_sum from reservation rs\n" +
                "  join used_service us on (rs.reservation_id = us.reservation_id)\n" +
                "  join service sr on (sr.service_id = us.service_id)\n" +
                "  join building buil on (buil.building_id = rs.building_id)\n" +
                "  group by buil.name, rs.room_num, rs.building_id, rs.floor_num\n" +
                " ) tb1\n" +
                " join (\n" +
                " select rm.room_num, rm.building_id, rm.floor_num, sum(rm.price) as room_sum from reservation rs\n" +
                "  join room rm on (rm.room_num = rs.room_num and \n" +
                "    rm.building_id = rs.building_id and \n" +
                "    rm.floor_num = rs.floor_num)\n" +
                "  group by rm.room_num, rm.building_id, rm.floor_num\n" +
                " ) tb2 on (tb1.room_num = tb2.room_num and \n" +
                "    tb1.building_id = tb2.building_id and \n" +
                "    tb1.floor_num = tb2.floor_num)";

        List<RoomProfitability> roomProfitabilities = new ArrayList<>();
        try (Connection c = jdbcTemplate.getDataSource().getConnection()) {
            try (PreparedStatement statement = c.prepareStatement( sql )) {
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    RoomId roomId = new RoomId();
                    FloorId floorId = new FloorId();
                    floorId.setBuildingId(rs.getInt("building_id"));
                    floorId.setFloorNum(rs.getInt("floor_num"));
                    floorId.setBuildingName(rs.getString("name"));
                    roomId.setFloorId(floorId);
                    roomId.setRoomNum(rs.getInt("room_num"));
                    Integer profitability = rs.getInt("profitability");
                    roomProfitabilities.add(new RoomProfitability(roomId, profitability));
                }
            }
        }
        return roomProfitabilities;
    }

    public List<Room> getRoomsByBuildingAndFloor(int buildingId, int floorNum) throws SQLException {
        String sql = "SELECT * from room where building_id = ? and floor_num = ?;";

        List<Room> rooms = new ArrayList<>();
        try (Connection c = jdbcTemplate.getDataSource().getConnection()) {
            try (PreparedStatement statement = c.prepareStatement( sql )) {
                statement.setInt(1, buildingId);
                statement.setInt(2, floorNum);
                ResultSet rs = statement.executeQuery();
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
            }
        }
        return rooms;
    }

    public class RoomInfo {
        private LocalDate arrivalDate;
        private int capacity;
        private int price;

        public RoomInfo() {

        }

        public RoomInfo(LocalDate arrivalDate, int capacity, int price) {
            this.arrivalDate = arrivalDate;
            this.capacity = capacity;
            this.price = price;
        }

        public LocalDate getArrivalDate() {
            return arrivalDate;
        }

        public void setArrivalDate(LocalDate arrivalDate) {
            this.arrivalDate = arrivalDate;
        }

        public int getCapacity() {
            return capacity;
        }

        public void setCapacity(int capacity) {
            this.capacity = capacity;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }
    }

    public class CurClientInfo {
        private Integer extraPrice;
        private List<Service> services;
        private List<String> complains;

        public CurClientInfo(Integer extraPrice, List<Service> services, List<String> complains) {
            this.extraPrice = extraPrice;
            this.services = services;
            this.complains = complains;
        }

        public Integer getExtraPrice() {
            return extraPrice;
        }

        public List<Service> getServices() {
            return services;
        }

        public List<String> getComplains() {
            return complains;
        }

        public void setExtraPrice(Integer extraPrice) {
            this.extraPrice = extraPrice;
        }

        public void setServices(List<Service> services) {
            this.services = services;
        }

        public void setComplains(List<String> complains) {
            this.complains = complains;
        }
    }

    public class RoomProfitability{
        private RoomId roomId;
        private Integer profitability;

        public RoomProfitability(RoomId roomId, Integer profitability) {
            this.roomId = roomId;
            this.profitability = profitability;
        }

        public Integer getProfitability() {
            return profitability;
        }

        public RoomId getRoomId() {
            return roomId;
        }

        public void setProfitability(Integer profitability) {
            this.profitability = profitability;
        }

        public void setRoomId(RoomId roomId) {
            this.roomId = roomId;
        }
    }

    @Override
    public List<Room> getAll(String filter, String sortBy, boolean sortAsc) throws PersistException, WrongDataException {
        List<Room> list;
        filter = '%' + filter + '%';
        String order = sortAsc ? "asc" : "desc";
        if (!sortBy.equals( "room_num" ) && !sortBy.equals( "name" )) {
            throw new WrongDataException( "Wrong arg for sorting" );
        }
        String sql = getSelectQuery() +
                " WHERE CAST(room_num as VARCHAR(9)) LIKE ? or CAST(floor_num as VARCHAR(9)) LIKE ? or name LIKE ? " +
                "or CAST(capacity as VARCHAR(9)) LIKE ? or CAST(price as VARCHAR(9)) LIKE ?" + " ORDER BY " + sortBy + " " + order;
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

    @Override
    public List<Room> getAll(int page, String sortBy, boolean sortAsc) throws PersistException, WrongDataException {
        List<Room> list;
        String order = sortAsc ? "asc" : "desc";
        if (!sortBy.equals( "room_num" ) && !sortBy.equals( "name" )) {
            throw new WrongDataException( "Wrong arg for sorting " + sortBy );
        }
        String sql = getSelectQuery() + " ORDER BY " + sortBy + " " + order + " LIMIT " + ROWS_PER_PAGE + " OFFSET " + String.valueOf( (page - 1) * ROWS_PER_PAGE );
        try (Connection c = jdbcTemplate.getDataSource().getConnection()) {
            try (PreparedStatement statement = c.prepareStatement( sql )) {
                ResultSet rs = statement.executeQuery();
                list = parseResultSet( rs );
            }
        } catch (Exception e) {
            throw new PersistException( e );
        }
        return list;
    }

}
