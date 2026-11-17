package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.service.TarificationService;

import java.util.List;

@Tag(name = "Tarification", description = "Calcul tarifaire selon les politiques de prix (Pattern GoF Strategy)")
@RestController
@RequiredArgsConstructor
@RequestMapping("/tarification")
public class TarificationRestController {

    private final TarificationService tarificationService;

    // http://localhost:8089/autoloc/tarification/calculer?type=standard&tarifJournalier=100&jours=5
    @Operation(summary = "Calculer le prix selon une politique tarifaire (standard, weekend, longueDuree, entreprise)")
    @GetMapping("/calculer")
    public double calculer(@RequestParam String type,
                           @RequestParam double tarifJournalier,
                           @RequestParam int jours) {
        return tarificationService.calculerPrix(type, tarifJournalier, jours);
    }

    // http://localhost:8089/autoloc/tarification/strategies
    @Operation(summary = "Lister les politiques tarifaires disponibles")
    @GetMapping("/strategies")
    public List<String> getStrategies() {
        return tarificationService.getAvailableStrategies();
    }
}
