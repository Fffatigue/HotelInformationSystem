package ru.nsu.fit.bd.g16203.hotelInformationSystem.model;

public class BuildingServiceId {
    private int buildingId;
    private int serviceId;

    public BuildingServiceId() {
    }

    public BuildingServiceId(int buildingId, int serviceId) {
        this.buildingId = buildingId;
        this.serviceId = serviceId;
    }

    public int getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }
}
