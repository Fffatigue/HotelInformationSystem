package ru.nsu.fit.bd.g16203.hotelInformationSystem.model;

import java.sql.Date;

public class Reservation implements Entity<Integer> {
    private Integer reservationId;
    private RoomId roomId;
    private Integer clientId;
    private String clientName;
    private Date arrivalDate;
    private Date departureDate;

    public Reservation() {
    }

    public Reservation(RoomId roomId, Integer clientId, Date arrivalDate, Date departureDate) {
        this.roomId = roomId;
        this.clientId = clientId;
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
    }

    public Reservation(Integer reservationId, RoomId roomId, Integer clientId, Date arrivalDate, Date departureDate) {
        this.reservationId = reservationId;
        this.roomId = roomId;
        this.clientId = clientId;
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
    }

    public RoomId getRoomId() {
        return roomId;
    }

    public void setRoomId(RoomId roomId) {
        this.roomId = roomId;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    @Override
    public Integer getPK() {
        return reservationId;
    }

    @Override
    public void setPK(Integer primaryKey) {
        reservationId = primaryKey;
    }
}
