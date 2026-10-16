package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Maintenance;

import java.util.List;

public interface MaintenanceService {

    Maintenance saveMaintenance(Maintenance maintenance);

    Maintenance updateMaintenance(Long idMaintenance, Maintenance maintenance);

    Maintenance getMaintenanceById(Long idMaintenance);

    List<Maintenance> getAllMaintenances();

    void deleteMaintenance(Long idMaintenance);

    List<Maintenance> getMaintenancesByVehiculeId(Long idVehicule);
}
