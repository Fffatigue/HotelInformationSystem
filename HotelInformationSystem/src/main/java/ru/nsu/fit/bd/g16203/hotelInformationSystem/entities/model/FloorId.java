package ru.nsu.fit.bd.g16203.hotelInformationSystem.entities.model;

import java.io.Serializable;
import java.util.Objects;

public class FloorId implements Serializable {
    private Building building;
    private Long floorNum;

    public FloorId() {
    }

    public FloorId(Building building, Long floorNum) {
        this.building = building;
        this.floorNum = floorNum;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public Long getFloorNum() {
        return floorNum;
    }

    public void setFloorNum(Long floorNum) {
        this.floorNum = floorNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FloorId floorId = (FloorId) o;
        if(!floorId.building.equals(this.building)){
            return false;
        }
        return floorId.floorNum.equals( floorNum );
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((building == null) ? 0 : building.hashCode());
        result = (int) (prime * result + floorNum);
        return result;
    }
}
