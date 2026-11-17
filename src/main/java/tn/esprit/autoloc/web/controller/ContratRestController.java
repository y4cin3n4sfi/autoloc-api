package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.service.ContratService;

import java.util.List;

@Tag(name = "Contrats", description = "Gestion des contrats de location")
@RestController
@AllArgsConstructor
@RequestMapping("/contrat")
public class ContratRestController {

    private final ContratService contratService;

    // http://localhost:8089/autoloc/contrat/retrieve-all-contrats
    @Operation(summary = "Récupérer la liste de tous les contrats")
    @GetMapping("/retrieve-all-contrats")
    public List<Contrat> getContrats() {
        return contratService.retrieveAllContrats();
    }

    // http://localhost:8089/autoloc/contrat/retrieve-contrat/{contrat-id}
    @Operation(summary = "Récupérer un contrat par son identifiant")
    @GetMapping("/retrieve-contrat/{contrat-id}")
    public Contrat retrieveContrat(@PathVariable("contrat-id") Long contratId) {
        return contratService.retrieveContrat(contratId);
    }

    // http://localhost:8089/autoloc/contrat/add-contrat
    @Operation(summary = "Ajouter un nouveau contrat")
    @PostMapping("/add-contrat")
    public Contrat addContrat(@RequestBody Contrat contrat) {
        return contratService.addContrat(contrat);
    }

    // http://localhost:8089/autoloc/contrat/modify-contrat
    @Operation(summary = "Modifier un contrat existant")
    @PutMapping("/modify-contrat")
    public Contrat modifyContrat(@RequestBody Contrat contrat) {
        return contratService.modifyContrat(contrat);
    }

    // http://localhost:8089/autoloc/contrat/remove-contrat/{contrat-id}
    @Operation(summary = "Supprimer un contrat par son identifiant")
    @DeleteMapping("/remove-contrat/{contrat-id}")
    public void removeContrat(@PathVariable("contrat-id") Long contratId) {
        contratService.removeContrat(contratId);
    }

    // http://localhost:8089/autoloc/contrat/retrieve-contrat-by-reservation/{reservation-id}
    @Operation(summary = "Récupérer un contrat associé à une réservation")
    @GetMapping("/retrieve-contrat-by-reservation/{reservation-id}")
    public Contrat retrieveContratByReservation(@PathVariable("reservation-id") Long reservationId) {
        return contratService.getContratByReservationId(reservationId);
    }
}
