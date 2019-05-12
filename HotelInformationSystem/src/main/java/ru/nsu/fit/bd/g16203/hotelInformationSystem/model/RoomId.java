package ru.nsu.fit.bd.g16203.hotelInformationSystem.model;

import java.io.Serializable;

public class RoomId implements Serializable {
    private FloorId floorId;
    private int roomNum;

    public RoomId() {
    }

    public RoomId(FloorId floorId, int roomNum) {
        this.floorId = floorId;
        this.roomNum = roomNum;
    }

    public FloorId getFloorId() {
        return floorId;
    }

    public void setFloorId(FloorId floorId) {
        this.floorId = floorId;
    }

    public int getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(int roomNum) {
        this.roomNum = roomNum;
    }
}
