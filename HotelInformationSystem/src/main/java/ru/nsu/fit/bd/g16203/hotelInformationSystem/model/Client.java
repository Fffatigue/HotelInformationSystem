package ru.nsu.fit.bd.g16203.hotelInformationSystem.model;

public class Client implements Entity<Integer> {
    private Integer clientId;
    private String name;

    public Client() {
    }

    public Client(String name) {
        this.name = name;
    }

    public Client(Integer clientId, String name) {
        this.clientId = clientId;
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
        return clientId;
    }

    @Override
    public void setPK(Integer primaryKey) {
        this.clientId = primaryKey;
    }
}
