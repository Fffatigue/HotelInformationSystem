package ru.nsu.fit.bd.g16203.hotelInformationSystem.model;

import org.apache.tomcat.jni.Local;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.time.LocalDate;

public class Reservation implements Entity<Integer> {
    private Integer reservationId;
    private RoomId roomId;
    private Integer clientId;
    private @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate arrivalDate;
    private @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate departureDate;

    public Reservation() {
    }

    public Reservation(RoomId roomId, Integer clientId, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate arrivalDate, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate departureDate) {
        this.roomId = roomId;
        this.clientId = clientId;
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
    }

    public Reservation(Integer reservationId, RoomId roomId, Integer clientId, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate arrivalDate, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate departureDate) {
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

    public @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate departureDate) {
        this.departureDate = departureDate;
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
