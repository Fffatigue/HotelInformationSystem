package ru.nsu.fit.bd.g16203.hotelInformationSystem.model;

public interface Entity<PK> {
    public PK getPK();
    public void setPK(PK primaryKey);
}
