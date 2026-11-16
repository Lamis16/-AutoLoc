package tn.esprit.tpautoloc.web.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import tn.esprit.tpautoloc.web.dto.ReservationRequestDTO;

import java.time.LocalDate;
import java.time.ZoneId;

@Component
public class ReservationRequestValidator implements Validator {

    @Override
    public boolean supports(Class<?> classe) {
        return ReservationRequestDTO.class.equals(classe);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ReservationRequestDTO dto = (ReservationRequestDTO) target;

        // Si les dates sont nulles, on laisse @NotNull s'en occuper
        if (dto.getDateDebut() == null || dto.getDateFin() == null) {
            return;
        }

        // Règle 1 : La date de début doit être aujourd'hui ou dans le futur
        if (dto.getDateDebut().isBefore(LocalDate.now(ZoneId.systemDefault()))) {
            errors.rejectValue("dateDebut", "reservation.dateDebut.passe",
                    "La date de début doit être aujourd'hui ou dans le futur");
        }

        // Règle 2 : La date de fin doit être strictement postérieure à la date de début
        if (!dto.getDateFin().isAfter(dto.getDateDebut())) {
            errors.rejectValue("dateFin", "reservation.dates.invalides",
                    "La date de fin doit être postérieure à la date de début");
        }
    }
}