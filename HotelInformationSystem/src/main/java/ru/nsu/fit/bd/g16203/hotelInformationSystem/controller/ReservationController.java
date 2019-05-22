package ru.nsu.fit.bd.g16203.hotelInformationSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.PersistException;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Reservation;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.service.IReservationService;

@RestController
@RequestMapping("reservation{reservationId}")
public class ReservationController {
    @Autowired
    private IReservationService reservationService;

    @RequestMapping
    public Reservation getReservation(@PathVariable int reservationId) throws PersistException {
        return reservationService.getByPK(reservationId);
    }

    @DeleteMapping
    public void deleteReservation(@PathVariable int reservationId) throws PersistException {
        reservationService.delete(reservationId);
    }

    @PutMapping
    public void updateReservation(@PathVariable int reservationId, @RequestBody Reservation reservation) throws PersistException {
        reservation.setPK( reservationId );
        reservationService.update( reservation );
    }

    @PostMapping
    public Reservation createReservation(@PathVariable int reservationId, @RequestBody Reservation reservation) throws PersistException {
        reservation.setPK( reservationId );
        reservation = reservationService.create( reservation );
        return reservation;
    }

    //TODO сделоть
}
