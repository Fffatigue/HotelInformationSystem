package ru.nsu.fit.bd.g16203.hotelInformationSystem.entities.model;

import java.io.Serializable;

public class RoomId implements Serializable {
    private Floor floor;
    private Long roomNum;

    public RoomId() {
    }

    public RoomId(Floor floor, Long roomNum) {
        this.floor = floor;
        this.roomNum = roomNum;
    }

    public Floor getfloor() {
        return floor;
    }

    public void setfloor(Floor floor) {
        this.floor = floor;
    }

    public Long getroomNum() {
        return roomNum;
    }

    public void setroomNum(Long roomNum) {
        this.roomNum = roomNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomId roomId = (RoomId) o;
        if(!roomId.floor.equals(this.floor)){
            return false;
        }
        return roomId.roomNum.equals( roomNum );
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((floor == null) ? 0 : floor.hashCode());
        result = (int) (prime * result + roomNum);
        return result;
    }
}
