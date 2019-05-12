package ru.nsu.fit.bd.g16203.hotelInformationSystem.model;

public class UsedServiceId {
    private int serviceId;
    private int reservationId;

    public UsedServiceId() {
    }

    public UsedServiceId(int serviceId, int reservationId) {
        this.serviceId = serviceId;
        this.reservationId = reservationId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }
}
