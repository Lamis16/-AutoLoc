package tn.esprit.tpautoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tpautoloc.domain.Agence;
import tn.esprit.tpautoloc.service.IAgenceService;

import java.util.List;

@RestController
@RequestMapping("/agences")
@RequiredArgsConstructor
@Tag(name = "Agences", description = "Gestion des agences")
public class AgenceController {
    private final IAgenceService agenceService;

    @GetMapping
    @Operation(summary = "Lister toutes les agences")
    @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès")
    public List<Agence> findAll() { return agenceService.findAll(); }

    @GetMapping("/{id}")
    @Operation(summary = "Consulter une agence par ID")
    @ApiResponse(responseCode = "200", description = "Agence trouvée")
    @ApiResponse(responseCode = "404", description = "Agence introuvable", content = @Content)
    public Agence findById(@PathVariable Long id) { return agenceService.findById(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Créer une agence")
    @ApiResponse(responseCode = "201", description = "Agence créée")
    @ApiResponse(responseCode = "400", description = "Données invalides", content = @Content)
    public Agence create(@RequestBody Agence agence) { return agenceService.create(agence); }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier une agence")
    @ApiResponse(responseCode = "200", description = "Agence modifiée")
    @ApiResponse(responseCode = "404", description = "Agence introuvable", content = @Content)
    public Agence update(@PathVariable Long id, @RequestBody Agence agence) {
        return agenceService.update(id, agence);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Supprimer une agence")
    @ApiResponse(responseCode = "204", description = "Agence supprimée")
    @ApiResponse(responseCode = "404", description = "Agence introuvable", content = @Content)
    public void delete(@PathVariable Long id) { agenceService.deleteById(id); }
}