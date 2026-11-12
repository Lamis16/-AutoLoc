package tn.esprit.tpautoloc.web.dto;

import lombok.*;
import tn.esprit.tpautoloc.domain.enums.ModePaiement;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class PaiementDTO {
    private Long id;
    private BigDecimal montant;
    private LocalDate datePaiement;
    private ModePaiement modePaiement;
    private Long contratId;
}