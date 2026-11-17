package tn.esprit.autoloc.validator;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Vehicule;

import java.math.BigDecimal;

@Component
public class VehiculeValidator {

    public void valider(Vehicule vehicule) {
        if (vehicule == null) {
            throw new IllegalArgumentException("Le véhicule ne peut pas être nul");
        }
        if (vehicule.getTarifJournalier() == null || vehicule.getTarifJournalier().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Le tarif doit être positif");
        }
    }
}
