package tn.esprit.tpautoloc.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class EquipementDTO {
    private Long id;

    @NotBlank(message = "Le libellé de l'équipement est obligatoire")
    private String libelle;
}