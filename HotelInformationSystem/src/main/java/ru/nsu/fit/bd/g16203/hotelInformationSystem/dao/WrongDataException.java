package ru.nsu.fit.bd.g16203.hotelInformationSystem.dao;

public class WrongDataException extends Exception{
    public WrongDataException(String msg){
        super(msg);
    }
}
