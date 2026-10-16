package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Equipement;

import java.util.List;

public interface EquipementService {

    Equipement saveEquipement(Equipement equipement);

    Equipement updateEquipement(Long idEquipement, Equipement equipement);

    Equipement getEquipementById(Long idEquipement);

    List<Equipement> getAllEquipements();

    void deleteEquipement(Long idEquipement);
}
