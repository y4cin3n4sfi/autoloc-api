package tn.esprit.autoloc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.MaintenanceRepository;
import tn.esprit.autoloc.service.MaintenanceService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MaintenanceServiceImpl implements MaintenanceService {

    private final MaintenanceRepository maintenanceRepository;

    @Override
    @Transactional
    public Maintenance saveMaintenance(Maintenance maintenance) {
        return maintenanceRepository.save(maintenance);
    }

    @Override
    @Transactional
    public Maintenance updateMaintenance(Long idMaintenance, Maintenance maintenance) {
        Maintenance existingMaintenance = getMaintenanceById(idMaintenance);
        existingMaintenance.setDateDebut(maintenance.getDateDebut());
        existingMaintenance.setDateFin(maintenance.getDateFin());
        existingMaintenance.setDescription(maintenance.getDescription());
        existingMaintenance.setVehicule(maintenance.getVehicule());
        return maintenanceRepository.save(existingMaintenance);
    }

    @Override
    public Maintenance getMaintenanceById(Long idMaintenance) {
        return maintenanceRepository.findById(idMaintenance)
                .orElseThrow(() -> new ResourceNotFoundException("Maintenance not found with id: " + idMaintenance));
    }

    @Override
    public List<Maintenance> getAllMaintenances() {
        return maintenanceRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteMaintenance(Long idMaintenance) {
        if (!maintenanceRepository.existsById(idMaintenance)) {
            throw new ResourceNotFoundException("Maintenance not found with id: " + idMaintenance);
        }
        maintenanceRepository.deleteById(idMaintenance);
    }

    @Override
    public List<Maintenance> getMaintenancesByVehiculeId(Long idVehicule) {
        return maintenanceRepository.findByVehiculeIdVehicule(idVehicule);
    }
}
