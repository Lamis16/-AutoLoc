package tn.esprit.tpautoloc.domain;

import jakarta.persistence.*;
import lombok.*;
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

    @ManyToOne
    @JoinColumn(name = "agence_id_agence")
    private Agence agence;

    @OneToMany(mappedBy = "vehicule")
    private List<Reservation> reservations = new ArrayList<>();

    //Cascade simple : maintenance liée au véhicule
    @OneToMany(mappedBy = "vehicule", cascade = CascadeType.ALL)
    private List<Maintenance> maintenances = new ArrayList<>();

    // Cascade simple : équipements liés au véhicule
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "vehicule_equipements",
            joinColumns = @JoinColumn(name = "vehicules_id_vehicule"),
            inverseJoinColumns = @JoinColumn(name = "equipements_id_equipement")
    )
    private List<Equipement> equipements = new ArrayList<>();
}