package tn.esprit.tpautoloc.service.factory;

import org.springframework.stereotype.Component;
import tn.esprit.tpautoloc.domain.Reservation;
import tn.esprit.tpautoloc.service.strategy.ITarificationStrategy;

import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TarificationStrategyFactory {

    private final Map<String, ITarificationStrategy> strategies = new HashMap<>();

    public TarificationStrategyFactory(List<ITarificationStrategy> strategyList) {
        for (ITarificationStrategy strategy : strategyList) {
            strategies.put(strategy.getCode(), strategy);
        }
    }

    public ITarificationStrategy resoudre(Reservation reservation) {
        long jours = ChronoUnit.DAYS.between(reservation.getDateDebut(), reservation.getDateFin());

        // Si >= 7 jours -> Longue durée
        if (jours >= 7) {
            return strategies.get("LONGUE_DUREE");
        }
        // Si <= 3 jours ET commence un vendredi (ou tombe le week-end) -> Weekend
        if (jours <= 3 && estWeekend(reservation)) {
            return strategies.get("WEEKEND");
        }
        // Sinon -> Standard
        return strategies.get("STANDARD");
    }

    private boolean estWeekend(Reservation reservation) {
        // Si la date de début est un vendredi, samedi ou dimanche
        int jourSemaine = reservation.getDateDebut().getDayOfWeek().getValue();
        return jourSemaine == 5 || jourSemaine == 6 || jourSemaine == 7;
    }
}