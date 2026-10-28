package tn.esprit.tpautoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tpautoloc.domain.Contrat;
import tn.esprit.tpautoloc.service.IContratService;

import java.util.List;

@RestController
@RequestMapping("/contrats")
@RequiredArgsConstructor
@Tag(name = "Contrats", description = "Gestion des contrats de location")
public class ContratController {

    private final IContratService contratService;

    @GetMapping
    @Operation(summary = "Lister tous les contrats", description = "Récupère la liste complète des contrats.")
    @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès")
    public List<Contrat> findAll() {
        return contratService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulter un contrat par ID")
    @ApiResponse(responseCode = "200", description = "Contrat trouvé")
    @ApiResponse(responseCode = "404", description = "Contrat introuvable", content = @Content)
    public Contrat findById(@PathVariable Long id) {
        return contratService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Créer un contrat", description = "Crée un nouveau contrat. Les paiements associés sont créés en cascade.")
    @ApiResponse(responseCode = "201", description = "Contrat créé avec succès")
    @ApiResponse(responseCode = "400", description = "Données invalides (ex: montant négatif)", content = @Content)
    public Contrat create(@RequestBody Contrat contrat) {
        return contratService.create(contrat);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier un contrat")
    @ApiResponse(responseCode = "200", description = "Contrat modifié avec succès")
    @ApiResponse(responseCode = "404", description = "Contrat introuvable", content = @Content)
    @ApiResponse(responseCode = "400", description = "Données invalides", content = @Content)
    public Contrat update(@PathVariable Long id, @RequestBody Contrat contrat) {
        return contratService.update(id, contrat);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Supprimer un contrat", description = "Supprime le contrat et ses paiements associés (cascade REMOVE).")
    @ApiResponse(responseCode = "204", description = "Contrat supprimé avec succès")
    @ApiResponse(responseCode = "404", description = "Contrat introuvable", content = @Content)
    public void delete(@PathVariable Long id) {
        contratService.deleteById(id);
    }
}