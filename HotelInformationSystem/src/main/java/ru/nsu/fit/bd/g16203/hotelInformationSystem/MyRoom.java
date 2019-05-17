package ru.nsu.fit.bd.g16203.hotelInformationSystem;

import java.io.Serializable;

public class MyRoom implements Serializable {
    int roomNum = 5;
    int floorNum = 10;
    String buildingName = "eee";

    public int getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(int roomNum) {
        this.roomNum = roomNum;
    }

    public int getFloorNum() {
        return floorNum;
    }

    public void setFloorNum(int floorNum) {
        this.floorNum = floorNum;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }
}
