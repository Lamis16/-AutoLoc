package tn.esprit.tpautoloc.web.dto;

import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class AgenceDTO {
    private Long id;
    private String nom;
    private String ville;
    private String adresse;
    private String telephone;
}