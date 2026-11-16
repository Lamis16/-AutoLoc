package tn.esprit.tpautoloc.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class AgenceDTO {
    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @NotBlank(message = "La ville est obligatoire")
    private String ville;

    private String adresse;
    private String telephone;
}