package ru.nsu.fit.bd.g16203.hotelInformationSystem.entities.model;

import java.io.Serializable;
import java.util.Objects;

public class UsedServiceId implements Serializable {
    private Service service;
    private Reservation reservation;

    public UsedServiceId() {
    }

    public UsedServiceId(Service service, Reservation reservation) {
        this.service = service;
        this.reservation = reservation;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsedServiceId that = (UsedServiceId) o;
        return Objects.equals( service, that.service ) &&
                Objects.equals( reservation, that.reservation );
    }

    @Override
    public int hashCode() {
        return Objects.hash( service, reservation );
    }
}
