package tn.esprit.autoloc.strategy.impl;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.strategy.StrategieTarification;

@Component("longueDuree")
public class TarificationLongueDuree implements StrategieTarification {

    @Override
    public double calculer(double tarifJournalier, int jours) {
        return tarifJournalier * jours * 0.80; // Réduction de 20%
    }
}
