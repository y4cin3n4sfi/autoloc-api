package tn.esprit.autoloc.web.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.service.VehiculeService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/vehicule")
public class VehiculeRestController {

    private final VehiculeService vehiculeService;

    // http://localhost:8089/autoloc/vehicule/retrieve-all-vehicules
    @GetMapping("/retrieve-all-vehicules")
    public List<Vehicule> getVehicules() {
        return vehiculeService.retrieveAllVehicules();
    }

    // http://localhost:8089/autoloc/vehicule/retrieve-vehicule/{vehicule-id}
    @GetMapping("/retrieve-vehicule/{vehicule-id}")
    public Vehicule retrieveVehicule(@PathVariable("vehicule-id") Long vehiculeId) {
        return vehiculeService.retrieveVehicule(vehiculeId);
    }

    // http://localhost:8089/autoloc/vehicule/add-vehicule
    @PostMapping("/add-vehicule")
    public Vehicule addVehicule(@RequestBody Vehicule vehicule) {
        return vehiculeService.addVehicule(vehicule);
    }

    // http://localhost:8089/autoloc/vehicule/modify-vehicule
    @PutMapping("/modify-vehicule")
    public Vehicule modifyVehicule(@RequestBody Vehicule vehicule) {
        return vehiculeService.modifyVehicule(vehicule);
    }

    // http://localhost:8089/autoloc/vehicule/remove-vehicule/{vehicule-id}
    @DeleteMapping("/remove-vehicule/{vehicule-id}")
    public void removeVehicule(@PathVariable("vehicule-id") Long vehiculeId) {
        vehiculeService.removeVehicule(vehiculeId);
    }

    // http://localhost:8089/autoloc/vehicule/retrieve-vehicule-by-immat?immatriculation=123-TN-456
    @GetMapping("/retrieve-vehicule-by-immat")
    public Vehicule retrieveVehiculeByImmat(@RequestParam String immatriculation) {
        return vehiculeService.getVehiculeByImmatriculation(immatriculation);
    }

    // http://localhost:8089/autoloc/vehicule/retrieve-vehicules-by-agence/{agence-id}
    @GetMapping("/retrieve-vehicules-by-agence/{agence-id}")
    public List<Vehicule> retrieveVehiculesByAgence(@PathVariable("agence-id") Long agenceId) {
        return vehiculeService.getVehiculesByAgenceId(agenceId);
    }
}
