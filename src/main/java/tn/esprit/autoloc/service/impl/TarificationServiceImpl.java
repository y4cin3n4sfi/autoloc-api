package tn.esprit.autoloc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.service.TarificationService;
import tn.esprit.autoloc.strategy.StrategieTarification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TarificationServiceImpl implements TarificationService {

    private final Map<String, StrategieTarification> strategies;

    @Override
    public double calculerPrix(String typeStrategie, double tarifJournalier, int jours) {
        if (jours <= 0) {
            throw new IllegalArgumentException("Le nombre de jours doit être positif");
        }
        if (tarifJournalier <= 0) {
            throw new IllegalArgumentException("Le tarif journalier doit être positif");
        }
        if (typeStrategie == null || typeStrategie.isBlank()) {
            throw new IllegalArgumentException("Le type de tarification est obligatoire");
        }

        StrategieTarification strategie = resoudreStrategie(typeStrategie);
        if (strategie == null) {
            throw new IllegalArgumentException("Type de tarif inconnu : " + typeStrategie);
        }

        return Math.round(strategie.calculer(tarifJournalier, jours) * 100.0) / 100.0;
    }

    @Override
    public List<String> getAvailableStrategies() {
        return new ArrayList<>(strategies.keySet());
    }

    private StrategieTarification resoudreStrategie(String type) {
        if (strategies.containsKey(type)) {
            return strategies.get(type);
        }

        String normalized = type.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        for (Map.Entry<String, StrategieTarification> entry : strategies.entrySet()) {
            String candidateKey = entry.getKey().replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
            String candidateClass = entry.getValue().getClass().getSimpleName().replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
            if (candidateKey.equals(normalized) || candidateClass.equals(normalized)
                    || candidateKey.contains(normalized) || normalized.contains(candidateKey)) {
                return entry.getValue();
            }
        }
        return null;
    }
}
