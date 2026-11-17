package tn.esprit.autoloc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.AgenceRepository;
import tn.esprit.autoloc.repository.EquipementRepository;
import tn.esprit.autoloc.repository.VehiculeRepository;
import tn.esprit.autoloc.service.JournalisationService;
import tn.esprit.autoloc.service.TarificationService;
import tn.esprit.autoloc.service.VehiculeService;
import tn.esprit.autoloc.validator.VehiculeValidator;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VehiculeServiceImpl implements VehiculeService {

    private final VehiculeRepository vehiculeRepository;
    private final AgenceRepository agenceRepository;
    private final EquipementRepository equipementRepository;
    private final VehiculeValidator vehiculeValidator;
    private final JournalisationService journalisationService;
    private final TarificationService tarificationService;

    @Override
    @Transactional
    public Vehicule saveVehicule(Vehicule vehicule) {
        vehiculeValidator.valider(vehicule);
        journalisationService.log("Ajout du véhicule : " + vehicule.getImmatriculation());
        return vehiculeRepository.save(vehicule);
    }

    @Override
    @Transactional
    public Vehicule updateVehicule(Long idVehicule, Vehicule vehicule) {
        vehiculeValidator.valider(vehicule);
        Vehicule existingVehicule = getVehiculeById(idVehicule);
        existingVehicule.setImmatriculation(vehicule.getImmatriculation());
        existingVehicule.setMarque(vehicule.getMarque());
        existingVehicule.setModele(vehicule.getModele());
        existingVehicule.setCategorie(vehicule.getCategorie());
        existingVehicule.setTarifJournalier(vehicule.getTarifJournalier());
        existingVehicule.setStatut(vehicule.getStatut());
        existingVehicule.setAgence(vehicule.getAgence());
        existingVehicule.setEquipements(vehicule.getEquipements());
        return vehiculeRepository.save(existingVehicule);
    }

    @Override
    public Vehicule getVehiculeById(Long idVehicule) {
        return vehiculeRepository.findById(idVehicule)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule not found with id: " + idVehicule));
    }

    @Override
    public List<Vehicule> getAllVehicules() {
        return vehiculeRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteVehicule(Long idVehicule) {
        if (!vehiculeRepository.existsById(idVehicule)) {
            throw new ResourceNotFoundException("Vehicule not found with id: " + idVehicule);
        }
        vehiculeRepository.deleteById(idVehicule);
    }

    @Override
    public Vehicule getVehiculeByImmatriculation(String immatriculation) {
        return vehiculeRepository.findByImmatriculation(immatriculation)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule not found with immatriculation: " + immatriculation));
    }

    @Override
    public List<Vehicule> getVehiculesByAgenceId(Long idAgence) {
        return vehiculeRepository.findByAgenceIdAgence(idAgence);
    }

    // ESPRIT convention implementations
    @Override
    @Transactional
    public Vehicule addVehicule(Vehicule vehicule) {
        vehicule.setIdVehicule(null);
        return saveVehicule(vehicule);
    }

    @Override
    public Vehicule retrieveVehicule(Long idVehicule) {
        return getVehiculeById(idVehicule);
    }

    @Override
    public List<Vehicule> retrieveAllVehicules() {
        return getAllVehicules();
    }

    @Override
    @Transactional
    public Vehicule modifyVehicule(Vehicule vehicule) {
        vehiculeValidator.valider(vehicule);
        return vehiculeRepository.save(vehicule);
    }

    @Override
    @Transactional
    public void removeVehicule(Long idVehicule) {
        deleteVehicule(idVehicule);
    }

    // Atelier SOLID / GoF implementations
    @Override
    @Transactional
    public Vehicule ajouter(Vehicule vehicule) {
        vehiculeValidator.valider(vehicule);
        journalisationService.log("Ajout du véhicule");
        vehicule.setIdVehicule(null);
        return vehiculeRepository.save(vehicule);
    }

    @Override
    public double calculerTarif(Vehicule vehicule, int nombreJours) {
        return calculerTarif(vehicule, nombreJours, "standard");
    }

    @Override
    public double calculerTarif(Vehicule vehicule, int nombreJours, String typeStrategie) {
        if (vehicule == null) {
            throw new IllegalArgumentException("Le véhicule ne peut pas être nul");
        }
        if (vehicule.getTarifJournalier() == null) {
            throw new IllegalArgumentException("Le tarif journalier du véhicule est requis");
        }
        return tarificationService.calculerPrix(typeStrategie, vehicule.getTarifJournalier().doubleValue(), nombreJours);
    }

    // Atelier Affectations / Désaffectations
    @Override
    @Transactional
    public Vehicule affecterVehicule(Long idVehicule, Long idAgence) {
        Vehicule vehicule = getVehiculeById(idVehicule);
        Agence agence = agenceRepository.findById(idAgence)
                .orElseThrow(() -> new ResourceNotFoundException("Agence not found with id: " + idAgence));
        vehicule.setAgence(agence);
        return vehiculeRepository.save(vehicule);
    }

    @Override
    @Transactional
    public Vehicule desaffecterVehicule(Long idVehicule) {
        Vehicule vehicule = getVehiculeById(idVehicule);
        vehicule.setAgence(null);
        return vehiculeRepository.save(vehicule);
    }

    @Override
    @Transactional
    public Vehicule affecterEquipement(Long idVehicule, Long idEquipement) {
        Vehicule vehicule = getVehiculeById(idVehicule);
        Equipement equipement = equipementRepository.findById(idEquipement)
                .orElseThrow(() -> new ResourceNotFoundException("Equipement not found with id: " + idEquipement));

        if (vehicule.getEquipements() == null) {
            vehicule.setEquipements(new ArrayList<>());
        }

        boolean alreadyPresent = vehicule.getEquipements().stream()
                .anyMatch(e -> e.getIdEquipement().equals(idEquipement));
        if (!alreadyPresent) {
            vehicule.getEquipements().add(equipement);
        }

        return vehiculeRepository.save(vehicule);
    }

    @Override
    @Transactional
    public Vehicule desaffecterEquipement(Long idVehicule, Long idEquipement) {
        Vehicule vehicule = getVehiculeById(idVehicule);
        Equipement equipement = equipementRepository.findById(idEquipement)
                .orElseThrow(() -> new ResourceNotFoundException("Equipement not found with id: " + idEquipement));

        if (vehicule.getEquipements() != null) {
            vehicule.getEquipements().removeIf(e -> e.getIdEquipement().equals(idEquipement));
        }

        return vehiculeRepository.save(vehicule);
    }
}
