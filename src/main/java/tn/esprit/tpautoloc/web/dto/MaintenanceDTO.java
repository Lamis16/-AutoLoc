package tn.esprit.tpautoloc.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDate;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class MaintenanceDTO {
    private Long id;

    @NotNull(message = "La date de début est obligatoire")
    private LocalDate dateDebut;

    @NotNull(message = "La date de fin est obligatoire")
    private LocalDate dateFin;

    private String description;

    @NotNull(message = "L'ID du véhicule est obligatoire")
    private Long vehiculeId;
}