package tn.esprit.tpautoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tpautoloc.domain.Employee;
import tn.esprit.tpautoloc.service.IEmployeeService;

import java.util.List;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
@Tag(name = "Employes", description = "Gestion des employés")
public class EmployeeController {
    private final IEmployeeService employeeService;

    @GetMapping
    @Operation(summary = "Lister tous les employés")
    @ApiResponse(responseCode = "200", description = "Liste récupérée")
    public List<Employee> findAll() { return employeeService.findAll(); }

    @GetMapping("/{id}")
    @Operation(summary = "Consulter un employé par ID")
    @ApiResponse(responseCode = "200", description = "Employé trouvé")
    @ApiResponse(responseCode = "404", description = "Employé introuvable", content = @Content)
    public Employee findById(@PathVariable Long id) { return employeeService.findById(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Créer un employé")
    @ApiResponse(responseCode = "201", description = "Employé créé")
    @ApiResponse(responseCode = "400", description = "Nom vide", content = @Content)
    public Employee create(@RequestBody Employee employee) { return employeeService.create(employee); }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier un employé")
    @ApiResponse(responseCode = "200", description = "Employé modifié")
    @ApiResponse(responseCode = "404", description = "Employé introuvable", content = @Content)
    public Employee update(@PathVariable Long id, @RequestBody Employee employee) {
        return employeeService.update(id, employee);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Supprimer un employé")
    @ApiResponse(responseCode = "204", description = "Employé supprimé")
    @ApiResponse(responseCode = "404", description = "Employé introuvable", content = @Content)
    public void delete(@PathVariable Long id) { employeeService.deleteById(id); }
}