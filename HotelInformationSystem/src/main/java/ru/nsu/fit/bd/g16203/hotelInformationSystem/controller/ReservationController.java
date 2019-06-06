package ru.nsu.fit.bd.g16203.hotelInformationSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.PersistException;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.dao.WrongDataException;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Reservation;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.model.Service;
import ru.nsu.fit.bd.g16203.hotelInformationSystem.service.IReservationService;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    @Autowired
    private IReservationService reservationService;

    @RequestMapping("/id/{reservationId}")
    public Reservation getReservation(@PathVariable int reservationId) throws PersistException {
        return reservationService.getByPK(reservationId);
    }

    @DeleteMapping("/id/{reservationId}")
    public void deleteReservation(@PathVariable int reservationId) throws PersistException, WrongDataException {
        reservationService.delete(reservationId);
    }

    @PutMapping("/id/{reservationId}")
    public void updateReservation(@PathVariable int reservationId, @RequestBody Reservation reservation) throws PersistException, SQLException, WrongDataException {
        reservation.setPK( reservationId );
        reservationService.update( reservation );
    }

    @PostMapping("/id/{reservationId}/services/{serviceId}")
    public void updateBuildingServices(@PathVariable int reservationId, @PathVariable int serviceId) throws PersistException, SQLException, WrongDataException {
        reservationService.insertAvailableService( reservationId, serviceId );
    }

    @DeleteMapping("/id/{reservationId}/services/{serviceId}")
    public void deleteBuildingServices(@PathVariable int reservationId, @PathVariable int serviceId) throws PersistException, SQLException, WrongDataException {
        reservationService.deleteAvailableService( reservationId, serviceId );
    }

    @GetMapping("/id/{reservationId}/services")
    public List<Service> deleteBuildingServices(@PathVariable int reservationId) throws PersistException, SQLException, WrongDataException {
        return reservationService.getAvailableServices( reservationId );
    }

    @PostMapping
    public Reservation createReservation(@RequestBody Reservation reservation) throws PersistException, SQLException, WrongDataException {
        reservationService.create( reservation );
        return reservation;
    }

    @RequestMapping("/page/{page}")
    public List<Reservation> getReservations(@PathVariable int page) throws PersistException {
        return reservationService.getAll(page);
    }

    @GetMapping("/page")
    public int getPageNum() throws SQLException {
        return reservationService.getPageNum();
    }
}
