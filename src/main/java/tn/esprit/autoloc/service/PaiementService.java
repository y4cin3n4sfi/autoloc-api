package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Paiement;

import java.util.List;

public interface PaiementService {

    Paiement savePaiement(Paiement paiement);

    Paiement updatePaiement(Long idPaiement, Paiement paiement);

    Paiement getPaiementById(Long idPaiement);

    List<Paiement> getAllPaiements();

    void deletePaiement(Long idPaiement);

    List<Paiement> getPaiementsByContratId(Long idContrat);

    // ESPRIT convention methods
    Paiement addPaiement(Paiement paiement);

    Paiement retrievePaiement(Long idPaiement);

    List<Paiement> retrieveAllPaiements();

    Paiement modifyPaiement(Paiement paiement);

    void removePaiement(Long idPaiement);
}
