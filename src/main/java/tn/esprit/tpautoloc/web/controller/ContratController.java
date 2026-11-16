package tn.esprit.tpautoloc.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tpautoloc.service.IContratService;
import tn.esprit.tpautoloc.web.dto.ContratDTO;

import java.util.List;

@RestController
@RequestMapping("/contrats")
@RequiredArgsConstructor
public class ContratController {
    private final IContratService contratService;

    @GetMapping
    public ResponseEntity<List<ContratDTO>> findAll() { return ResponseEntity.ok(contratService.findAll()); }

    @GetMapping("/{id}")
    public ResponseEntity<ContratDTO> findById(@PathVariable Long id) { return ResponseEntity.ok(contratService.findById(id)); }

    @PostMapping
    public ResponseEntity<ContratDTO> create(@Valid @RequestBody ContratDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(contratService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContratDTO> update(@PathVariable Long id, @Valid @RequestBody ContratDTO dto) {
        return ResponseEntity.ok(contratService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { contratService.deleteById(id); }
}