package tn.esprit.tpautoloc.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "agence")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Agence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAgence;

    private String nom;
    private String ville;
    private String adresse;
    private String telephone;

    // Une agence possède plusieurs employés (côté inverse)
    @OneToMany(mappedBy = "agence")
    private List<Employee> employees = new ArrayList<>();

    // Une agence possède plusieurs véhicules (côté inverse)
    @OneToMany(mappedBy = "agence")
    private List<Vehicule> vehicules = new ArrayList<>();
}