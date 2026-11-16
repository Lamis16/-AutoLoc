package tn.esprit.tpautoloc.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tpautoloc.service.IAgenceService;
import tn.esprit.tpautoloc.web.dto.AgenceDTO;

import java.util.List;

@RestController
@RequestMapping("/agences")
@RequiredArgsConstructor
public class AgenceController {
    private final IAgenceService agenceService;

    @GetMapping
    public ResponseEntity<List<AgenceDTO>> findAll() { return ResponseEntity.ok(agenceService.findAll()); }

    @GetMapping("/{id}")
    public ResponseEntity<AgenceDTO> findById(@PathVariable Long id) { return ResponseEntity.ok(agenceService.findById(id)); }

    @PostMapping
    public ResponseEntity<AgenceDTO> create(@Valid @RequestBody AgenceDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(agenceService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AgenceDTO> update(@PathVariable Long id, @Valid @RequestBody AgenceDTO dto) {
        return ResponseEntity.ok(agenceService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { agenceService.deleteById(id); }
}