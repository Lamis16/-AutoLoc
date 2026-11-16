package tn.esprit.tpautoloc.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tpautoloc.service.IMaintenanceService;
import tn.esprit.tpautoloc.web.dto.MaintenanceDTO;

import java.util.List;

@RestController
@RequestMapping("/maintenances")
@RequiredArgsConstructor
public class MaintenanceController {
    private final IMaintenanceService maintenanceService;

    @GetMapping
    public ResponseEntity<List<MaintenanceDTO>> findAll() { return ResponseEntity.ok(maintenanceService.findAll()); }

    @GetMapping("/{id}")
    public ResponseEntity<MaintenanceDTO> findById(@PathVariable Long id) { return ResponseEntity.ok(maintenanceService.findById(id)); }

    @PostMapping
    public ResponseEntity<MaintenanceDTO> create(@Valid @RequestBody MaintenanceDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(maintenanceService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MaintenanceDTO> update(@PathVariable Long id, @Valid @RequestBody MaintenanceDTO dto) {
        return ResponseEntity.ok(maintenanceService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { maintenanceService.deleteById(id); }
}