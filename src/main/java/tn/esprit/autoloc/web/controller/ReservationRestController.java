package tn.esprit.autoloc.web.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.service.ReservationService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/reservation")
public class ReservationRestController {

    private final ReservationService reservationService;

    // http://localhost:8089/autoloc/reservation/retrieve-all-reservations
    @GetMapping("/retrieve-all-reservations")
    public List<Reservation> getReservations() {
        return reservationService.retrieveAllReservations();
    }

    // http://localhost:8089/autoloc/reservation/retrieve-reservation/{reservation-id}
    @GetMapping("/retrieve-reservation/{reservation-id}")
    public Reservation retrieveReservation(@PathVariable("reservation-id") Long reservationId) {
        return reservationService.retrieveReservation(reservationId);
    }

    // http://localhost:8089/autoloc/reservation/add-reservation
    @PostMapping("/add-reservation")
    public Reservation addReservation(@RequestBody Reservation reservation) {
        return reservationService.addReservation(reservation);
    }

    // http://localhost:8089/autoloc/reservation/modify-reservation
    @PutMapping("/modify-reservation")
    public Reservation modifyReservation(@RequestBody Reservation reservation) {
        return reservationService.modifyReservation(reservation);
    }

    // http://localhost:8089/autoloc/reservation/remove-reservation/{reservation-id}
    @DeleteMapping("/remove-reservation/{reservation-id}")
    public void removeReservation(@PathVariable("reservation-id") Long reservationId) {
        reservationService.removeReservation(reservationId);
    }

    // http://localhost:8089/autoloc/reservation/retrieve-reservations-by-client/{client-id}
    @GetMapping("/retrieve-reservations-by-client/{client-id}")
    public List<Reservation> retrieveReservationsByClient(@PathVariable("client-id") Long clientId) {
        return reservationService.getReservationsByClientId(clientId);
    }

    // http://localhost:8089/autoloc/reservation/retrieve-reservations-by-vehicule/{vehicule-id}
    @GetMapping("/retrieve-reservations-by-vehicule/{vehicule-id}")
    public List<Reservation> retrieveReservationsByVehicule(@PathVariable("vehicule-id") Long vehiculeId) {
        return reservationService.getReservationsByVehiculeId(vehiculeId);
    }
}
