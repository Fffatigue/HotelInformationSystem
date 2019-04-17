package ru.nsu.fit.bd.g16203.hotelInformationSystem.entities.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "building")

public class Building implements Serializable {

    private String name;
    private Set<Floor> floors = new HashSet<>();
    private Set<BuildingService> buildingServices = new HashSet<>(  );

    public Building() {

    }

    public Building(String name) {
        this.name = name;
    }

    @Id
    @Column(name = "NAME", unique = true, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "building", cascade = CascadeType.ALL)
    public Set<Floor> getFloors() {
        return floors;
    }

    public void setFloors(Set<Floor> floors) {
        this.floors = floors;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "building", cascade = CascadeType.ALL)
    public Set<BuildingService> getBuildingServices() {
        return buildingServices;
    }

    public void setBuildingServices(Set<BuildingService> buildingServices) {
        this.buildingServices = buildingServices;
    }
}
