package ru.nsu.fit.bd.g16203.hotelInformationSystem.model;

public class Room implements Entity<RoomId> {
    private RoomId roomId;
    private int capacity;
    private int price;

    public Room() {
    }

    public Room(RoomId roomId, int capacity, int price) {
        this.roomId = roomId;
        this.capacity = capacity;
        this.price = price;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public RoomId getPK() {
        return roomId;
    }

    @Override
    public void setPK(RoomId primaryKey) {
        roomId = primaryKey;
    }
}
