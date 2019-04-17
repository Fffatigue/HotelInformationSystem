package ru.nsu.fit.bd.g16203.hotelInformationSystem.entities.model;

import javax.persistence.*;

import java.io.Serializable;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "room")
public class Room implements Serializable {

    private Long roomId;
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
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ROOM_ID", unique = true, nullable = false)
    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FLOOR", nullable = false)
    public Floor getFloor() {
        return floor;
    }

    public void setFloor(Floor floor) {
        this.floor = floor;
    }

    public void setFloorId(Floor floor) {
        this.floor = floor;
    }

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

}
