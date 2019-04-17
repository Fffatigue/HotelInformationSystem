package ru.nsu.fit.bd.g16203.hotelInformationSystem.entities.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "building")

public class Building implements Serializable {

    private Long buildingId;
    private Long name;
    private Set<Floor> floors = new HashSet<>();

    public Building(){

    }

    public Building(Long name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "BUILDING_ID", unique = true, nullable = false)
    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    @Column(name = "NAME", unique = true, nullable = false)
    public Long getName() {
        return name;
    }

    public void setName(Long name) {
        this.name = name;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "building")
    public Set<Floor> getFloors() {
        return floors;
    }

    public void setFloors(Set<Floor> floors) {
        this.floors = floors;
    }
}
