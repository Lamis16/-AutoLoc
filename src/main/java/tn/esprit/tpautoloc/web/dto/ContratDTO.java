package tn.esprit.tpautoloc.web.dto;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ContratDTO {
    private Long id;
    private LocalDate dateSignature;

    @PositiveOrZero(message = "Le montant total doit être positif ou nul")
    private BigDecimal montantTotal;

    private Boolean valide;
    private Long reservationId;
}