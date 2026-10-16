package tn.esprit.autoloc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.ReservationRepository;
import tn.esprit.autoloc.service.ReservationService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    @Override
    @Transactional
    public Reservation saveReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    @Transactional
    public Reservation updateReservation(Long idReservation, Reservation reservation) {
        Reservation existingReservation = getReservationById(idReservation);
        existingReservation.setDateDebut(reservation.getDateDebut());
        existingReservation.setDateFin(reservation.getDateFin());
        existingReservation.setStatut(reservation.getStatut());
        existingReservation.setVehicule(reservation.getVehicule());
        existingReservation.setClient(reservation.getClient());
        return reservationRepository.save(existingReservation);
    }

    @Override
    public Reservation getReservationById(Long idReservation) {
        return reservationRepository.findById(idReservation)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with id: " + idReservation));
    }

    @Override
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteReservation(Long idReservation) {
        if (!reservationRepository.existsById(idReservation)) {
            throw new ResourceNotFoundException("Reservation not found with id: " + idReservation);
        }
        reservationRepository.deleteById(idReservation);
    }

    @Override
    public List<Reservation> getReservationsByClientId(Long idClient) {
        return reservationRepository.findByClientIdClient(idClient);
    }

    @Override
    public List<Reservation> getReservationsByVehiculeId(Long idVehicule) {
        return reservationRepository.findByVehiculeIdVehicule(idVehicule);
    }

    // ESPRIT convention implementations
    @Override
    @Transactional
    public Reservation addReservation(Reservation reservation) {
        return saveReservation(reservation);
    }

    @Override
    public Reservation retrieveReservation(Long idReservation) {
        return getReservationById(idReservation);
    }

    @Override
    public List<Reservation> retrieveAllReservations() {
        return getAllReservations();
    }

    @Override
    @Transactional
    public Reservation modifyReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    @Transactional
    public void removeReservation(Long idReservation) {
        deleteReservation(idReservation);
    }
}
