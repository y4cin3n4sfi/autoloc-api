package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Employe;

import java.util.List;

public interface EmployeService {

    Employe saveEmploye(Employe employe);

    Employe updateEmploye(Long idEmploye, Employe employe);

    Employe getEmployeById(Long idEmploye);

    List<Employe> getAllEmployes();

    void deleteEmploye(Long idEmploye);

    List<Employe> getEmployesByAgenceId(Long idAgence);
}
