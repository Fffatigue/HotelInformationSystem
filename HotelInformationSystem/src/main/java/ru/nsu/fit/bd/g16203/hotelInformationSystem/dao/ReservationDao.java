package ru.nsu.fit.bd.g16203.hotelInformationSystem.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReservationDao extends AbstractJDBCDao<Reservation, Integer> implements IReservationDao {

    @Override
    public String getSelectQuery() {
        return "SELECT rs.reservation_id, rs.arrival_date, rs.departure_date, \n" +
                "       bl.name as building_name, rs.building_id, rs.floor_num, rs.room_num, \n" +
                "    rs.client_id, en.name, ind.full_name  \n" +
                " FROM reservation rs join building bl on (rs.building_id = bl.building_id)\n" +
                "     left join individual ind on (rs.client_id = ind.client_id)\n" +
                "     left join entity en on (rs.client_id = en.client_id) ";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO reservation (reservation_id, client_id, arrival_date, departure_date, room_num, building_id, floor_num) \n" +
                "VALUES (DEFAULT, ?, ?, ?, ?, ?, ?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE reservation SET client_id = ?, arrival_date = ?,  departure_date = ?, room_num = ?, building_id = ?, floor_num = ?" +
                "WHERE reservation_id = ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM reservation WHERE reservation_id = ?;";
    }

    @Override
    protected String getIdComparisionStatementPart() {
        return " WHERE reservation_id = ?;";
    }

    @Override
    protected void prepareStatementForGetByPK(PreparedStatement statement, Integer primaryKey) throws SQLException {
        statement.setInt( 1, primaryKey );
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Reservation obj) throws SQLException {
        statement.setInt( 1, obj.getClientId() );
        statement.setDate( 2, obj.getArrivalDate() );
        statement.setDate( 3, obj.getDepartureDate() );
        statement.setInt( 4, obj.getRoomId().getRoomNum() );
        statement.setInt( 5, obj.getRoomId().getFloorId().getBuildingId() );
        statement.setInt( 6, obj.getRoomId().getFloorId().getFloorNum() );
        statement.setInt( 7, obj.getPK() );
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Reservation obj) throws SQLException {
        statement.setInt( 1, obj.getClientId() );
        statement.setDate( 2, obj.getArrivalDate() );
        statement.setDate( 3, obj.getDepartureDate() );
        statement.setInt( 4, obj.getRoomId().getRoomNum() );
        statement.setInt( 5, obj.getRoomId().getFloorId().getBuildingId() );
        statement.setInt( 6, obj.getRoomId().getFloorId().getFloorNum() );
    }

    @Override
    protected void prepareStatementForDelete(PreparedStatement statement, Integer primaryKey) throws SQLException {
        statement.setInt( 1, primaryKey );
    }

    @Override
    protected void checkDataCreate(Reservation obj) throws SQLException, WrongDataException {
        String sql = "SELECT * FROM reservation WHERE arrival_date<=? and ?<=departure_date and building_id = ? and floor_num = ?" +
                "and room_num = ?;";
        try (Connection c = jdbcTemplate.getDataSource().getConnection()) {
            try (PreparedStatement statement = c.prepareStatement( sql )) {
                statement.setDate( 1, obj.getDepartureDate() );
                statement.setDate( 2, obj.getArrivalDate() );
                statement.setInt( 3, obj.getRoomId().getFloorId().getBuildingId() );
                statement.setInt( 4, obj.getRoomId().getFloorId().getFloorNum() );
                statement.setInt( 5, obj.getRoomId().getRoomNum() );
                if (!parseResultSetIn( statement.executeQuery() ).isEmpty()) {
                    throw new WrongDataException( "room already reserved at that date" );
                }
            }
        }
    }

    @Override
    protected void checkDataUpdate(Reservation obj) throws SQLException, WrongDataException {
        String sql = "SELECT * FROM reservation WHERE arrival_date<=? and ?<=departure_date and building_id = ? and floor_num = ?" +
                "and room_num = ?;";
        try (Connection c = jdbcTemplate.getDataSource().getConnection()) {
            try (PreparedStatement statement = c.prepareStatement( sql )) {
                statement.setDate( 1, obj.getDepartureDate() );
                statement.setDate( 2, obj.getArrivalDate() );
                statement.setInt( 3, obj.getRoomId().getFloorId().getBuildingId() );
                statement.setInt( 4, obj.getRoomId().getFloorId().getFloorNum() );
                statement.setInt( 5, obj.getRoomId().getRoomNum() );
                List<Reservation> reservations = parseResultSetIn( statement.executeQuery() );
                for (Reservation reservation : reservations) {
                    if (!reservation.getPK().equals( obj.getPK() )) {
                        throw new WrongDataException( "room already reserved at that date" );
                    }
                }
            }
        }
    }

    @Override
    public Reservation getByPK(Integer primaryKey) throws PersistException {
        List<Reservation> list;
        String sql = "select rs.reservation_id, rs.arrival_date, rs.departure_date, \n" +
                "            bl.name as building_name, rs.building_id, rs.floor_num, rs.room_num, \n" +
                "            rs.client_id, en.name, ind.full_name  from reservation rs \n" +
                "     join building bl on (rs.building_id = bl.building_id)\n" +
                "               left join individual ind on (rs.client_id = ind.client_id)\n" +
                "               left join entity en on (rs.client_id = en.client_id)\n" +
                "      WHERE reservation_id = ?";
        try (Connection c = jdbcTemplate.getDataSource().getConnection()) {
            try (PreparedStatement statement = c.prepareStatement( sql )) {
                prepareStatementForGetByPK( statement, primaryKey );
                ResultSet rs = statement.executeQuery();
                list = parseResultSet( rs );
            }
        } catch (Exception e) {
            throw new PersistException( e );
        }
        if (list == null || list.isEmpty()) {
            return null;
        }
        if (list.size() > 1) {
            throw new PersistException( "Received more than one record." );
        }
        return list.iterator().next();
    }

    @Override
    public void insertAvailableService(Integer reservationId, Integer serviceId) throws SQLException {
        String sql = "insert into used_service values (?,?)";
        try (Connection c = jdbcTemplate.getDataSource().getConnection()) {
            try (PreparedStatement statement = c.prepareStatement( sql )) {
                statement.setInt( 1, reservationId );
                statement.setInt( 2, serviceId );
                statement.executeUpdate();
            }
        }
    }

    @Override
    public void deleteAvailableService(Integer reservationId, Integer serviceId) throws SQLException {
        String sql = "delete from used_service where reservation_id = ? and service_id = ?";
        try (Connection c = jdbcTemplate.getDataSource().getConnection()) {
            try (PreparedStatement statement = c.prepareStatement( sql )) {
                statement.setInt( 1, reservationId );
                statement.setInt( 2, serviceId );
                statement.executeUpdate();
            }
        }
    }

    @Override
    public List<Service> getAvailableServices(Integer reservationId) throws SQLException {
        String sql = "select from used_service where used_id = ?";
        try (Connection c = jdbcTemplate.getDataSource().getConnection()) {
            try (PreparedStatement statement = c.prepareStatement( sql )) {
                statement.setInt( 1, reservationId );
                return parseServiceResultSet( statement.executeQuery() );
            }
        }
    }

    @Override
    public OrganizationReserves getNumRoomsReservedOrg(Integer organization_id, Date dateFrom, Date dateTo) throws SQLException{
        String sql = "select en.name, count(*) from reservation rs\n" +
                " inner join entity en on (rs.client_id = en.client_id)\n" +
                " inner join room rm on (rm.room_num = rs.room_num and \n" +
                "    rm.floor_num = rs.floor_num and \n" +
                "    rm.building_id = rs.building_id)\n" +
                " where en.client_id = ? and\n" +
                "    rs.departure_date <= ? and\n" +
                "    rs.arrival_date >= ?\n" +
                " group by en.name";
        Integer count;
        try (Connection c = jdbcTemplate.getDataSource().getConnection()) {
            try (PreparedStatement statement = c.prepareStatement( sql )) {
                statement.setInt(1, organization_id);
                statement.setDate(2, dateTo);
                statement.setDate(3, dateFrom);
                ResultSet resultSet = statement.executeQuery();
                resultSet.next();
                count = resultSet.getInt("count");
            }
        }
        sql = "select en.name, count(*), rm.room_num, rm.building_id, rm.floor_num, rm.price, rm.capacity from reservation rs\n" +
                " join entity en on (rs.client_id = en.client_id)\n" +
                " join room rm on (rm.room_num = rs.room_num and \n" +
                "   rm.floor_num = rs.floor_num and \n" +
                "   rm.building_id = rs.building_id)\n" +
                " where en.client_id = ? and\n" +
                "   rs.departure_date <= ? and\n" +
                "   rs.arrival_date >= ?\n" +
                " group by en.name, rm.room_num, rm.building_id, rm.floor_num, rm.price, rm.capacity\n" +
                " having count(*) > 2\n" +
                " order by count(*) DESC";
        List<Room> rooms = new ArrayList<>();
        try (Connection c = jdbcTemplate.getDataSource().getConnection()) {
            try (PreparedStatement statement = c.prepareStatement( sql )) {
                statement.setInt(1, organization_id);
                statement.setDate(2, dateTo);
                statement.setDate(3, dateFrom);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    Room room = new Room();
                    RoomId roomId = new RoomId();
                    FloorId floorId = new FloorId();
                    floorId.setBuildingId( resultSet.getInt( "building_id" ) );
                    floorId.setBuildingName( resultSet.getString( "name" ) );
                    floorId.setFloorNum( resultSet.getInt( "floor_num" ) );
                    roomId.setFloorId( floorId );
                    roomId.setRoomNum( resultSet.getInt( "room_num" ) );
                    room.setPK( roomId );
                    room.setPrice( resultSet.getInt( "price" ) );
                    room.setCapacity( resultSet.getInt( "capacity" ) );
                    rooms.add( room );
                }
            }
        }
        return new OrganizationReserves(count, rooms);
    }

    public class OrganizationReserves {
        Integer count;
        List<Room> prefRooms;

        public OrganizationReserves(Integer count, List<Room> prefRooms) {
            this.count = count;
            this.prefRooms = prefRooms;
        }

        public List<Room> getPrefRooms() {
            return prefRooms;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        public void setPrefRooms(List<Room> prefRooms) {
            this.prefRooms = prefRooms;
        }
    }

    @Override
    protected List<Reservation> parseResultSet(ResultSet rs) throws SQLException {
        List<Reservation> reservations = new ArrayList<>();

        while (rs.next()) {
            Reservation reservation = new Reservation();
            RoomId roomId = new RoomId();
            FloorId floorId = new FloorId();
            floorId.setBuildingId( rs.getInt( "building_id" ) );
            floorId.setBuildingName(rs.getString("building_name"));
            floorId.setFloorNum( rs.getInt( "floor_num" ) );
            roomId.setRoomNum( rs.getInt( "room_num" ) );
            roomId.setFloorId( floorId );

            reservation.setPK( rs.getInt( "reservation_id" ) );
            reservation.setArrivalDate( rs.getDate( "arrival_date" ) );
            reservation.setDepartureDate( rs.getDate( "departure_date" ) );
            reservation.setClientId( rs.getInt( "client_id" ) );
            reservation.setRoomId( roomId );
            if (rs.getString("name") == null){
                reservation.setClientName(rs.getString("full_name"));
            } else {
                reservation.setClientName(rs.getString("name"));
            }

            reservations.add( reservation );
        }

        return reservations;
    }

    protected List<Reservation> parseResultSetIn(ResultSet rs) throws SQLException {
        List<Reservation> reservations = new ArrayList<>();

        while (rs.next()) {
            Reservation reservation = new Reservation();
            RoomId roomId = new RoomId();
            FloorId floorId = new FloorId();
            floorId.setBuildingId( rs.getInt( "building_id" ) );
            floorId.setFloorNum( rs.getInt( "floor_num" ) );
            roomId.setRoomNum( rs.getInt( "room_num" ) );
            roomId.setFloorId( floorId );

            reservation.setPK( rs.getInt( "reservation_id" ) );
            reservation.setArrivalDate( rs.getDate( "arrival_date" ) );
            reservation.setDepartureDate( rs.getDate( "departure_date" ) );
            reservation.setClientId( rs.getInt( "client_id" ) );
            reservation.setRoomId( roomId );
            reservations.add( reservation );
        }

        return reservations;
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
