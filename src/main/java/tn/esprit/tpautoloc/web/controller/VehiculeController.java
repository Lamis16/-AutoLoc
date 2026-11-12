package tn.esprit.tpautoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tpautoloc.domain.Vehicule;
import tn.esprit.tpautoloc.service.IVehiculeService;

import java.util.List;

@RestController
@RequestMapping("/vehicules")
@RequiredArgsConstructor
@Tag(name = "Vehicules", description = "Gestion des véhicules")
public class VehiculeController {

    private final IVehiculeService vehiculeService;

    @GetMapping
    @Operation(summary = "Lister tous les véhicules")
    @ApiResponse(responseCode = "200", description = "Liste récupérée")
    public List<Vehicule> findAll() { return vehiculeService.findAll(); }

    @GetMapping("/{id}")
    @Operation(summary = "Consulter un véhicule par ID")
    @ApiResponse(responseCode = "200", description = "Véhicule trouvé")
    @ApiResponse(responseCode = "404", description = "Véhicule introuvable", content = @Content)
    public Vehicule findById(@PathVariable Long id) { return vehiculeService.findById(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Ajouter un véhicule")
    @ApiResponse(responseCode = "201", description = "Véhicule ajouté")
    @ApiResponse(responseCode = "400", description = "Tarif négatif", content = @Content)
    public Vehicule create(@RequestBody Vehicule vehicule) { return vehiculeService.create(vehicule); }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier un véhicule")
    @ApiResponse(responseCode = "200", description = "Véhicule modifié")
    @ApiResponse(responseCode = "404", description = "Véhicule introuvable", content = @Content)
    public Vehicule update(@PathVariable Long id, @RequestBody Vehicule vehicule) {
        return vehiculeService.update(id, vehicule);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Supprimer un véhicule")
    @ApiResponse(responseCode = "204", description = "Véhicule supprimé")
    @ApiResponse(responseCode = "404", description = "Véhicule introuvable", content = @Content)
    public void delete(@PathVariable Long id) { vehiculeService.deleteById(id); }

    // ==========================================
    // NOUVEAUX ENDPOINTS - ATELIER 7
    // ==========================================

    @PutMapping("/{vehiculeId}/agence/{agenceId}")
    @Operation(summary = "Affecter un véhicule à une agence")
    @ApiResponse(responseCode = "200", description = "Affectation réussie")
    @ApiResponse(responseCode = "404", description = "Véhicule ou Agence introuvable", content = @Content)
    public void affecterVehiculeAAgence(@PathVariable Long vehiculeId, @PathVariable Long agenceId) {
        vehiculeService.affecterVehiculeAAgence(vehiculeId, agenceId);
    }

    @PutMapping("/{vehiculeId}/desaffecter")
    @Operation(summary = "Désaffecter un véhicule de son agence")
    @ApiResponse(responseCode = "200", description = "Désaffectation réussie")
    @ApiResponse(responseCode = "400", description = "Le véhicule n'a pas d'agence", content = @Content)
    @ApiResponse(responseCode = "404", description = "Véhicule introuvable", content = @Content)
    public void desaffecterVehiculeDeAgence(@PathVariable Long vehiculeId) {
        vehiculeService.desaffecterVehiculeDeAgence(vehiculeId);
    }
}