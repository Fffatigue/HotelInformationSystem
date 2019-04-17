package ru.nsu.fit.bd.g16203.hotelInformationSystem.entities.model;

import java.io.Serializable;
import java.util.Objects;

public class BuildingServiceId implements Serializable {
    private Building building;
    private Service service;

    public BuildingServiceId() {
    }

    public BuildingServiceId(Building building, Service service) {
        this.building = building;
        this.service = service;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BuildingServiceId that = (BuildingServiceId) o;
        return Objects.equals( building, that.building ) &&
                Objects.equals( service, that.service );
    }

    @Override
    public int hashCode() {
        return Objects.hash( building, service );
    }
}
