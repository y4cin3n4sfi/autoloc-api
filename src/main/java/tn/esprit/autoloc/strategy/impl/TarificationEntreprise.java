package tn.esprit.autoloc.strategy.impl;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.strategy.StrategieTarification;

@Component("entreprise")
public class TarificationEntreprise implements StrategieTarification {

    @Override
    public double calculer(double tarifJournalier, int jours) {
        return tarifJournalier * jours * 0.85; // Réduction de 15%
    }
}
