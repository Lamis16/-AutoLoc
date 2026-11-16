package tn.esprit.tpautoloc.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tpautoloc.service.IEmployeeService;
import tn.esprit.tpautoloc.web.dto.EmployeeDTO;

import java.util.List;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final IEmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> findAll() { return ResponseEntity.ok(employeeService.findAll()); }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> findById(@PathVariable Long id) { return ResponseEntity.ok(employeeService.findById(id)); }

    @PostMapping
    public ResponseEntity<EmployeeDTO> create(@Valid @RequestBody EmployeeDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> update(@PathVariable Long id, @Valid @RequestBody EmployeeDTO dto) {
        return ResponseEntity.ok(employeeService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { employeeService.deleteById(id); }

    @PutMapping("/{employeeId}/agence/{agenceId}")
    public void affecter(@PathVariable Long employeeId, @PathVariable Long agenceId) {
        employeeService.affecterEmployeeAAgence(employeeId, agenceId);
    }

    @PutMapping("/{employeeId}/desaffecter")
    public void desaffecter(@PathVariable Long employeeId) {
        employeeService.desaffecterEmployeeDeAgence(employeeId);
    }
}