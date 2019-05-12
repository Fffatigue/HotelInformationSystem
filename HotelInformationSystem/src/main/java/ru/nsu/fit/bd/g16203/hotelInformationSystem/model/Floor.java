package ru.nsu.fit.bd.g16203.hotelInformationSystem.model;

public class Floor implements Entity<FloorId> {

    private FloorId floorId;

    public Floor() {
    }

    public Floor(FloorId floorId) {
        this.floorId = floorId;
    }

    @Override
    public FloorId getPK() {
        return floorId;
    }

    @Override
    public void setPK(FloorId primaryKey) {
        floorId = primaryKey;
    }
}
