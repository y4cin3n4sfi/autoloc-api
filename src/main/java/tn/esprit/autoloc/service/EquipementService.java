package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Equipement;

import java.util.List;

public interface EquipementService {

    Equipement saveEquipement(Equipement equipement);

    Equipement updateEquipement(Long idEquipement, Equipement equipement);

    Equipement getEquipementById(Long idEquipement);

    List<Equipement> getAllEquipements();

    void deleteEquipement(Long idEquipement);

    // ESPRIT convention methods
    Equipement addEquipement(Equipement equipement);

    Equipement retrieveEquipement(Long idEquipement);

    List<Equipement> retrieveAllEquipements();

    Equipement modifyEquipement(Equipement equipement);

    void removeEquipement(Long idEquipement);
}
