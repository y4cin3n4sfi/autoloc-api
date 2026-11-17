package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.service.PaiementService;

import java.util.List;

@Tag(name = "Paiements", description = "Gestion des transactions et paiements")
@RestController
@AllArgsConstructor
@RequestMapping("/paiement")
public class PaiementRestController {

    private final PaiementService paiementService;

    // http://localhost:8089/autoloc/paiement/retrieve-all-paiements
    @Operation(summary = "Récupérer la liste de tous les paiements")
    @GetMapping("/retrieve-all-paiements")
    public List<Paiement> getPaiements() {
        return paiementService.retrieveAllPaiements();
    }

    // http://localhost:8089/autoloc/paiement/retrieve-paiement/{paiement-id}
    @Operation(summary = "Récupérer un paiement par son identifiant")
    @GetMapping("/retrieve-paiement/{paiement-id}")
    public Paiement retrievePaiement(@PathVariable("paiement-id") Long paiementId) {
        return paiementService.retrievePaiement(paiementId);
    }

    // http://localhost:8089/autoloc/paiement/add-paiement
    @Operation(summary = "Enregistrer un nouveau paiement")
    @PostMapping("/add-paiement")
    public Paiement addPaiement(@RequestBody Paiement paiement) {
        return paiementService.addPaiement(paiement);
    }

    // http://localhost:8089/autoloc/paiement/modify-paiement
    @Operation(summary = "Modifier les informations d'un paiement existant")
    @PutMapping("/modify-paiement")
    public Paiement modifyPaiement(@RequestBody Paiement paiement) {
        return paiementService.modifyPaiement(paiement);
    }

    // http://localhost:8089/autoloc/paiement/remove-paiement/{paiement-id}
    @Operation(summary = "Supprimer un paiement par son identifiant")
    @DeleteMapping("/remove-paiement/{paiement-id}")
    public void removePaiement(@PathVariable("paiement-id") Long paiementId) {
        paiementService.removePaiement(paiementId);
    }

    // http://localhost:8089/autoloc/paiement/retrieve-paiements-by-contrat/{contrat-id}
    @Operation(summary = "Récupérer les paiements associés à un contrat")
    @GetMapping("/retrieve-paiements-by-contrat/{contrat-id}")
    public List<Paiement> retrievePaiementsByContrat(@PathVariable("contrat-id") Long contratId) {
        return paiementService.getPaiementsByContratId(contratId);
    }
}
