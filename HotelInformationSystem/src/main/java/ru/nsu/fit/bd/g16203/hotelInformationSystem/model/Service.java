package ru.nsu.fit.bd.g16203.hotelInformationSystem.model;

public class Service implements Entity<Integer>{

    private String name;
    private Integer serviceId;
    private int price;

    public Service() {
    }

    public Service(String name, Integer serviceId, int price) {
        this.name = name;
        this.serviceId = serviceId;
        this.price = price;
    }

    public Service(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public Integer getPK() {
        return serviceId;
    }

    @Override
    public void setPK(Integer primaryKey) {
        serviceId = primaryKey;
    }
}
