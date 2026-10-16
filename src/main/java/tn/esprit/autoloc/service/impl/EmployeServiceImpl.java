package tn.esprit.autoloc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.EmployeRepository;
import tn.esprit.autoloc.service.EmployeService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmployeServiceImpl implements EmployeService {

    private final EmployeRepository employeRepository;

    @Override
    @Transactional
    public Employe saveEmploye(Employe employe) {
        return employeRepository.save(employe);
    }

    @Override
    @Transactional
    public Employe updateEmploye(Long idEmploye, Employe employe) {
        Employe existingEmploye = getEmployeById(idEmploye);
        existingEmploye.setNom(employe.getNom());
        existingEmploye.setPrenom(employe.getPrenom());
        existingEmploye.setRole(employe.getRole());
        existingEmploye.setAgence(employe.getAgence());
        return employeRepository.save(existingEmploye);
    }

    @Override
    public Employe getEmployeById(Long idEmploye) {
        return employeRepository.findById(idEmploye)
                .orElseThrow(() -> new ResourceNotFoundException("Employe not found with id: " + idEmploye));
    }

    @Override
    public List<Employe> getAllEmployes() {
        return employeRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteEmploye(Long idEmploye) {
        if (!employeRepository.existsById(idEmploye)) {
            throw new ResourceNotFoundException("Employe not found with id: " + idEmploye);
        }
        employeRepository.deleteById(idEmploye);
    }

    @Override
    public List<Employe> getEmployesByAgenceId(Long idAgence) {
        return employeRepository.findByAgenceIdAgence(idAgence);
    }

    // ESPRIT convention implementations
    @Override
    @Transactional
    public Employe addEmploye(Employe employe) {
        return saveEmploye(employe);
    }

    @Override
    public Employe retrieveEmploye(Long idEmploye) {
        return getEmployeById(idEmploye);
    }

    @Override
    public List<Employe> retrieveAllEmployes() {
        return getAllEmployes();
    }

    @Override
    @Transactional
    public Employe modifyEmploye(Employe employe) {
        return employeRepository.save(employe);
    }

    @Override
    @Transactional
    public void removeEmploye(Long idEmploye) {
        deleteEmploye(idEmploye);
    }
}
