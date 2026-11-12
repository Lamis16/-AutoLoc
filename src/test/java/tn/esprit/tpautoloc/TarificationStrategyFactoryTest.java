package tn.esprit.tpautoloc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tn.esprit.tpautoloc.domain.Reservation;
import tn.esprit.tpautoloc.service.factory.TarificationStrategyFactory;
import tn.esprit.tpautoloc.service.strategy.*;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TarificationStrategyFactoryTest {

    private TarificationStrategyFactory factory;

    @BeforeEach
    void setUp() {
        factory = new TarificationStrategyFactory(List.of(
                new TarifStandardStrategy(),
                new TarifWeekendStrategy(),
                new TarifLongueDureeStrategy()
        ));
    }

    @Test
    void doitRetournerLongueDureePour7JoursOuPlus() {
        Reservation r = new Reservation();
        r.setDateDebut(LocalDate.of(2024, 1, 1));
        r.setDateFin(LocalDate.of(2024, 1, 8)); // 7 jours
        assertThat(factory.resoudre(r)).isInstanceOf(TarifLongueDureeStrategy.class);
        assertThat(factory.resoudre(r).getCode()).isEqualTo("LONGUE_DUREE");
    }

    @Test
    void doitRetournerWeekendPourCourtSejourVendredi() {
        Reservation r = new Reservation();
        r.setDateDebut(LocalDate.of(2024, 3, 8)); // Vendredi
        r.setDateFin(LocalDate.of(2024, 3, 10)); // 2 jours
        assertThat(factory.resoudre(r).getCode()).isEqualTo("WEEKEND");
    }

    @Test
    void doitRetournerStandardPourMoyenneDuree() {
        Reservation r = new Reservation();
        r.setDateDebut(LocalDate.of(2024, 3, 4)); // Lundi
        r.setDateFin(LocalDate.of(2024, 3, 8)); // 4 jours
        assertThat(factory.resoudre(r).getCode()).isEqualTo("STANDARD");
    }
}