package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.service.MaintenanceService;

import java.util.List;

@Tag(name = "Maintenances", description = "Gestion des entretiens et réparations de véhicules")
@RestController
@AllArgsConstructor
@RequestMapping("/maintenance")
public class MaintenanceRestController {

    private final MaintenanceService maintenanceService;

    // http://localhost:8089/autoloc/maintenance/retrieve-all-maintenances
    @Operation(summary = "Récupérer la liste de toutes les maintenances")
    @GetMapping("/retrieve-all-maintenances")
    public List<Maintenance> getMaintenances() {
        return maintenanceService.retrieveAllMaintenances();
    }

    // http://localhost:8089/autoloc/maintenance/retrieve-maintenance/{maintenance-id}
    @Operation(summary = "Récupérer une fiche de maintenance par son identifiant")
    @GetMapping("/retrieve-maintenance/{maintenance-id}")
    public Maintenance retrieveMaintenance(@PathVariable("maintenance-id") Long maintenanceId) {
        return maintenanceService.retrieveMaintenance(maintenanceId);
    }

    // http://localhost:8089/autoloc/maintenance/add-maintenance
    @Operation(summary = "Ajouter une nouvelle opération de maintenance")
    @PostMapping("/add-maintenance")
    public Maintenance addMaintenance(@RequestBody Maintenance maintenance) {
        return maintenanceService.addMaintenance(maintenance);
    }

    // http://localhost:8089/autoloc/maintenance/modify-maintenance
    @Operation(summary = "Modifier une opération de maintenance existante")
    @PutMapping("/modify-maintenance")
    public Maintenance modifyMaintenance(@RequestBody Maintenance maintenance) {
        return maintenanceService.modifyMaintenance(maintenance);
    }

    // http://localhost:8089/autoloc/maintenance/remove-maintenance/{maintenance-id}
    @Operation(summary = "Supprimer une fiche de maintenance par son identifiant")
    @DeleteMapping("/remove-maintenance/{maintenance-id}")
    public void removeMaintenance(@PathVariable("maintenance-id") Long maintenanceId) {
        maintenanceService.removeMaintenance(maintenanceId);
    }

    // http://localhost:8089/autoloc/maintenance/retrieve-maintenances-by-vehicule/{vehicule-id}
    @Operation(summary = "Récupérer l'historique des maintenances d'un véhicule")
    @GetMapping("/retrieve-maintenances-by-vehicule/{vehicule-id}")
    public List<Maintenance> retrieveMaintenancesByVehicule(@PathVariable("vehicule-id") Long vehiculeId) {
        return maintenanceService.getMaintenancesByVehiculeId(vehiculeId);
    }
}
