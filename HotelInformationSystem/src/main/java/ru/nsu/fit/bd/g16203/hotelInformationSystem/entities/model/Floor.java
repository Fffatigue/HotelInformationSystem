package ru.nsu.fit.bd.g16203.hotelInformationSystem.entities.model;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@IdClass( FloorId.class )
@Table(name = "floor")
public class Floor implements Serializable {

    private Building building;
    private Long floorNum;
    private Set<Room> rooms = new HashSet<Room>();

    public Floor(){}

    public Floor(Building building, Long floorNum) {
        this.building = building;
        this.floorNum = floorNum;
    }

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BUILDING", nullable = false)
    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    @Id
    @Column(name = "FLOOR_NUM", nullable = false)
    public Long getFloorNum() {
        return floorNum;
    }

    public void setFloorNum(Long floorNum) {
        this.floorNum = floorNum;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "floor", cascade = CascadeType.ALL)
    public Set<Room> getRooms() {
        return rooms;
    }

    public void setRooms(Set<Room> rooms) {
        this.rooms = rooms;
    }
}
