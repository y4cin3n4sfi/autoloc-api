package tn.esprit.autoloc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.EquipementRepository;
import tn.esprit.autoloc.service.EquipementService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EquipementServiceImpl implements EquipementService {

    private final EquipementRepository equipementRepository;

    @Override
    @Transactional
    public Equipement saveEquipement(Equipement equipement) {
        return equipementRepository.save(equipement);
    }

    @Override
    @Transactional
    public Equipement updateEquipement(Long idEquipement, Equipement equipement) {
        Equipement existingEquipement = getEquipementById(idEquipement);
        existingEquipement.setLibelle(equipement.getLibelle());
        return equipementRepository.save(existingEquipement);
    }

    @Override
    public Equipement getEquipementById(Long idEquipement) {
        return equipementRepository.findById(idEquipement)
                .orElseThrow(() -> new ResourceNotFoundException("Equipement not found with id: " + idEquipement));
    }

    @Override
    public List<Equipement> getAllEquipements() {
        return equipementRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteEquipement(Long idEquipement) {
        if (!equipementRepository.existsById(idEquipement)) {
            throw new ResourceNotFoundException("Equipement not found with id: " + idEquipement);
        }
        equipementRepository.deleteById(idEquipement);
    }
}
