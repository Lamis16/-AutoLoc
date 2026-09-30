# AutoLoc — Plateforme de gestion de location de véhicules multi-agences

## 🎯 Objectif du projet

Concevoir et développer une plateforme Spring Boot permettant à plusieurs
agences de gérer la location de véhicules (flotte, réservations, clients,
facturation).

## 👥 Acteurs identifiés (Séance 1)

- **Client** : recherche un véhicule, réserve, paie.
- **Agent d'agence** : gère les réservations et l'état de la flotte.
- **Responsable d'agence** : supervise l'agence, consulte les statistiques.
- **Administrateur** : gère les agences, les utilisateurs et les paramètres globaux.

## 🛠️ Stack technique

- **Langage / Build** : Java 17+, Maven
- **Framework** : Spring Boot, Spring Data JPA, Spring MVC, Spring AOP, Spring Scheduler
- **Base de données** : MySQL 8.x via XAMPP/MariaDB (dev), H2 (tests)
- **Productivité** : Lombok, SLF4J/Logback, MapStruct (optionnel)
- **Documentation API** : springdoc-openapi (Swagger UI)
- **Tests** : JUnit 5, Mockito, MockMvc, Jacoco
- **Qualité** : SonarLint (IDE)
- **Outillage** : Git/GitHub, Postman, IntelliJ IDEA Ultimate

## 📌 Atelier 0 — Mise en place de l'environnement

- [x] JDK 17 installé et vérifié
- [x] IntelliJ IDEA Ultimate + licence étudiante activée
- [x] XAMPP (MySQL/MariaDB) + base `autoloc_db` créée
- [x] Postman installé + collection `AutoLoc-API`
- [x] Dépôt Git `AutoLoc` initialisé et cloné

## 👨‍💻 Équipe

- Lamis Touhami — lamis.Touhami@esprit.tn

## 📎 Preuves d'environnement fonctionnel

> Les captures d'écran seront ajoutées dans le dossier `docs/`.