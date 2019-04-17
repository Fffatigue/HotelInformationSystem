package ru.nsu.fit.bd.g16203.hotelInformationSystem.entities.model;

import javax.persistence.*;

@Entity
@IdClass( UsedServiceId.class )
@Table(name = "used_service")
public class UsedService {
    private Service service;
    private Reservation reservation;

    public UsedService() {
    }

    public UsedService(Service service, Reservation reservation) {
        this.service = service;
        this.reservation = reservation;
    }

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SERVICE", nullable = false)
    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RESERVATION", nullable = false)
    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }
}
