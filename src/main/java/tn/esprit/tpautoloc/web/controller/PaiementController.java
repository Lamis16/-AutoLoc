package tn.esprit.tpautoloc.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tpautoloc.service.IPaiementService;
import tn.esprit.tpautoloc.web.dto.PaiementDTO;

import java.util.List;

@RestController
@RequestMapping("/paiements")
@RequiredArgsConstructor
public class PaiementController {
    private final IPaiementService paiementService;

    @GetMapping
    public ResponseEntity<List<PaiementDTO>> findAll() { return ResponseEntity.ok(paiementService.findAll()); }

    @GetMapping("/{id}")
    public ResponseEntity<PaiementDTO> findById(@PathVariable Long id) { return ResponseEntity.ok(paiementService.findById(id)); }
}