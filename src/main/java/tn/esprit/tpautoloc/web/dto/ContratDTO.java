package tn.esprit.tpautoloc.web.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ContratDTO {
    private Long id;
    private LocalDate dateSignature;
    private BigDecimal montantTotal;
    private Boolean valide;
    private Long reservationId;
}