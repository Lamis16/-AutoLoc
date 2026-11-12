package tn.esprit.tpautoloc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.tpautoloc.domain.Client;
import tn.esprit.tpautoloc.domain.Reservation;
import tn.esprit.tpautoloc.observer.ReservationSubject;

@SpringBootTest
class ReservationServiceTest {

    @Autowired
    private ReservationSubject reservationSubject;

    @Test
    void testObserver() {
        // 1. Préparer un faux client et une fausse réservation
        Client client = new Client();
        client.setEmail("test@esprit.tn");
        client.setTelephone("20123456");

        Reservation res = new Reservation();
        res.setIdReservation(1L);
        res.setClient(client);

        // 2. Tester la confirmation
        System.out.println("--- TEST CONFIRMATION ---");
        reservationSubject.notifierConfirmation(res);

        // 3. Tester l'annulation
        System.out.println("--- TEST ANNULATION ---");
        reservationSubject.notifierAnnulation(res);
    }
}