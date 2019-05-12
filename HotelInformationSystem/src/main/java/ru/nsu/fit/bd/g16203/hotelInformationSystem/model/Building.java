package ru.nsu.fit.bd.g16203.hotelInformationSystem.model;

public class Building implements Entity<Integer> {
    private Integer buildingId;
    private String name;

    public Building() {
    }

    public Building(String name) {
        this.name = name;
    }

    public Building(Integer buildingId, String name) {
        this.buildingId = buildingId;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Integer getPK() {
        return buildingId;
    }

    @Override
    public void setPK(Integer primaryKey) {
        this.buildingId = primaryKey;
    }
}
