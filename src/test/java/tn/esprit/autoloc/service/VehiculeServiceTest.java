package tn.esprit.autoloc.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.repository.AgenceRepository;
import tn.esprit.autoloc.repository.EquipementRepository;
import tn.esprit.autoloc.repository.VehiculeRepository;
import tn.esprit.autoloc.service.impl.VehiculeServiceImpl;
import tn.esprit.autoloc.validator.VehiculeValidator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehiculeServiceTest {

    @Mock
    private VehiculeRepository vehiculeRepository;

    @Mock
    private AgenceRepository agenceRepository;

    @Mock
    private EquipementRepository equipementRepository;

    @Mock
    private VehiculeValidator vehiculeValidator;

    @Mock
    private JournalisationService journalisationService;

    @Mock
    private TarificationService tarificationService;

    @InjectMocks
    private VehiculeServiceImpl vehiculeService;

    private Vehicule vehicule;
    private Agence agence;
    private Equipement equipement;

    @BeforeEach
    void setUp() {
        vehicule = new Vehicule();
        vehicule.setIdVehicule(1L);
        vehicule.setImmatriculation("234-TN-5678");
        vehicule.setTarifJournalier(BigDecimal.valueOf(100.0));
        vehicule.setEquipements(new ArrayList<>());

        agence = new Agence();
        agence.setIdAgence(10L);
        agence.setNom("Agence Tunis");

        equipement = new Equipement();
        equipement.setIdEquipement(20L);
        equipement.setLibelle("Siège bébé");
    }

    @Test
    @DisplayName("SRP: ajouter véhicule avec validation et journalisation")
    void testAjouterVehicule() {
        when(vehiculeRepository.save(any(Vehicule.class))).thenReturn(vehicule);

        Vehicule result = vehiculeService.ajouter(vehicule);

        assertNotNull(result);
        verify(vehiculeValidator).valider(vehicule);
        verify(journalisationService).log(contains("Ajout"));
        verify(vehiculeRepository).save(vehicule);
    }

    @Test
    @DisplayName("Atelier 2 - Scénario 2: Affecter un véhicule à une agence")
    void testAffecterVehicule() {
        when(vehiculeRepository.findById(1L)).thenReturn(Optional.of(vehicule));
        when(agenceRepository.findById(10L)).thenReturn(Optional.of(agence));
        when(vehiculeRepository.save(any(Vehicule.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Vehicule result = vehiculeService.affecterVehicule(1L, 10L);

        assertNotNull(result);
        assertEquals(agence, result.getAgence());
        verify(vehiculeRepository).save(vehicule);
    }

    @Test
    @DisplayName("Atelier 2 - Scénario 3: Affecter un équipement (siège bébé)")
    void testAffecterEquipement() {
        when(vehiculeRepository.findById(1L)).thenReturn(Optional.of(vehicule));
        when(equipementRepository.findById(20L)).thenReturn(Optional.of(equipement));
        when(vehiculeRepository.save(any(Vehicule.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Vehicule result = vehiculeService.affecterEquipement(1L, 20L);

        assertNotNull(result);
        assertTrue(result.getEquipements().contains(equipement));
        verify(vehiculeRepository).save(vehicule);
    }

    @Test
    @DisplayName("Atelier 2 - Scénario 3: Désaffecter un équipement (au retour)")
    void testDesaffecterEquipement() {
        vehicule.getEquipements().add(equipement);
        when(vehiculeRepository.findById(1L)).thenReturn(Optional.of(vehicule));
        when(equipementRepository.findById(20L)).thenReturn(Optional.of(equipement));
        when(vehiculeRepository.save(any(Vehicule.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Vehicule result = vehiculeService.desaffecterEquipement(1L, 20L);

        assertNotNull(result);
        assertFalse(result.getEquipements().contains(equipement));
        verify(vehiculeRepository).save(vehicule);
    }
}
