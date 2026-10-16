package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Agence;

import java.util.List;

public interface AgenceService {

    Agence saveAgence(Agence agence);

    Agence updateAgence(Long idAgence, Agence agence);

    Agence getAgenceById(Long idAgence);

    List<Agence> getAllAgences();

    void deleteAgence(Long idAgence);
}
