package tn.esprit.tpautoloc.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tpautoloc.service.IClientService;
import tn.esprit.tpautoloc.web.dto.ClientDTO;

import java.util.List;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {
    private final IClientService clientService;

    @GetMapping
    public ResponseEntity<List<ClientDTO>> findAll() { return ResponseEntity.ok(clientService.findAll()); }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> findById(@PathVariable Long id) { return ResponseEntity.ok(clientService.findById(id)); }

    @PostMapping
    public ResponseEntity<ClientDTO> create(@Valid @RequestBody ClientDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDTO> update(@PathVariable Long id, @Valid @RequestBody ClientDTO dto) {
        return ResponseEntity.ok(clientService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { clientService.deleteById(id); }
}