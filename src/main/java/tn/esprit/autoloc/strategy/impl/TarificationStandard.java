package tn.esprit.autoloc.strategy.impl;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.strategy.StrategieTarification;

@Component("standard")
public class TarificationStandard implements StrategieTarification {

    @Override
    public double calculer(double tarifJournalier, int jours) {
        return tarifJournalier * jours;
    }
}
