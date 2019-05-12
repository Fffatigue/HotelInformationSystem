package ru.nsu.fit.bd.g16203.hotelInformationSystem.model;

public class BuildingService implements Entity<BuildingServiceId> {
    BuildingServiceId buildingServiceId;

    public BuildingService() {
    }

    public BuildingService(BuildingServiceId buildingServiceId) {
        this.buildingServiceId = buildingServiceId;
    }


    @Override
    public BuildingServiceId getPK() {
        return buildingServiceId;
    }

    @Override
    public void setPK(BuildingServiceId primaryKey) {
        this.buildingServiceId = primaryKey;
    }
}
