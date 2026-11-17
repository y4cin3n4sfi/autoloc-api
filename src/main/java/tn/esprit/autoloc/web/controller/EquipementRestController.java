package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.service.EquipementService;

import java.util.List;

@Tag(name = "Équipements", description = "Gestion des équipements pour véhicules")
@RestController
@AllArgsConstructor
@RequestMapping("/equipement")
public class EquipementRestController {

    private final EquipementService equipementService;

    // http://localhost:8089/autoloc/equipement/retrieve-all-equipements
    @Operation(summary = "Récupérer la liste de tous les équipements")
    @GetMapping("/retrieve-all-equipements")
    public List<Equipement> getEquipements() {
        return equipementService.retrieveAllEquipements();
    }

    // http://localhost:8089/autoloc/equipement/retrieve-equipement/{equipement-id}
    @Operation(summary = "Récupérer un équipement par son identifiant")
    @GetMapping("/retrieve-equipement/{equipement-id}")
    public Equipement retrieveEquipement(@PathVariable("equipement-id") Long equipementId) {
        return equipementService.retrieveEquipement(equipementId);
    }

    // http://localhost:8089/autoloc/equipement/add-equipement
    @Operation(summary = "Ajouter un nouvel équipement")
    @PostMapping("/add-equipement")
    public Equipement addEquipement(@RequestBody Equipement equipement) {
        return equipementService.addEquipement(equipement);
    }

    // http://localhost:8089/autoloc/equipement/modify-equipement
    @Operation(summary = "Modifier un équipement existant")
    @PutMapping("/modify-equipement")
    public Equipement modifyEquipement(@RequestBody Equipement equipement) {
        return equipementService.modifyEquipement(equipement);
    }

    // http://localhost:8089/autoloc/equipement/remove-equipement/{equipement-id}
    @Operation(summary = "Supprimer un équipement par son identifiant")
    @DeleteMapping("/remove-equipement/{equipement-id}")
    public void removeEquipement(@PathVariable("equipement-id") Long equipementId) {
        equipementService.removeEquipement(equipementId);
    }
}
