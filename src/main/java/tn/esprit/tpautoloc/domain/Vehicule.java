package tn.esprit.tpautoloc.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.tpautoloc.domain.enums.CategorieVehicule;
import tn.esprit.tpautoloc.domain.enums.StatutVehicule;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vehicule")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vehicule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVehicule;

    private String immatriculation;
    private String marque;
    private String modele;

    @Enumerated(EnumType.STRING)
    private CategorieVehicule categorie;

    private BigDecimal tarifJournalier;

    @Enumerated(EnumType.STRING)
    private StatutVehicule statut;

    // Un véhicule appartient à une seule agence (côté propriétaire)
    @ManyToOne
    @JoinColumn(name = "agence_id")
    private Agence agence;

    // Un véhicule peut être réservé plusieurs fois (côté inverse)
    @OneToMany(mappedBy = "vehicule")
    private List<Reservation> reservations = new ArrayList<>();

    // Un véhicule peut avoir plusieurs maintenances (côté inverse)
    @OneToMany(mappedBy = "vehicule")
    private List<Maintenance> maintenances = new ArrayList<>();

    // Un véhicule possède plusieurs équipements, partagés entre véhicules (côté propriétaire)
    @ManyToMany
    @JoinTable(
            name = "vehicule_equipements",
            joinColumns = @JoinColumn(name = "vehicule_id"),
            inverseJoinColumns = @JoinColumn(name = "equipement_id")
    )
    private List<Equipement> equipements = new ArrayList<>();
}