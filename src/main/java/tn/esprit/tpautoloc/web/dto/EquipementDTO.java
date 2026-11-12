package tn.esprit.tpautoloc.web.dto;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class EquipementDTO {
    private Long id;
    private String libelle;
}