package tn.esprit.autoloc.web.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.service.EmployeService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/employe")
public class EmployeRestController {

    private final EmployeService employeService;

    // http://localhost:8089/autoloc/employe/retrieve-all-employes
    @GetMapping("/retrieve-all-employes")
    public List<Employe> getEmployes() {
        return employeService.retrieveAllEmployes();
    }

    // http://localhost:8089/autoloc/employe/retrieve-employe/{employe-id}
    @GetMapping("/retrieve-employe/{employe-id}")
    public Employe retrieveEmploye(@PathVariable("employe-id") Long employeId) {
        return employeService.retrieveEmploye(employeId);
    }

    // http://localhost:8089/autoloc/employe/add-employe
    @PostMapping("/add-employe")
    public Employe addEmploye(@RequestBody Employe employe) {
        return employeService.addEmploye(employe);
    }

    // http://localhost:8089/autoloc/employe/modify-employe
    @PutMapping("/modify-employe")
    public Employe modifyEmploye(@RequestBody Employe employe) {
        return employeService.modifyEmploye(employe);
    }

    // http://localhost:8089/autoloc/employe/remove-employe/{employe-id}
    @DeleteMapping("/remove-employe/{employe-id}")
    public void removeEmploye(@PathVariable("employe-id") Long employeId) {
        employeService.removeEmploye(employeId);
    }

    // http://localhost:8089/autoloc/employe/retrieve-employes-by-agence/{agence-id}
    @GetMapping("/retrieve-employes-by-agence/{agence-id}")
    public List<Employe> retrieveEmployesByAgence(@PathVariable("agence-id") Long agenceId) {
        return employeService.getEmployesByAgenceId(agenceId);
    }
}
