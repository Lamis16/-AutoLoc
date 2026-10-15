package tn.esprit.tpautoloc;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.tpautoloc.domain.*;
import tn.esprit.tpautoloc.domain.enums.*;
import tn.esprit.tpautoloc.repository.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TpAutolocApplicationTests {

    // ===== Injection des repositories =====
    @Autowired private IAgenceRepository agenceRepository;
    @Autowired private IClientRepository clientRepository;
    @Autowired private IVehiculeRepository vehiculeRepository;
    @Autowired private IReservationRepository reservationRepository;
    @Autowired private IContratRepository contratRepository;
    @Autowired private IMaintenanceRepository maintenanceRepository;

    // Test par défaut (conservé)

    @Test
    @Order(0)
    @DisplayName("Test 0 — Contexte Spring démarre correctement")
    void contextLoads() {
        System.out.println("\n✅ Contexte Spring Boot chargé avec succès");
        assertNotNull(agenceRepository);
        assertNotNull(clientRepository);
        assertNotNull(vehiculeRepository);
    }

    // PARTIE 2 — Test guidé de AgenceRepository

    @Test
    @Order(1)
    @DisplayName("Partie 2 — Test AgenceRepository (CRUD complet)")
    void testAgenceRepository() {
        System.out.println("\n========== TEST AGENCE REPOSITORY ==========");

        // 1. Créer une agence
        Agence agence = new Agence();
        agence.setNom("AutoLoc Tunis");
        agence.setVille("Tunis");
        agence.setAdresse("Kelibia");
        agence.setTelephone("71234567");

        // 2. save()
        Agence saved = agenceRepository.save(agence);
        System.out.println("✔ Agence enregistrée : " + saved);
        assertNotNull(saved.getIdAgence());
        assertEquals("AutoLoc Tunis", saved.getNom());

        // 3. findAll()
        List<Agence> all = agenceRepository.findAll();
        System.out.println("✔ Nombre d'agences : " + all.size());
        all.forEach(a -> System.out.println("   - " + a));
        assertFalse(all.isEmpty());

        // 4. findById()
        Optional<Agence> found = agenceRepository.findById(saved.getIdAgence());
        System.out.println("✔ findById : " + found);
        assertTrue(found.isPresent());

        // 5. existsById()
        boolean exists = agenceRepository.existsById(saved.getIdAgence());
        System.out.println("✔ existsById : " + exists);
        assertTrue(exists);

        // 6. count()
        long count = agenceRepository.count();
        System.out.println("✔ count : " + count);
        assertTrue(count > 0);

        // 7. deleteById()
       /* agenceRepository.deleteById(saved.getIdAgence());
        System.out.println("✔ Agence supprimée");

        assertFalse(agenceRepository.existsById(saved.getIdAgence()));
        System.out.println("✔ Vérification post-suppression : OK");
    */}

    // PARTIE 3 — Test guidé de ClientRepository
    @Test
    @Order(2)
    @DisplayName("Partie 3 — Test ClientRepository")
    void testClientRepository() {
        System.out.println("\n========== TEST CLIENT REPOSITORY ==========");

        // Créer 2 clients
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

        Client savedC1 = clientRepository.save(c1);
        Client savedC2 = clientRepository.save(c2);
        System.out.println("✔ Client 1 : " + savedC1);
        System.out.println("✔ Client 2 : " + savedC2);
        assertNotNull(savedC1.getIdClient());
        assertNotNull(savedC2.getIdClient());

        // findAll()
        List<Client> all = clientRepository.findAll();
        System.out.println("✔ Nombre de clients : " + all.size());
        all.forEach(c -> System.out.println("   - " + c));

        // findById()
        Optional<Client> found = clientRepository.findById(savedC1.getIdClient());
        System.out.println("✔ findById : " + found);
        assertTrue(found.isPresent());
        assertEquals("Touhami", found.get().getNom());

        // existsById — existant
        assertTrue(clientRepository.existsById(savedC1.getIdClient()));
        System.out.println("✔ existsById (existant) : true");

        // existsById — inexistant
        assertFalse(clientRepository.existsById(99999L));
        System.out.println("✔ existsById (inexistant) : false");

        // count()
        long count = clientRepository.count();
        System.out.println("✔ count : " + count);
        assertTrue(count >= 2);
    }

    // PARTIE 4 — Test guidé de VehiculeRepository
    @Test
    @Order(3)
    @DisplayName("Partie 4 — Test VehiculeRepository")
    void testVehiculeRepository() {
        System.out.println("\n========== TEST VEHICULE REPOSITORY ==========");

        Vehicule v = new Vehicule();
        v.setImmatriculation("TU-123-AB");
        v.setMarque("Renault");
        v.setModele("Clio");
        v.setCategorie(CategorieVehicule.CITADINE);
        v.setTarifJournalier(new BigDecimal("80.00"));
        v.setStatut(StatutVehicule.DISPONIBLE);

        Vehicule saved = vehiculeRepository.save(v);
        System.out.println("✔ Véhicule enregistré : " + saved);
        assertNotNull(saved.getIdVehicule());

        List<Vehicule> all = vehiculeRepository.findAll();
        System.out.println("✔ Nombre de véhicules : " + all.size());

        long count = vehiculeRepository.count();
        System.out.println("✔ count : " + count);

        // Modification
        saved.setTarifJournalier(new BigDecimal("95.00"));
        saved.setStatut(StatutVehicule.LOUE);
        Vehicule updated = vehiculeRepository.save(saved);
        System.out.println("✔ Véhicule modifié : " + updated);
        assertEquals(new BigDecimal("95.00"), updated.getTarifJournalier());
        assertEquals(StatutVehicule.LOUE, updated.getStatut());

        // Suppression
        vehiculeRepository.deleteById(updated.getIdVehicule());
        System.out.println("✔ Véhicule supprimé");
        assertFalse(vehiculeRepository.existsById(updated.getIdVehicule()));
    }

    // ================================================================
    // PARTIE 5.1 — Test ReservationRepository
    // ================================================================
    @Test
    @Order(4)
    @DisplayName("Partie 5.1 — Test ReservationRepository")
    void testReservationRepository() {
        System.out.println("\n========== TEST RESERVATION REPOSITORY ==========");

        // Dépendances
        Client client = new Client();
        client.setNom("Test");
        client.setPrenom("Client");
        client.setEmail("reservation.test@esprit.tn");
        client.setDateInscription(LocalDate.now());
        Client savedClient = clientRepository.save(client);

        Vehicule v = new Vehicule();
        v.setImmatriculation("RS-456-CD");
        v.setMarque("Peugeot");
        v.setModele("208");
        v.setCategorie(CategorieVehicule.CITADINE);
        v.setTarifJournalier(new BigDecimal("70.00"));
        v.setStatut(StatutVehicule.DISPONIBLE);
        Vehicule savedV = vehiculeRepository.save(v);

        Reservation r = new Reservation();
        r.setDateDebut(LocalDate.now().plusDays(1));
        r.setDateFin(LocalDate.now().plusDays(5));
        r.setStatut(StatutReservation.EN_ATTENTE);
        r.setClient(savedClient);
        r.setVehicule(savedV);

        Reservation saved = reservationRepository.save(r);
        System.out.println("✔ Réservation enregistrée : " + saved);
        assertNotNull(saved.getIdReservation());

        List<Reservation> all = reservationRepository.findAll();
        System.out.println("✔ Nombre de réservations : " + all.size());

        assertTrue(reservationRepository.existsById(saved.getIdReservation()));

        reservationRepository.deleteById(saved.getIdReservation());
        System.out.println("✔ Réservation supprimée");

        // Nettoyage
        clientRepository.deleteById(savedClient.getIdClient());
        vehiculeRepository.deleteById(savedV.getIdVehicule());
    }

    // ================================================================
    // PARTIE 5.2 — Test ContratRepository
    // ================================================================
    @Test
    @Order(5)
    @DisplayName("Partie 5.2 — Test ContratRepository")
    void testContratRepository() {
        System.out.println("\n========== TEST CONTRAT REPOSITORY ==========");

        // ================================================================
        // 1. Créer les dépendances (Client + Véhicule)
        // ================================================================
        Client client = new Client();
        client.setNom("Contrat");
        client.setPrenom("Test");
        client.setEmail("contrat.test@esprit.tn");
        client.setDateInscription(LocalDate.now());
        Client savedClient = clientRepository.save(client);
        System.out.println("✔ Client créé : " + savedClient.getIdClient());

        Vehicule v = new Vehicule();
        v.setImmatriculation("CT-789-EF");
        v.setMarque("Citroen");
        v.setModele("C3");
        v.setCategorie(CategorieVehicule.CITADINE);
        v.setTarifJournalier(new BigDecimal("60.00"));
        v.setStatut(StatutVehicule.DISPONIBLE);
        Vehicule savedV = vehiculeRepository.save(v);
        System.out.println("✔ Véhicule créé : " + savedV.getIdVehicule());

        // ================================================================
        // 2. Créer une Réservation
        // ================================================================
        Reservation r = new Reservation();
        r.setDateDebut(LocalDate.now());
        r.setDateFin(LocalDate.now().plusDays(3));
        r.setStatut(StatutReservation.CONFIRMEE);
        r.setClient(savedClient);
        r.setVehicule(savedV);
        Reservation savedR = reservationRepository.save(r);
        System.out.println("✔ Réservation créée : " + savedR.getIdReservation());

        // ================================================================
        // 3. Créer un Contrat
        // ================================================================
        Contrat c = new Contrat();
        c.setDateSignature(LocalDate.now());
        c.setMontantTotal(new BigDecimal("240.00"));
        c.setValide(true);
        c.setReservation(savedR);

        Contrat saved = contratRepository.save(c);
        System.out.println("✔ Contrat enregistré : " + saved);
        assertNotNull(saved.getIdContrat());

        // ================================================================
        // 4. Lier le Contrat à la Réservation (côté inverse)
        // ================================================================
        savedR.setContrat(saved);
        reservationRepository.save(savedR);
        System.out.println("✔ Contrat lié à la Réservation");

        // ================================================================
        // 5. findAll()
        // ================================================================
        List<Contrat> all = contratRepository.findAll();
        System.out.println("✔ Nombre de contrats : " + all.size());
        assertFalse(all.isEmpty(), "La liste des contrats ne doit pas être vide");

        // ================================================================
        // 6. findById()
        // ================================================================
        Optional<Contrat> found = contratRepository.findById(saved.getIdContrat());
        System.out.println("✔ findById : " + found);
        assertTrue(found.isPresent());

        // ================================================================
        // 7. existsById()
        // ================================================================
        assertTrue(contratRepository.existsById(saved.getIdContrat()));
        System.out.println("✔ existsById : true");

        // ================================================================
        // 8. Modification
        // ================================================================
        saved.setValide(false);
        Contrat updated = contratRepository.save(saved);
        assertFalse(updated.getValide(), "Le contrat doit être invalide");
        System.out.println("✔ Contrat modifié : valide = " + updated.getValide());

        // ================================================================
        // 9. Suppression en cascade
        // ================================================================
        // ✅ Supprimer la Réservation supprime automatiquement le Contrat
        //    (grâce à cascade = CascadeType.ALL + orphanRemoval = true)
        reservationRepository.deleteById(savedR.getIdReservation());
        System.out.println("✔ Réservation + Contrat supprimés (cascade)");

        // ================================================================
        // 10. Nettoyage des dépendances
        // ================================================================
        clientRepository.deleteById(savedClient.getIdClient());
        System.out.println("✔ Client supprimé");

        vehiculeRepository.deleteById(savedV.getIdVehicule());
        System.out.println("✔ Véhicule supprimé");

        System.out.println("========== FIN TEST CONTRAT ==========");
    }

    // ================================================================
    // PARTIE 5.3 — Test MaintenanceRepository
    // ================================================================
    @Test
    @Order(6)
    @DisplayName("Partie 5.3 — Test MaintenanceRepository")
    void testMaintenanceRepository() {
        System.out.println("\n========== TEST MAINTENANCE REPOSITORY ==========");

        Vehicule v = new Vehicule();
        v.setImmatriculation("MT-101-GH");
        v.setMarque("Ford");
        v.setModele("Focus");
        v.setCategorie(CategorieVehicule.BERLINE);
        v.setTarifJournalier(new BigDecimal("110.00"));
        v.setStatut(StatutVehicule.MAINTENANCE);
        Vehicule savedV = vehiculeRepository.save(v);

        Maintenance m = new Maintenance();
        m.setDateDebut(LocalDate.now());
        m.setDateFin(LocalDate.now().plusDays(2));
        m.setDescription("Vidange + contrôle freins");
        m.setVehicule(savedV);

        Maintenance saved = maintenanceRepository.save(m);
        System.out.println("✔ Maintenance enregistrée : " + saved);
        assertNotNull(saved.getIdMaintenance());

        List<Maintenance> all = maintenanceRepository.findAll();
        System.out.println("✔ Nombre de maintenances : " + all.size());

        assertTrue(maintenanceRepository.existsById(saved.getIdMaintenance()));

        maintenanceRepository.deleteById(saved.getIdMaintenance());
        System.out.println("✔ Maintenance supprimée");

        // Nettoyage
        vehiculeRepository.deleteById(savedV.getIdVehicule());
    }
}