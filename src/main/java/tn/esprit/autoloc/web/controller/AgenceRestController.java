package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.service.AgenceService;

import java.util.List;

@Tag(name = "Agences", description = "Gestion des agences de location")
@RestController
@AllArgsConstructor
@RequestMapping("/agence")
public class AgenceRestController {

    private final AgenceService agenceService;

    // http://localhost:8089/autoloc/agence/retrieve-all-agences
    @Operation(summary = "Récupérer la liste de toutes les agences")
    @GetMapping("/retrieve-all-agences")
    public List<Agence> getAgences() {
        return agenceService.retrieveAllAgences();
    }

    // http://localhost:8089/autoloc/agence/retrieve-agence/{agence-id}
    @Operation(summary = "Récupérer une agence par son identifiant")
    @GetMapping("/retrieve-agence/{agence-id}")
    public Agence retrieveAgence(@PathVariable("agence-id") Long agenceId) {
        return agenceService.retrieveAgence(agenceId);
    }

    // http://localhost:8089/autoloc/agence/add-agence
    @Operation(summary = "Ajouter une nouvelle agence")
    @PostMapping("/add-agence")
    public Agence addAgence(@RequestBody Agence agence) {
        return agenceService.addAgence(agence);
    }

    // http://localhost:8089/autoloc/agence/modify-agence
    @Operation(summary = "Modifier les informations d'une agence existante")
    @PutMapping("/modify-agence")
    public Agence modifyAgence(@RequestBody Agence agence) {
        return agenceService.modifyAgence(agence);
    }

    // http://localhost:8089/autoloc/agence/remove-agence/{agence-id}
    @Operation(summary = "Supprimer une agence par son identifiant")
    @DeleteMapping("/remove-agence/{agence-id}")
    public void removeAgence(@PathVariable("agence-id") Long agenceId) {
        agenceService.removeAgence(agenceId);
    }
}
