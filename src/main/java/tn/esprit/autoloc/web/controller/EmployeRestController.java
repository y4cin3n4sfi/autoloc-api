package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.service.EmployeService;

import java.util.List;

@Tag(name = "Employés", description = "Gestion du personnel et des employés")
@RestController
@AllArgsConstructor
@RequestMapping("/employe")
public class EmployeRestController {

    private final EmployeService employeService;

    // http://localhost:8089/autoloc/employe/retrieve-all-employes
    @Operation(summary = "Récupérer la liste de tous les employés")
    @GetMapping("/retrieve-all-employes")
    public List<Employe> getEmployes() {
        return employeService.retrieveAllEmployes();
    }

    // http://localhost:8089/autoloc/employe/retrieve-employe/{employe-id}
    @Operation(summary = "Récupérer un employé par son identifiant")
    @GetMapping("/retrieve-employe/{employe-id}")
    public Employe retrieveEmploye(@PathVariable("employe-id") Long employeId) {
        return employeService.retrieveEmploye(employeId);
    }

    // http://localhost:8089/autoloc/employe/add-employe
    @Operation(summary = "Ajouter un nouvel employé")
    @PostMapping("/add-employe")
    public Employe addEmploye(@RequestBody Employe employe) {
        return employeService.addEmploye(employe);
    }

    // http://localhost:8089/autoloc/employe/modify-employe
    @Operation(summary = "Modifier un employé existant")
    @PutMapping("/modify-employe")
    public Employe modifyEmploye(@RequestBody Employe employe) {
        return employeService.modifyEmploye(employe);
    }

    // http://localhost:8089/autoloc/employe/remove-employe/{employe-id}
    @Operation(summary = "Supprimer un employé par son identifiant")
    @DeleteMapping("/remove-employe/{employe-id}")
    public void removeEmploye(@PathVariable("employe-id") Long employeId) {
        employeService.removeEmploye(employeId);
    }

    // http://localhost:8089/autoloc/employe/retrieve-employes-by-agence/{agence-id}
    @Operation(summary = "Récupérer les employés rattachés à une agence")
    @GetMapping("/retrieve-employes-by-agence/{agence-id}")
    public List<Employe> retrieveEmployesByAgence(@PathVariable("agence-id") Long agenceId) {
        return employeService.getEmployesByAgenceId(agenceId);
    }

    // http://localhost:8089/autoloc/employe/desaffecter-employe/{idEmploye}
    @Operation(summary = "Désaffecter un employé de son agence actuelle")
    @PutMapping("/desaffecter-employe/{idEmploye}")
    public Employe desaffecterEmploye(@PathVariable("idEmploye") Long idEmploye) {
        return employeService.desaffecterEmploye(idEmploye);
    }

    // http://localhost:8089/autoloc/employe/affecter-employe/{idEmploye}/{idAgence}
    @Operation(summary = "Affecter un employé à une agence")
    @PutMapping("/affecter-employe/{idEmploye}/{idAgence}")
    public Employe affecterEmploye(@PathVariable("idEmploye") Long idEmploye,
                                  @PathVariable("idAgence") Long idAgence) {
        return employeService.affecterEmploye(idEmploye, idAgence);
    }
}

