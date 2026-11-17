package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.service.VehiculeService;

import java.util.List;

@Tag(name = "Véhicules", description = "Gestion du parc de véhicules")
@RestController
@AllArgsConstructor
@RequestMapping("/vehicule")
public class VehiculeRestController {

    private final VehiculeService vehiculeService;

    // http://localhost:8089/autoloc/vehicule/retrieve-all-vehicules
    @Operation(summary = "Récupérer la liste de tous les véhicules")
    @GetMapping("/retrieve-all-vehicules")
    public List<Vehicule> getVehicules() {
        return vehiculeService.retrieveAllVehicules();
    }

    // http://localhost:8089/autoloc/vehicule/retrieve-vehicule/{vehicule-id}
    @Operation(summary = "Récupérer un véhicule par son identifiant")
    @GetMapping("/retrieve-vehicule/{vehicule-id}")
    public Vehicule retrieveVehicule(@PathVariable("vehicule-id") Long vehiculeId) {
        return vehiculeService.retrieveVehicule(vehiculeId);
    }

    // http://localhost:8089/autoloc/vehicule/add-vehicule
    @Operation(summary = "Ajouter un nouveau véhicule")
    @PostMapping("/add-vehicule")
    public Vehicule addVehicule(@RequestBody Vehicule vehicule) {
        return vehiculeService.addVehicule(vehicule);
    }

    // http://localhost:8089/autoloc/vehicule/modify-vehicule
    @Operation(summary = "Modifier les informations d'un véhicule existant")
    @PutMapping("/modify-vehicule")
    public Vehicule modifyVehicule(@RequestBody Vehicule vehicule) {
        return vehiculeService.modifyVehicule(vehicule);
    }

    // http://localhost:8089/autoloc/vehicule/remove-vehicule/{vehicule-id}
    @Operation(summary = "Supprimer un véhicule par son identifiant")
    @DeleteMapping("/remove-vehicule/{vehicule-id}")
    public void removeVehicule(@PathVariable("vehicule-id") Long vehiculeId) {
        vehiculeService.removeVehicule(vehiculeId);
    }

    // http://localhost:8089/autoloc/vehicule/retrieve-vehicule-by-immat?immatriculation=123-TN-456
    @Operation(summary = "Rechercher un véhicule par sa plaque d'immatriculation")
    @GetMapping("/retrieve-vehicule-by-immat")
    public Vehicule retrieveVehiculeByImmat(@RequestParam String immatriculation) {
        return vehiculeService.getVehiculeByImmatriculation(immatriculation);
    }

    // http://localhost:8089/autoloc/vehicule/retrieve-vehicules-by-agence/{agence-id}
    @Operation(summary = "Récupérer la liste des véhicules associés à une agence")
    @GetMapping("/retrieve-vehicules-by-agence/{agence-id}")
    public List<Vehicule> retrieveVehiculesByAgence(@PathVariable("agence-id") Long agenceId) {
        return vehiculeService.getVehiculesByAgenceId(agenceId);
    }

    // http://localhost:8089/autoloc/vehicule/ajouter
    @Operation(summary = "Ajouter un véhicule (refactoring SRP avec validation et journalisation)")
    @PostMapping("/ajouter")
    public Vehicule ajouter(@RequestBody Vehicule vehicule) {
        return vehiculeService.ajouter(vehicule);
    }

    // http://localhost:8089/autoloc/vehicule/calculer-tarif/{vehicule-id}?jours=3&strategie=standard
    @Operation(summary = "Calculer le tarif d'un véhicule selon une stratégie (standard, weekend, longueDuree, entreprise)")
    @GetMapping("/calculer-tarif/{vehicule-id}")
    public double calculerTarif(@PathVariable("vehicule-id") Long vehiculeId,
                                @RequestParam int jours,
                                @RequestParam(defaultValue = "standard") String strategie) {
        Vehicule vehicule = vehiculeService.getVehiculeById(vehiculeId);
        return vehiculeService.calculerTarif(vehicule, jours, strategie);
    }

    // http://localhost:8089/autoloc/vehicule/affecter-vehicule/{idVehicule}/{idAgence}
    @Operation(summary = "Affecter un véhicule à une agence")
    @PutMapping("/affecter-vehicule/{idVehicule}/{idAgence}")
    public Vehicule affecterVehicule(@PathVariable("idVehicule") Long idVehicule,
                                    @PathVariable("idAgence") Long idAgence) {
        return vehiculeService.affecterVehicule(idVehicule, idAgence);
    }

    // http://localhost:8089/autoloc/vehicule/desaffecter-vehicule/{idVehicule}
    @Operation(summary = "Désaffecter un véhicule de son agence")
    @PutMapping("/desaffecter-vehicule/{idVehicule}")
    public Vehicule desaffecterVehicule(@PathVariable("idVehicule") Long idVehicule) {
        return vehiculeService.desaffecterVehicule(idVehicule);
    }

    // http://localhost:8089/autoloc/vehicule/affecter-equipement/{idVehicule}/{idEquipement}
    @Operation(summary = "Affecter un équipement à un véhicule")
    @PutMapping("/affecter-equipement/{idVehicule}/{idEquipement}")
    public Vehicule affecterEquipement(@PathVariable("idVehicule") Long idVehicule,
                                       @PathVariable("idEquipement") Long idEquipement) {
        return vehiculeService.affecterEquipement(idVehicule, idEquipement);
    }

    // http://localhost:8089/autoloc/vehicule/desaffecter-equipement/{idVehicule}/{idEquipement}
    @Operation(summary = "Désaffecter un équipement d'un véhicule")
    @PutMapping("/desaffecter-equipement/{idVehicule}/{idEquipement}")
    public Vehicule desaffecterEquipement(@PathVariable("idVehicule") Long idVehicule,
                                          @PathVariable("idEquipement") Long idEquipement) {
        return vehiculeService.desaffecterEquipement(idVehicule, idEquipement);
    }
}

