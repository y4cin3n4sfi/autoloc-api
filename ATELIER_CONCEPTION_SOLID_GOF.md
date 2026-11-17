# Atelier AutoLoc : Amélioration de la conception avec GoF et SOLID

Ce document synthétise l'ensemble des réponses aux questions de conception ainsi que les choix d'implémentation réalisés dans le projet **AutoLoc API**.

---

## 1. Partie A — Identifier les problèmes de conception

### 1.1 Responsabilités prises en charge par la version initiale de `VehiculeService`
Dans le code initial, la classe `VehiculeService` accumulait quatre responsabilités distinctes :
1. **Validation métier des données** : vérification de la validité du tarif journalier (`tarifJournalier <= 0`).
2. **Journalisation / Tracing** : affichage direct dans la console système via `System.out.println`.
3. **Persistance des données** : persistance et sauvegarde en base de données via `repository.save()`.
4. **Calcul de tarification** : calcul du prix d'une location (`tarifJournalier * nombreJours`).

### 1.2 Pourquoi la validation, la journalisation et la gestion des véhicules doivent évoluer indépendamment ?
- **Règles de validation** : Les règles métier changent souvent (nouvelles règles sur les catégories de véhicules, validation d'immatriculation selon la norme nationale, contraintes sur les tarifs min/max).
- **Journalisation** : Les mécanismes de journalisation relèvent de l'infrastructure technique (passage de la console vers un framework comme Logback/SLF4J, écriture dans des fichiers rotatifs, envoi vers un cluster ELK ou Datadog, formatage JSON).
- **Gestion des véhicules** : Concerne le cycle de vie du domaine métier et les transactions de persistance.

Si ces trois aspects sont mélangés, modifier un niveau de log ou ajouter une règle de format d'immatriculation oblige à modifier et retester la classe centrale de gestion des véhicules, augmentant le risque de régression.

### 1.3 Quel principe SOLID pourrait améliorer cette conception ?
Le **Single Responsibility Principle (SRP)** : une classe ne doit avoir qu'une seule et unique responsabilité, c'est-à-dire une seule raison de changer.

### 1.4 Proposition d'une répartition plus claire des responsabilités
- `VehiculeValidator` : validation stricte des données et des contraintes d'intégrité métier d'un véhicule.
- `JournalisationService` : centralisation de l'enregistrement des événements et des traces applicatives.
- `VehiculeService` : orchestration des opérations métier sur les véhicules et coordination avec la persistance.
- `TarificationService` & `StrategieTarification` : calcul des tarifs selon les différentes politiques commerciales.

---

## 2. Partie B — Appliquer SRP et DIP

### B.1 Principe de responsabilité unique (SRP)
La méthode `ajouter(Vehicule vehicule)` a été refactorisée de sorte que :
- La validation est déléguée à `VehiculeValidator.valider(vehicule)`.
- La journalisation est déléguée à `JournalisationService.log(...)`.
- `VehiculeService` orchestre l'enregistrement et persiste l'entité via `VehiculeRepository`.

### B.2 Principe d'inversion des dépendances (DIP)
#### 1. Rôle de `VehiculeRepository` dans cette architecture
`VehiculeRepository` est une abstraction (interface héritant de `JpaRepository`). Elle définit le **contrat** de persistance sans imposer d'implémentation particulière.

#### 2. Pourquoi le service dépend-il d'un contrat plutôt que de créer lui-même son mécanisme de persistance ?
En dépendant d'une interface :
- Le service de haut niveau est découplé des détails techniques de bas niveau (JDBC, requêtes SQL directes, ORM Hibernate, type de base de données).
- Les tests unitaires peuvent être exécutés rapidement sans dépendance externe en utilisant des mocks (Mockito).
- Le mécanisme de stockage peut évoluer ou être remplacé sans toucher au code métier.

#### 3. Comment Spring injecte le repository dans le service ?
Spring génère dynamiquement un proxy implémentant `VehiculeRepository` lors du démarrage du contexte applicatif. Grâce à l'injection de dépendances par constructeur (`@RequiredArgsConstructor` de Lombok ou `@Autowired`), Spring détecte le composant requis et injecte l'instance du proxy gérée dans le conteneur IoC.

#### 4. Lien entre DIP et l'injection de dépendances (DI)
- **DIP (Dependency Inversion Principle)** est un **principe de conception théorique** stipulant que les modules de haut niveau ne doivent pas dépendre des modules de bas niveau, mais d'abstractions.
- **DI (Dependency Injection)** est le **mécanisme technique et architectural** permettant d'appliquer le DIP : les dépendances sont fournies de l'extérieur (par le conteneur Spring) plutôt qu'instanciées par la classe consommatrice (`new ...`).

---

## 3. Partie C — GoF Strategy et principe OCP

### 3.1 Architecture mise en place
1. **Interface d'abstraction** : `StrategieTarification` avec la méthode `double calculer(double tarifJournalier, int jours)`.
2. **Stratégies implémentées** :
   - `TarificationStandard` : `tarifJournalier * jours`
   - `TarificationWeekend` : 10% de réduction (`tarifJournalier * jours * 0.90`)
   - `TarificationLongueDuree` : 20% de réduction (`tarifJournalier * jours * 0.80`)
   - `TarificationEntreprise` : 15% de réduction (`tarifJournalier * jours * 0.85`)
3. **Moteur d'exécution** : `TarificationServiceImpl` qui s'appuie sur l'injection Spring `Map<String, StrategieTarification>` pour résoudre dynamiquement la stratégie demandée.

### 3.2 Réponses aux questions de la Partie C
#### 1. Quel problème le patron Strategy permet-il de résoudre ?
Il permet d'encapsuler des familles d'algorithmes interchangeables et d'éliminer les blocs conditionnels rigides (`if / else if` ou `switch`) disséminés dans le code pour choisir un algorithme.

#### 2. Pourquoi est-il préférable d'isoler chaque formule dans une classe différente ?
Chaque classe respecte le SRP : elle ne contient que la logique propre à sa règle de calcul. Modifier la formule du week-end n'entraîne aucun risque de détérioration de la tarification entreprise ou standard, et facilite l'écriture de tests unitaires ciblés.

#### 3. Comment cette conception applique-t-elle le principe OCP ?
Selon l'**Open/Closed Principle (OCP)**, le code doit être **ouvert à l'extension mais fermé à la modification**.
Pour introduire `TarificationEntreprise`, il a suffi de créer une nouvelle classe implémentant `StrategieTarification` annotée `@Component("entreprise")`. Le service `TarificationServiceImpl` n'a subi **aucune modification**.

#### 4. Que doit faire le service si le type de tarif demandé n'existe pas ?
Le service doit lever une exception explicite (ex : `IllegalArgumentException("Type de tarif inconnu : " + typeStrategie)`). Cela suit le principe "Fail-Fast" et empêche l'application de calculer un prix erroné ou d'avoir un comportement silencieusement imprévisible.

---

## 4. Partie D — Reconnaître Factory, ISP et LSP

### Situation 1 — Factory (Génération de factures PDF ou Excel)
- **Problème résolu par `FactureFactory`** : elle isole et masque la complexité d'instanciation des différents formats de documents (PDF, Excel) du code client qui demande une facture.
- **Méthode exposée** :
  ```java
  public interface FactureFactory {
      Facture creerFacture(TypeDocument format, DonneesFacturation donnees);
  }
  ```
- **Avantage de la centralisation** : évite la dispersion de `new FacturePdf()` ou `new FactureExcel()` dans toute l'application, centralise les configurations de rendu (librairies tierces, styles, polices), et facilite l'ajout ultérieur d'un format (ex: HTML, XML, Factur-X) sans toucher au métier.

### Situation 2 — ISP (Interface Segregation Principle)
- **Principe concerné** : **Interface Segregation Principle (ISP)** : un client ne doit pas être forcé de dépendre de méthodes qu'il n'utilise pas.
- **Proposition de découpage en contrats spécialisés** :
  ```java
  public interface VehiculeReaderService {
      Vehicule consulter(Long id);
  }

  public interface VehiculeWriterService {
      Vehicule ajouter(Vehicule vehicule);
      Vehicule modifier(Long id, Vehicule vehicule);
      void supprimer(Long id);
  }

  public interface VehiculeReportingService {
      void exporterRapport();
  }

  public interface VehiculeNotificationService {
      void envoyerNotification();
  }
  ```
  Un composant n'ayant besoin que de la consultation n'injectera désormais que `VehiculeReaderService`.

### Situation 3 — LSP (Liskov Substitution Principle)
- **Pourquoi lancer `UnsupportedOperationException` peut violer LSP ?**
  Le principe de substitution de Liskov stipule que si une classe $S$ est un sous-type de $T$, les objets de type $T$ doivent pouvoir être remplacés par des objets de type $S$ sans altérer la cohérence et l'exactitude du programme.
  Si un client appelle une méthode définie sur la classe de base `Vehicule` et reçoit une exception `UnsupportedOperationException` d'une sous-classe, le contrat promis par la classe parent est rompu, provoquant des échecs à l'exécution.
- **Solutions pour respecter le contrat** :
  1. **Préférer la composition à l'héritage** : si la classe fille ne supporte pas toutes les opérations de la classe mère, alors elle ne doit pas en hériter ("is-not-a").
  2. **Ségrégation d'interfaces (ISP)** : isoler le comportement optionnel dans une interface séparée que seules les sous-classes capables implémentent (ex : `Motorise`, `Remorquable`).

---

## 5. Synthèse des principes SOLID et patrons GoF utilisés

| Concept | Éléments dans AutoLoc API | Bénéfice obtenu |
| :--- | :--- | :--- |
| **SRP** | `VehiculeValidator`, `JournalisationService`, `VehiculeService` | Chaque classe a une unique responsabilité bien délimitée. |
| **OCP** | `TarificationService` + `StrategieTarification` | Nouvelles politiques tarifaires ajoutées par simple création de classe sans retoucher au service. |
| **LSP** | Respect des contrats d'interfaces et polymorphisme des stratégies | Toute stratégie peut être substituée sans casser le calcul. |
| **ISP** | Séparation des interfaces de service et des composants spécialisés | Aucun composant ne dépend de méthodes inutiles. |
| **DIP** | Repositories JPA et interfaces de services injectés par Spring | Découplage complet de l'infrastructure et testabilité unitaire isolée. |
| **GoF Strategy** | `TarificationStandard`, `TarificationWeekend`, `TarificationLongueDuree`, `TarificationEntreprise` | Encapsulation et interchangeabilité dynamique des formules de calcul. |
