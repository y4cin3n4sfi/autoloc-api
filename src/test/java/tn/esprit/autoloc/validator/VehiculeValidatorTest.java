package tn.esprit.autoloc.validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tn.esprit.autoloc.domain.Vehicule;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class VehiculeValidatorTest {

    private final VehiculeValidator validator = new VehiculeValidator();

    @Test
    @DisplayName("Véhicule valide avec tarif positif")
    void testValidationVehiculeValide() {
        Vehicule vehicule = new Vehicule();
        vehicule.setTarifJournalier(BigDecimal.valueOf(80.0));

        assertDoesNotThrow(() -> validator.valider(vehicule));
    }

    @Test
    @DisplayName("Exception si le véhicule est null")
    void testValidationVehiculeNull() {
        assertThrows(IllegalArgumentException.class, () -> validator.valider(null));
    }

    @Test
    @DisplayName("Exception si le tarif est zéro ou négatif")
    void testValidationTarifNegatif() {
        Vehicule vehicule = new Vehicule();
        vehicule.setTarifJournalier(BigDecimal.valueOf(-10.0));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> validator.valider(vehicule));
        assertEquals("Le tarif doit être positif", ex.getMessage());
    }
}
