package ru.nsu.fit.bd.g16203.hotelInformationSystem.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReservationDao extends AbstractJDBCDao<Reservation, Integer> implements IReservationDao {

    @Override
    public String getSelectQuery() {
        return "SELECT * FROM reservation ";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO reservation (reservation_id, client_id, arrival_date, departure_date, room_num, building_id, floor_num) \n" +
                "VALUES (?, ?, ?, ?, ?, ?, ?);";
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
        statement.setInt( 1, obj.getPK() );
        statement.setInt( 2, obj.getClientId() );
        statement.setDate( 3, obj.getArrivalDate() );
        statement.setDate( 4, obj.getDepartureDate() );
        statement.setInt( 5, obj.getRoomId().getRoomNum() );
        statement.setInt( 6, obj.getRoomId().getFloorId().getBuildingId() );
        statement.setInt( 7, obj.getRoomId().getFloorId().getFloorNum() );
    }

    @Override
    protected void prepareStatementForDelete(PreparedStatement statement, Integer primaryKey) throws SQLException {
        statement.setInt( 1, primaryKey );
    }

    @Override
    protected void checkDataCreate(Reservation obj) throws SQLException, WrongDataException {
        String sql = "SELECT * FROM entity WHERE arrival_date<=? and ?<=departure_date and building_id = ? and floor_num = ?" +
                "and room_num = ?;";
        try (Connection c = jdbcTemplate.getDataSource().getConnection()) {
            try (PreparedStatement statement = c.prepareStatement( sql )) {
                statement.setDate( 1, obj.getDepartureDate() );
                statement.setDate( 2, obj.getArrivalDate() );
                statement.setInt( 3, obj.getRoomId().getFloorId().getBuildingId() );
                statement.setInt( 4, obj.getRoomId().getFloorId().getFloorNum() );
                statement.setInt( 5, obj.getRoomId().getRoomNum() );
                if (!parseResultSet( statement.executeQuery() ).isEmpty()) {
                    throw new WrongDataException( "room already reserved at that date" );
                }
            }
        }
    }

    @Override
    protected void checkDataUpdate(Reservation obj) throws SQLException, WrongDataException {
        String sql = "SELECT * FROM entity WHERE arrival_date<=? and ?<=departure_date and building_id = ? and floor_num = ?" +
                "and room_num = ?;";
        try (Connection c = jdbcTemplate.getDataSource().getConnection()) {
            try (PreparedStatement statement = c.prepareStatement( sql )) {
                statement.setDate( 1, obj.getDepartureDate() );
                statement.setDate( 2, obj.getArrivalDate() );
                statement.setInt( 3, obj.getRoomId().getFloorId().getBuildingId() );
                statement.setInt( 4, obj.getRoomId().getFloorId().getFloorNum() );
                statement.setInt( 5, obj.getRoomId().getRoomNum() );
                List<Reservation> reservations = parseResultSet( statement.executeQuery() );
                for (Reservation reservation : reservations) {
                    if (!reservation.getPK().equals( obj.getPK() )) {
                        throw new WrongDataException( "room already reserved at that date" );
                    }
                }
            }
        }
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
    protected List<Reservation> parseResultSet(ResultSet rs) throws SQLException {
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
