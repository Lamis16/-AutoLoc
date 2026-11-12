package tn.esprit.tpautoloc.web.dto;
import lombok.*;
import tn.esprit.tpautoloc.domain.enums.RoleEmploye;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class EmployeeDTO {
    private Long id;
    private String nom;
    private String prenom;
    private RoleEmploye role;
    private Long agenceId;
}