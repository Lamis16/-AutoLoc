package tn.esprit.tpautoloc.service.strategy;

import org.springframework.stereotype.Component;
import tn.esprit.tpautoloc.domain.Reservation;
import tn.esprit.tpautoloc.domain.Vehicule;
import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;

@Component
public class TarifWeekendStrategy implements ITarificationStrategy {
    private static final BigDecimal MAJORATION = new BigDecimal("1.20");

    @Override
    public BigDecimal calculerTarif(Vehicule vehicule, Reservation reservation) {
        long jours = ChronoUnit.DAYS.between(reservation.getDateDebut(), reservation.getDateFin());
        if (jours <= 0) jours = 1;
        return vehicule.getTarifJournalier()
                .multiply(BigDecimal.valueOf(jours))
                .multiply(MAJORATION);
    }

    @Override
    public String getCode() { return "WEEKEND"; }
}