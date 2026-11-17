package tn.esprit.autoloc.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tn.esprit.autoloc.service.impl.TarificationServiceImpl;
import tn.esprit.autoloc.strategy.StrategieTarification;
import tn.esprit.autoloc.strategy.impl.TarificationEntreprise;
import tn.esprit.autoloc.strategy.impl.TarificationLongueDuree;
import tn.esprit.autoloc.strategy.impl.TarificationStandard;
import tn.esprit.autoloc.strategy.impl.TarificationWeekend;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TarificationServiceTest {

    private TarificationService tarificationService;

    @BeforeEach
    void setUp() {
        Map<String, StrategieTarification> strategies = new HashMap<>();
        strategies.put("standard", new TarificationStandard());
        strategies.put("weekend", new TarificationWeekend());
        strategies.put("longueDuree", new TarificationLongueDuree());
        strategies.put("entreprise", new TarificationEntreprise());

        tarificationService = new TarificationServiceImpl(strategies);
    }

    @Test
    @DisplayName("Tarif Standard: tarifJournalier * jours")
    void testCalculTarifStandard() {
        double prix = tarificationService.calculerPrix("standard", 100.0, 5);
        assertEquals(500.0, prix, 0.001);
    }

    @Test
    @DisplayName("Tarif Week-end: réduction de 10%")
    void testCalculTarifWeekend() {
        double prix = tarificationService.calculerPrix("weekend", 100.0, 2);
        assertEquals(180.0, prix, 0.001); // 100 * 2 * 0.9 = 180
    }

    @Test
    @DisplayName("Tarif Longue Durée: réduction de 20%")
    void testCalculTarifLongueDuree() {
        double prix = tarificationService.calculerPrix("longueDuree", 100.0, 10);
        assertEquals(800.0, prix, 0.001); // 100 * 10 * 0.8 = 800
    }

    @Test
    @DisplayName("Tarif Entreprise: réduction de 15% (OCP)")
    void testCalculTarifEntreprise() {
        double prix = tarificationService.calculerPrix("entreprise", 100.0, 10);
        assertEquals(850.0, prix, 0.001); // 100 * 10 * 0.85 = 850
    }

    @Test
    @DisplayName("Exception si le type de tarif est inconnu")
    void testStrategieInconnue() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                tarificationService.calculerPrix("inconnu", 100.0, 3)
        );
        assertTrue(exception.getMessage().contains("inconnu"));
    }

    @Test
    @DisplayName("Exception si nombre de jours <= 0")
    void testJoursInvalides() {
        assertThrows(IllegalArgumentException.class, () ->
                tarificationService.calculerPrix("standard", 100.0, 0)
        );
    }

    @Test
    @DisplayName("Exception si tarif <= 0")
    void testTarifInvalide() {
        assertThrows(IllegalArgumentException.class, () ->
                tarificationService.calculerPrix("standard", 0.0, 5)
        );
    }
}
