package ru.nsu.fit.bd.g16203.hotelInformationSystem.entities.model;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@IdClass( RoomId.class )
@Table(name = "room")
public class Room implements Serializable {
    private Set<Review> reviews = new HashSet<>(  );
    private Set<Reservation> reservations = new HashSet<>(  );
    private Floor floor;
    private Long roomNum;
    private Long capacity;
    private Long price;

    public Room(){}

    public Room(Floor floor, Long roomNum, Long capacity, Long price) {
        this.floor = floor;
        this.roomNum = roomNum;
        this.capacity = capacity;
        this.price = price;
    }

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "BUILDING", nullable = false),
            @JoinColumn(name = "FLOOR", nullable = false)
    }
    )
    public Floor getFloor() {
        return floor;
    }

    public void setFloor(Floor floor) {
        this.floor = floor;
    }

    public void setFloorId(Floor floor) {
        this.floor = floor;
    }

    @Id
    @Column(name = "ROOM_NUM", nullable = false)
    public Long getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(Long roomNum) {
        this.roomNum = roomNum;
    }


    @Column(name = "CAPACITY", nullable = false)
    public Long getCapacity() {
        return capacity;
    }

    public void setCapacity(Long capacity) {
        this.capacity = capacity;
    }

    @Column(name = "PRICE", nullable = false)
    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "room", cascade = CascadeType.ALL)
    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "room", cascade = CascadeType.ALL)
    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }

}
