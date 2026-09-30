package tn.esprit.tpautoloc.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.tpautoloc.domain.enums.StatutReservation;

import java.time.LocalDate;

@Entity
@Table(name = "reservation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReservation;

    private LocalDate dateDebut;
    private LocalDate dateFin;

    @Enumerated(EnumType.STRING)
    private StatutReservation statut; // EN_ATTENTE, CONFIRMEE, ANNULEE, TERMINEE

    // Une réservation concerne un seul client (côté propriétaire)
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    // Une réservation concerne un seul véhicule (côté propriétaire)
    @ManyToOne
    @JoinColumn(name = "vehicule_id")
    private Vehicule vehicule;

    // Une réservation génère un seul contrat (côté inverse)
    @OneToOne(mappedBy = "reservation")
    private Contrat contrat;
}