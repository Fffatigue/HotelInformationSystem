package ru.nsu.fit.bd.g16203.hotelInformationSystem.model;

public class Organization implements Entity<Integer> {
    private Integer clientId;
    private String name;
    private int discount;

    public int getDiscount() {
        return discount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    @Override
    public Integer getPK() {
        return clientId;
    }

    @Override
    public void setPK(Integer primaryKey) {
        clientId = primaryKey;
    }
}
