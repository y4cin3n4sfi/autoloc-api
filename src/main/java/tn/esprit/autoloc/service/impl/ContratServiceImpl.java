package tn.esprit.autoloc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.ContratRepository;
import tn.esprit.autoloc.service.ContratService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContratServiceImpl implements ContratService {

    private final ContratRepository contratRepository;

    @Override
    @Transactional
    public Contrat saveContrat(Contrat contrat) {
        return contratRepository.save(contrat);
    }

    @Override
    @Transactional
    public Contrat updateContrat(Long idContrat, Contrat contrat) {
        Contrat existingContrat = getContratById(idContrat);
        existingContrat.setDateSignature(contrat.getDateSignature());
        existingContrat.setMontantTotal(contrat.getMontantTotal());
        existingContrat.setValide(contrat.getValide());
        existingContrat.setReservation(contrat.getReservation());
        return contratRepository.save(existingContrat);
    }

    @Override
    public Contrat getContratById(Long idContrat) {
        return contratRepository.findById(idContrat)
                .orElseThrow(() -> new ResourceNotFoundException("Contrat not found with id: " + idContrat));
    }

    @Override
    public List<Contrat> getAllContrats() {
        return contratRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteContrat(Long idContrat) {
        if (!contratRepository.existsById(idContrat)) {
            throw new ResourceNotFoundException("Contrat not found with id: " + idContrat);
        }
        contratRepository.deleteById(idContrat);
    }

    @Override
    public Contrat getContratByReservationId(Long idReservation) {
        return contratRepository.findByReservationIdReservation(idReservation)
                .orElseThrow(() -> new ResourceNotFoundException("Contrat not found for reservation id: " + idReservation));
    }
}
