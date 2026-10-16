package tn.esprit.autoloc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.PaiementRepository;
import tn.esprit.autoloc.service.PaiementService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaiementServiceImpl implements PaiementService {

    private final PaiementRepository paiementRepository;

    @Override
    @Transactional
    public Paiement savePaiement(Paiement paiement) {
        return paiementRepository.save(paiement);
    }

    @Override
    @Transactional
    public Paiement updatePaiement(Long idPaiement, Paiement paiement) {
        Paiement existingPaiement = getPaiementById(idPaiement);
        existingPaiement.setMontant(paiement.getMontant());
        existingPaiement.setDatePaiement(paiement.getDatePaiement());
        existingPaiement.setModePaiement(paiement.getModePaiement());
        existingPaiement.setContrat(paiement.getContrat());
        return paiementRepository.save(existingPaiement);
    }

    @Override
    public Paiement getPaiementById(Long idPaiement) {
        return paiementRepository.findById(idPaiement)
                .orElseThrow(() -> new ResourceNotFoundException("Paiement not found with id: " + idPaiement));
    }

    @Override
    public List<Paiement> getAllPaiements() {
        return paiementRepository.findAll();
    }

    @Override
    @Transactional
    public void deletePaiement(Long idPaiement) {
        if (!paiementRepository.existsById(idPaiement)) {
            throw new ResourceNotFoundException("Paiement not found with id: " + idPaiement);
        }
        paiementRepository.deleteById(idPaiement);
    }

    @Override
    public List<Paiement> getPaiementsByContratId(Long idContrat) {
        return paiementRepository.findByContratIdContrat(idContrat);
    }

    // ESPRIT convention implementations
    @Override
    @Transactional
    public Paiement addPaiement(Paiement paiement) {
        return savePaiement(paiement);
    }

    @Override
    public Paiement retrievePaiement(Long idPaiement) {
        return getPaiementById(idPaiement);
    }

    @Override
    public List<Paiement> retrieveAllPaiements() {
        return getAllPaiements();
    }

    @Override
    @Transactional
    public Paiement modifyPaiement(Paiement paiement) {
        return paiementRepository.save(paiement);
    }

    @Override
    @Transactional
    public void removePaiement(Long idPaiement) {
        deletePaiement(idPaiement);
    }
}
