package tn.esprit.tpautoloc.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tpautoloc.service.IEquipementService;
import tn.esprit.tpautoloc.web.dto.EquipementDTO;

import java.util.List;

@RestController
@RequestMapping("/equipements")
@RequiredArgsConstructor
public class EquipementController {
    private final IEquipementService equipementService;

    @GetMapping
    public ResponseEntity<List<EquipementDTO>> findAll() { return ResponseEntity.ok(equipementService.findAll()); }

    @GetMapping("/{id}")
    public ResponseEntity<EquipementDTO> findById(@PathVariable Long id) { return ResponseEntity.ok(equipementService.findById(id)); }

    @PostMapping
    public ResponseEntity<EquipementDTO> create(@Valid @RequestBody EquipementDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(equipementService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EquipementDTO> update(@PathVariable Long id, @Valid @RequestBody EquipementDTO dto) {
        return ResponseEntity.ok(equipementService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { equipementService.deleteById(id); }
}