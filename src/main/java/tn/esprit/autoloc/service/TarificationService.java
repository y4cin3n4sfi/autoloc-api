package tn.esprit.autoloc.service;

import java.util.List;

public interface TarificationService {

    double calculerPrix(String typeStrategie, double tarifJournalier, int jours);

    List<String> getAvailableStrategies();
}
