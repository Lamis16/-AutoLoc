package tn.esprit.tpautoloc.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.tpautoloc.domain.*;
import tn.esprit.tpautoloc.domain.enums.*;
import tn.esprit.tpautoloc.exception.ResourceNotFoundException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ServiceTests {

    // ===== Injection des services =====
    @Autowired private IAgenceService agenceService;
    @Autowired private IClientService clientService;
    @Autowired private IVehiculeService vehiculeService;
    @Autowired private IReservationService reservationService;
    @Autowired private IContratService contratService;
    @Autowired private IMaintenanceService maintenanceService;

    // ================================================================
    // PARTIE 2 — Test guidé de AgenceService
    // ================================================================
    @Test
    @Order(1)
    @DisplayName("Partie 2 — Test AgenceService (CRUD complet)")
    void testAgenceService() {
        System.out.println("\n========== TEST AGENCE SERVICE ==========");

        Agence agence = new Agence();
        agence.setNom("AutoLoc Tunis");
        agence.setVille("Tunis");
        agence.setAdresse("12 rue de la Liberté");
        agence.setTelephone("71234567");
        System.out.println("✔ Agence à créer : " + agence.getNom() + " - " + agence.getVille());

        Agence saved = agenceService.create(agence);
        System.out.println("✔ Agence enregistrée : id=" + saved.getIdAgence()
                + ", nom=" + saved.getNom()
                + ", ville=" + saved.getVille());
        assertNotNull(saved.getIdAgence());
        assertEquals("AutoLoc Tunis", saved.getNom());

        List<Agence> all = agenceService.findAll();
        System.out.println("✔ Nombre d'agences : " + all.size());
        all.forEach(a -> System.out.println("   - [id=" + a.getIdAgence() + "] "
                + a.getNom() + " (" + a.getVille() + ")"));

        Agence found = agenceService.findById(saved.getIdAgence());
        System.out.println("✔ findById : " + found.getNom() + " - " + found.getVille());
        assertEquals(saved.getIdAgence(), found.getIdAgence());

        long count = all.size();
        System.out.println("✔ Nombre d'agences (count) : " + count);
        assertTrue(count > 0);

        agenceService.deleteById(saved.getIdAgence());
        System.out.println("✔ Agence supprimée (id=" + saved.getIdAgence() + ")");

        assertThrows(ResourceNotFoundException.class,
                () -> agenceService.findById(saved.getIdAgence()));
        System.out.println("✔ Vérification post-suppression : OK");
        System.out.println("========== FIN TEST AGENCE ==========");
    }

    // ================================================================
    // PARTIE 3 — Test guidé de ClientService
    // ================================================================
    @Test
    @Order(2)
    @DisplayName("Partie 3 — Test ClientService")
    void testClientService() {
        System.out.println("\n========== TEST CLIENT SERVICE ==========");

        // 1. Créer deux clients
        Client c1 = new Client();
        c1.setNom("Touhami");
        c1.setPrenom("Lamis");
        c1.setEmail("lamis.touhami@esprit.tn");
        c1.setTelephone("20123456");
        c1.setNumPermis("P123456");
        c1.setDateInscription(LocalDate.now());

        Client c2 = new Client();
        c2.setNom("Ben Salah");
        c2.setPrenom("Ahmed");
        c2.setEmail("ahmed.bensalah@esprit.tn");
        c2.setTelephone("21654321");
        c2.setNumPermis("P654321");
        c2.setDateInscription(LocalDate.now());

        // 2. Enregistrer les deux clients
        Client savedC1 = clientService.create(c1);
        Client savedC2 = clientService.create(c2);
        System.out.println("✔ Client 1 : id=" + savedC1.getIdClient()
                + ", " + savedC1.getNom() + " " + savedC1.getPrenom()
                + " (" + savedC1.getEmail() + ")");
        System.out.println("✔ Client 2 : id=" + savedC2.getIdClient()
                + ", " + savedC2.getNom() + " " + savedC2.getPrenom()
                + " (" + savedC2.getEmail() + ")");
        assertNotNull(savedC1.getIdClient());
        assertNotNull(savedC2.getIdClient());

        // 3. Afficher la liste avec findAll()
        List<Client> all = clientService.findAll();
        System.out.println("✔ Nombre de clients : " + all.size());
        all.forEach(c -> System.out.println("   - [id=" + c.getIdClient() + "] "
                + c.getNom() + " " + c.getPrenom()
                + " (" + c.getEmail() + ")"));

        // 4. Rechercher un client avec findById()
        Client found = clientService.findById(savedC1.getIdClient());
        System.out.println("✔ findById : " + found.getNom() + " " + found.getPrenom());
        assertEquals("Touhami", found.getNom());
        assertEquals("Lamis", found.getPrenom());

        // 5. Nombre de clients (count)
        long count = all.size();
        System.out.println("✔ Nombre de clients (count) : " + count);
        assertTrue(count >= 2);

        // 6. Supprimer le client avec deleteById()
        clientService.deleteById(savedC1.getIdClient());
        System.out.println("✔ Client 1 supprimé (id=" + savedC1.getIdClient() + ")");

        // 7. Vérifier qu'il n'existe plus
        assertThrows(ResourceNotFoundException.class,
                () -> clientService.findById(savedC1.getIdClient()));
        System.out.println("✔ Vérification post-suppression : OK (ResourceNotFoundException levée)");

        System.out.println("========== FIN TEST CLIENT ==========");
    }

    // ================================================================
    // PARTIE 4 — Test guidé de VehiculeService
    // ================================================================
    @Test
    @Order(3)
    @DisplayName("Partie 4 — Test VehiculeService")
    void testVehiculeService() {
        System.out.println("\n========== TEST VEHICULE SERVICE ==========");

        // 1. Créer un véhicule
        Vehicule v = new Vehicule();
        v.setImmatriculation("TU-123-AB");
        v.setMarque("Renault");
        v.setModele("Clio");
        v.setCategorie(CategorieVehicule.CITADINE);
        v.setTarifJournalier(new BigDecimal("80.00"));
        v.setStatut(StatutVehicule.DISPONIBLE);
        System.out.println("✔ Véhicule à créer : " + v.getMarque() + " " + v.getModele());

        // 2. Enregistrer le véhicule
        Vehicule saved = vehiculeService.create(v);
        System.out.println("✔ Véhicule enregistré : id=" + saved.getIdVehicule()
                + ", " + saved.getMarque() + " " + saved.getModele()
                + " [" + saved.getImmatriculation() + "]");
        assertNotNull(saved.getIdVehicule());
        assertEquals("Renault", saved.getMarque());

        // 3. Afficher tous les véhicules avec findAll()
        List<Vehicule> all = vehiculeService.findAll();
        System.out.println("✔ Nombre de véhicules : " + all.size());
        all.forEach(veh -> System.out.println("   - [id=" + veh.getIdVehicule() + "] "
                + veh.getMarque() + " " + veh.getModele()
                + " - " + veh.getTarifJournalier() + " DT/jour"));

        // 4. Rechercher un véhicule avec findById()
        Vehicule found = vehiculeService.findById(saved.getIdVehicule());
        System.out.println("✔ findById : " + found.getMarque() + " " + found.getModele());
        assertEquals(saved.getIdVehicule(), found.getIdVehicule());

        // 5. Nombre de véhicules (count)
        long count = all.size();
        System.out.println("✔ Nombre de véhicules (count) : " + count);
        assertTrue(count > 0);

        // 6. Supprimer le véhicule avec deleteById()
        vehiculeService.deleteById(saved.getIdVehicule());
        System.out.println("✔ Véhicule supprimé (id=" + saved.getIdVehicule() + ")");

        // 7. Vérifier la suppression
        assertThrows(ResourceNotFoundException.class,
                () -> vehiculeService.findById(saved.getIdVehicule()));
        System.out.println("✔ Vérification post-suppression : OK");

        System.out.println("========== FIN TEST VEHICULE ==========");
    }

    // ================================================================
    // PARTIE 5.1 — Test ReservationService (exercice autonome)
    // ================================================================
    @Test
    @Order(4)
    @DisplayName("Partie 5.1 — Test ReservationService")
    void testReservationService() {
        System.out.println("\n========== TEST RESERVATION SERVICE ==========");

        // Créer les dépendances
        Client client = new Client();
        client.setNom("Test");
        client.setPrenom("Reservation");
        client.setEmail("resa.test@esprit.tn");
        client.setDateInscription(LocalDate.now());
        Client savedClient = clientService.create(client);

        Vehicule v = new Vehicule();
        v.setImmatriculation("RS-456-CD");
        v.setMarque("Peugeot");
        v.setModele("208");
        v.setCategorie(CategorieVehicule.CITADINE);
        v.setTarifJournalier(new BigDecimal("70.00"));
        v.setStatut(StatutVehicule.DISPONIBLE);
        Vehicule savedV = vehiculeService.create(v);

        // Créer une réservation
        Reservation r = new Reservation();
        r.setDateDebut(LocalDate.now().plusDays(1));
        r.setDateFin(LocalDate.now().plusDays(5));
        r.setStatut(StatutReservation.EN_ATTENTE);
        r.setClient(savedClient);
        r.setVehicule(savedV);

        Reservation saved = reservationService.create(r);
        System.out.println("✔ Réservation créée : id=" + saved.getIdReservation()
                + ", du " + saved.getDateDebut() + " au " + saved.getDateFin());
        assertNotNull(saved.getIdReservation());

        // findAll()
        List<Reservation> all = reservationService.findAll();
        System.out.println("✔ Nombre de réservations : " + all.size());

        // findById()
        Reservation found = reservationService.findById(saved.getIdReservation());
        System.out.println("✔ findById : id=" + found.getIdReservation()
                + ", statut=" + found.getStatut());
        assertEquals(saved.getIdReservation(), found.getIdReservation());

        // deleteById()
        reservationService.deleteById(saved.getIdReservation());
        System.out.println("✔ Réservation supprimée");

        assertThrows(ResourceNotFoundException.class,
                () -> reservationService.findById(saved.getIdReservation()));
        System.out.println("✔ Vérification post-suppression : OK");

        // Nettoyage
        clientService.deleteById(savedClient.getIdClient());
        vehiculeService.deleteById(savedV.getIdVehicule());

        System.out.println("========== FIN TEST RESERVATION ==========");
    }

    // ================================================================
    // PARTIE 5.2 — Test ContratService (exercice autonome)
    // ================================================================
    @Test
    @Order(5)
    @DisplayName("Partie 5.2 — Test ContratService")
    void testContratService() {
        System.out.println("\n========== TEST CONTRAT SERVICE ==========");

        // Créer les dépendances
        Client client = new Client();
        client.setNom("Test");
        client.setPrenom("Contrat");
        client.setEmail("contrat.test@esprit.tn");
        client.setDateInscription(LocalDate.now());
        Client savedClient = clientService.create(client);

        Vehicule v = new Vehicule();
        v.setImmatriculation("CT-789-EF");
        v.setMarque("Citroen");
        v.setModele("C3");
        v.setCategorie(CategorieVehicule.CITADINE);
        v.setTarifJournalier(new BigDecimal("60.00"));
        v.setStatut(StatutVehicule.DISPONIBLE);
        Vehicule savedV = vehiculeService.create(v);

        Reservation r = new Reservation();
        r.setDateDebut(LocalDate.now());
        r.setDateFin(LocalDate.now().plusDays(3));
        r.setStatut(StatutReservation.CONFIRMEE);
        r.setClient(savedClient);
        r.setVehicule(savedV);
        Reservation savedR = reservationService.create(r);

        // Créer un contrat
        Contrat c = new Contrat();
        c.setDateSignature(LocalDate.now());
        c.setMontantTotal(new BigDecimal("240.00"));
        c.setValide(true);
        c.setReservation(savedR);

        Contrat saved = contratService.create(c);
        System.out.println("✔ Contrat créé : id=" + saved.getIdContrat()
                + ", montant=" + saved.getMontantTotal() + " DT");
        assertNotNull(saved.getIdContrat());

        // findAll()
        List<Contrat> all = contratService.findAll();
        System.out.println("✔ Nombre de contrats : " + all.size());

        // findById()
        Contrat found = contratService.findById(saved.getIdContrat());
        System.out.println("✔ findById : id=" + found.getIdContrat()
                + ", valide=" + found.getValide());
        assertEquals(saved.getIdContrat(), found.getIdContrat());

        // update()
        found.setMontantTotal(new BigDecimal("250.00"));
        Contrat updated = contratService.update(found.getIdContrat(), found);
        assertEquals(new BigDecimal("250.00"), updated.getMontantTotal());
        System.out.println("✔ Contrat modifié : montant = " + updated.getMontantTotal() + " DT");

        // deleteById()
        contratService.deleteById(updated.getIdContrat());
        System.out.println("✔ Contrat supprimé");

        assertThrows(ResourceNotFoundException.class,
                () -> contratService.findById(updated.getIdContrat()));
        System.out.println("✔ Vérification post-suppression : OK");

        // Nettoyage
        reservationService.deleteById(savedR.getIdReservation());
        clientService.deleteById(savedClient.getIdClient());
        vehiculeService.deleteById(savedV.getIdVehicule());

        System.out.println("========== FIN TEST CONTRAT ==========");
    }

    // ================================================================
    // PARTIE 5.3 — Test MaintenanceService (exercice autonome)
    // ================================================================
    @Test
    @Order(6)
    @DisplayName("Partie 5.3 — Test MaintenanceService")
    void testMaintenanceService() {
        System.out.println("\n========== TEST MAINTENANCE SERVICE ==========");

        // Créer un véhicule
        Vehicule v = new Vehicule();
        v.setImmatriculation("MT-101-GH");
        v.setMarque("Ford");
        v.setModele("Focus");
        v.setCategorie(CategorieVehicule.BERLINE);
        v.setTarifJournalier(new BigDecimal("110.00"));
        v.setStatut(StatutVehicule.MAINTENANCE);
        Vehicule savedV = vehiculeService.create(v);

        // Créer une maintenance
        Maintenance m = new Maintenance();
        m.setDateDebut(LocalDate.now());
        m.setDateFin(LocalDate.now().plusDays(2));
        m.setDescription("Vidange + contrôle freins");
        m.setVehicule(savedV);

        Maintenance saved = maintenanceService.create(m);
        System.out.println("✔ Maintenance créée : id=" + saved.getIdMaintenance()
                + ", " + saved.getDescription());
        assertNotNull(saved.getIdMaintenance());

        // findAll()
        List<Maintenance> all = maintenanceService.findAll();
        System.out.println("✔ Nombre de maintenances : " + all.size());
        all.forEach(main -> System.out.println("   - [id=" + main.getIdMaintenance() + "] "
                + main.getDescription() + " du " + main.getDateDebut() + " au " + main.getDateFin()));

        // findById()
        Maintenance found = maintenanceService.findById(saved.getIdMaintenance());
        System.out.println("✔ findById : " + found.getDescription());
        assertEquals(saved.getIdMaintenance(), found.getIdMaintenance());

        // deleteById()
        maintenanceService.deleteById(saved.getIdMaintenance());
        System.out.println("✔ Maintenance supprimée");

        assertThrows(ResourceNotFoundException.class,
                () -> maintenanceService.findById(saved.getIdMaintenance()));
        System.out.println("✔ Vérification post-suppression : OK");

        // Nettoyage
        vehiculeService.deleteById(savedV.getIdVehicule());

        System.out.println("========== FIN TEST MAINTENANCE ==========");
    }
}