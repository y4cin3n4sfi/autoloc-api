package tn.esprit.autoloc.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.repository.AgenceRepository;
import tn.esprit.autoloc.repository.EmployeRepository;
import tn.esprit.autoloc.service.impl.EmployeServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeServiceTest {

    @Mock
    private EmployeRepository employeRepository;

    @Mock
    private AgenceRepository agenceRepository;

    @InjectMocks
    private EmployeServiceImpl employeService;

    private Employe employe;
    private Agence agenceTunis;

    @BeforeEach
    void setUp() {
        employe = new Employe();
        employe.setIdEmploye(1L);
        employe.setNom("Ben Salah");
        employe.setPrenom("Ahmed");

        agenceTunis = new Agence();
        agenceTunis.setIdAgence(5L);
        agenceTunis.setNom("Agence Tunis");
    }

    @Test
    @DisplayName("Atelier 2 - Scénario 1: Désaffecter un employé")
    void testDesaffecterEmploye() {
        employe.setAgence(agenceTunis);
        when(employeRepository.findById(1L)).thenReturn(Optional.of(employe));
        when(employeRepository.save(any(Employe.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Employe result = employeService.desaffecterEmploye(1L);

        assertNotNull(result);
        assertNull(result.getAgence());
        verify(employeRepository).save(employe);
    }

    @Test
    @DisplayName("Atelier 2 - Scénario 1: Affecter un employé à une nouvelle agence")
    void testAffecterEmploye() {
        when(employeRepository.findById(1L)).thenReturn(Optional.of(employe));
        when(agenceRepository.findById(5L)).thenReturn(Optional.of(agenceTunis));
        when(employeRepository.save(any(Employe.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Employe result = employeService.affecterEmploye(1L, 5L);

        assertNotNull(result);
        assertEquals(agenceTunis, result.getAgence());
        verify(employeRepository).save(employe);
    }
}
