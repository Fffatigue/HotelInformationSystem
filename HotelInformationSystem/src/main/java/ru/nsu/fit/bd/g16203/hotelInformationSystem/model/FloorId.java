package ru.nsu.fit.bd.g16203.hotelInformationSystem.model;

import java.io.Serializable;

public class FloorId implements Serializable {
    private int buildingId;
    private int floorNum;

    public FloorId() {
    }

    public FloorId(int buildingId, int floorNum) {
        this.buildingId = buildingId;
        this.floorNum = floorNum;
    }

    public int getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }

    public int getFloorNum() {
        return floorNum;
    }

    public void setFloorNum(int floorNum) {
        this.floorNum = floorNum;
    }
}
