package ru.nsu.fit.bd.g16203.hotelInformationSystem.entities.model;

import javax.persistence.*;

@Entity
@IdClass( BuildingServiceId.class )
@Table(name = "building_service")
public class BuildingService {
    private Building building;
    private Service service;

    public BuildingService() {
    }

    public BuildingService(Building building, Service service) {
        this.building = building;
        this.service = service;
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SERVICE", nullable = false)
    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
}
