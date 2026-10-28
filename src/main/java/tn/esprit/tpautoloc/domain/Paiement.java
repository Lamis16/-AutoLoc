package tn.esprit.tpautoloc.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import tn.esprit.tpautoloc.domain.enums.ModePaiement;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "paiement")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Paiement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPaiement;

    private BigDecimal montant;
    private LocalDate datePaiement;

    @Enumerated(EnumType.STRING)
    private ModePaiement modePaiement;

    // Coupe la boucle JSON côté enfant
    @ManyToOne
    @JoinColumn(name = "contrat_id_contrat")
    @JsonBackReference("contrat-paiements")
    private Contrat contrat;
}