package tn.esprit.tpautoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tpautoloc.domain.Equipement;
import tn.esprit.tpautoloc.service.IEquipementService;

import java.util.List;

@RestController
@RequestMapping("/equipements")
@RequiredArgsConstructor
@Tag(name = "Equipements", description = "Gestion des équipements")
public class EquipementController {
    private final IEquipementService equipementService;

    @GetMapping
    @Operation(summary = "Lister tous les équipements")
    @ApiResponse(responseCode = "200", description = "Liste récupérée")
    public List<Equipement> findAll() { return equipementService.findAll(); }

    @GetMapping("/{id}")
    @Operation(summary = "Consulter un équipement par ID")
    @ApiResponse(responseCode = "200", description = "Équipement trouvé")
    @ApiResponse(responseCode = "404", description = "Équipement introuvable", content = @Content)
    public Equipement findById(@PathVariable Long id) { return equipementService.findById(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Créer un équipement")
    @ApiResponse(responseCode = "201", description = "Équipement créé")
    @ApiResponse(responseCode = "400", description = "Libellé vide", content = @Content)
    public Equipement create(@RequestBody Equipement equipement) { return equipementService.create(equipement); }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier un équipement")
    @ApiResponse(responseCode = "200", description = "Équipement modifié")
    @ApiResponse(responseCode = "404", description = "Équipement introuvable", content = @Content)
    public Equipement update(@PathVariable Long id, @RequestBody Equipement equipement) {
        return equipementService.update(id, equipement);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Supprimer un équipement")
    @ApiResponse(responseCode = "204", description = "Équipement supprimé")
    @ApiResponse(responseCode = "404", description = "Équipement introuvable", content = @Content)
    public void delete(@PathVariable Long id) { equipementService.deleteById(id); }
}