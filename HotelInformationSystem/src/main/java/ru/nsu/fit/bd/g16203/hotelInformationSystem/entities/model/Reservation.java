package ru.nsu.fit.bd.g16203.hotelInformationSystem.entities.model;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "reservation")
public class Reservation {
    private long reservationId;
    private Set<UsedService> usedServices = new HashSet<>(  );
    private Room room;
    private Client client;
    private Date arrivalDate;
    private Date departureDate;

    public Reservation() {
    }

    public Reservation(Room room, Client client, Date arrivalDate, Date departureDate) {
        this.room = room;
        this.client = client;
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "RESERVATION_ID", unique = true, nullable = false)
    public long getReservationId() {
        return reservationId;
    }

    public void setReservationId(long reservationId) {
        this.reservationId = reservationId;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "BUILDING", nullable = false),
            @JoinColumn(name = "FLOOR", nullable = false),
            @JoinColumn(name = "ROOM", nullable = false)
    }
    )
    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLIENT")
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "ARRIVAL_DATE", nullable = false)
    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DEPARTURE_DATE", nullable = false)
    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "reservation", cascade = CascadeType.ALL)
    public Set<UsedService> getUsedServices() {
        return usedServices;
    }

    public void setUsedServices(Set<UsedService> usedServices) {
        this.usedServices = usedServices;
    }
}
