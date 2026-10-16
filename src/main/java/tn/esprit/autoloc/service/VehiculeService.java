package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Vehicule;

import java.util.List;

public interface VehiculeService {

    Vehicule saveVehicule(Vehicule vehicule);

    Vehicule updateVehicule(Long idVehicule, Vehicule vehicule);

    Vehicule getVehiculeById(Long idVehicule);

    List<Vehicule> getAllVehicules();

    void deleteVehicule(Long idVehicule);

    Vehicule getVehiculeByImmatriculation(String immatriculation);

    List<Vehicule> getVehiculesByAgenceId(Long idAgence);
}
