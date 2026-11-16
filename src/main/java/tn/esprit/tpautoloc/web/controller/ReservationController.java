package tn.esprit.tpautoloc.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tpautoloc.service.IReservationService;
import tn.esprit.tpautoloc.web.dto.ReservationRequestDTO;
import tn.esprit.tpautoloc.web.dto.ReservationResponseDTO;
import tn.esprit.tpautoloc.web.validator.ReservationRequestValidator;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final IReservationService reservationService;
    private final ReservationRequestValidator reservationRequestValidator;

    @GetMapping
    public ResponseEntity<List<ReservationResponseDTO>> findAll() {
        return ResponseEntity.ok(reservationService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ReservationRequestDTO dto, BindingResult result) {
        // Appel explicite du validateur personnalisé
        reservationRequestValidator.validate(dto, result);

        if (result.hasErrors()) {
            List<String> erreurs = result.getFieldErrors().stream()
                    .map(error -> error.getField() + " : " + error.getDefaultMessage())
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(erreurs);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(reservationService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservationResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody ReservationRequestDTO dto) {
        // Pour le PUT, on peut se contenter du @Valid (ou ajouter le BindingResult si on veut)
        return ResponseEntity.ok(reservationService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        reservationService.deleteById(id);
    }
}