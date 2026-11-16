package tn.esprit.tpautoloc.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import tn.esprit.tpautoloc.domain.enums.RoleEmploye;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class EmployeeDTO {
    private Long id;

    @NotBlank(message = "Le nom de l'employé est obligatoire")
    private String nom;

    @NotBlank(message = "Le prénom de l'employé est obligatoire")
    private String prenom;

    @NotNull(message = "Le rôle est obligatoire")
    private RoleEmploye role;

    private Long agenceId;
}