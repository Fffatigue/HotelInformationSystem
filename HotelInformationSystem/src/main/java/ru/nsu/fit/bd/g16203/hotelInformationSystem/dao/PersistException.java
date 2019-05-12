package ru.nsu.fit.bd.g16203.hotelInformationSystem.dao;

public class PersistException extends Exception {
    public PersistException(Exception e) {
        super( e );
    }

    public PersistException(String errorMsg) {
        super( errorMsg );
    }
}
