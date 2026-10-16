package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Contrat;

import java.util.List;

public interface ContratService {

    Contrat saveContrat(Contrat contrat);

    Contrat updateContrat(Long idContrat, Contrat contrat);

    Contrat getContratById(Long idContrat);

    List<Contrat> getAllContrats();

    void deleteContrat(Long idContrat);

    Contrat getContratByReservationId(Long idReservation);

    // ESPRIT convention methods
    Contrat addContrat(Contrat contrat);

    Contrat retrieveContrat(Long idContrat);

    List<Contrat> retrieveAllContrats();

    Contrat modifyContrat(Contrat contrat);

    void removeContrat(Long idContrat);
}
