package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Reservation;

import java.util.List;

public interface ReservationService {

    Reservation saveReservation(Reservation reservation);

    Reservation updateReservation(Long idReservation, Reservation reservation);

    Reservation getReservationById(Long idReservation);

    List<Reservation> getAllReservations();

    void deleteReservation(Long idReservation);

    List<Reservation> getReservationsByClientId(Long idClient);

    List<Reservation> getReservationsByVehiculeId(Long idVehicule);
}
