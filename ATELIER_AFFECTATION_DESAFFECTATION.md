# Atelier : Services d'Affectation et Désaffectations

Ce document récapitule l'implémentation des services d'affectation et de désaffectation conformément aux spécifications du devoir.

---

## 1. Scénario 1 : Gestion des Employés (@ManyToOne)

### Contexte
Le service RH mute un agent vers l'agence de Tunis. Un employé ne peut appartenir qu'à une seule agence à la fois, donc un transfert passe d'abord par une désaffectation.

### Méthodes implémentées dans `EmployeService` et `EmployeServiceImpl`
```java
// 1. Désaffecter un employé de son agence actuelle
public Employe desaffecterEmploye(Long idEmploye);

// 2. Affecter un employé à une agence cible
public Employe affecterEmploye(Long idEmploye, Long idAgence);
```

### Endpoints REST dans `EmployeRestController`
- **Désaffecter :** `PUT /autoloc/employe/desaffecter-employe/{idEmploye}`
- **Affecter :** `PUT /autoloc/employe/affecter-employe/{idEmploye}/{idAgence}`

---

## 2. Scénario 2 : Rattachement de Véhicule (@ManyToOne)

### Contexte
Un véhicule neuf arrive et est rattaché à l'agence de Sfax.

### Méthodes implémentées dans `VehiculeService` et `VehiculeServiceImpl`
```java
// Affecter un véhicule à une agence
public Vehicule affecterVehicule(Long idVehicule, Long idAgence);

// Désaffecter un véhicule d'une agence (méthode complémentaire)
public Vehicule desaffecterVehicule(Long idVehicule);
```

### Endpoints REST dans `VehiculeRestController`
- **Affecter à une agence :** `PUT /autoloc/vehicule/affecter-vehicule/{idVehicule}/{idAgence}`
- **Désaffecter d'une agence :** `PUT /autoloc/vehicule/desaffecter-vehicule/{idVehicule}`

---

## 3. Scénario 3 : Équipements de Véhicule (@ManyToMany)

### Contexte
Un client demande un siège bébé. L'agent l'installe sur le véhicule, puis le retire au retour.

### Méthodes implémentées dans `VehiculeService` et `VehiculeServiceImpl`
```java
// Installer / Affecter un équipement sur le véhicule
public Vehicule affecterEquipement(Long idVehicule, Long idEquipement);

// Retirer / Désaffecter un équipement du véhicule au retour
public Vehicule desaffecterEquipement(Long idVehicule, Long idEquipement);
```

### Endpoints REST dans `VehiculeRestController`
- **Affecter un équipement :** `PUT /autoloc/vehicule/affecter-equipement/{idVehicule}/{idEquipement}`
- **Désaffecter un équipement :** `PUT /autoloc/vehicule/desaffecter-equipement/{idVehicule}/{idEquipement}`

---

## 4. Tests Unitaires
Les tests unitaires couvrant l'ensemble de ces scénarios ont été ajoutés et validés avec succès :
- [EmployeServiceTest.java](file:///C:/Users/hp/IdeaProjects/autoloc-api/src/test/java/tn/esprit/autoloc/service/EmployeServiceTest.java)
- [VehiculeServiceTest.java](file:///C:/Users/hp/IdeaProjects/autoloc-api/src/test/java/tn/esprit/autoloc/service/VehiculeServiceTest.java)
