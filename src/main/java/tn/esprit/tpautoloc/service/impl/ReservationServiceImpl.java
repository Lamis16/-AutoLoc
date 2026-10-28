package tn.esprit.tpautoloc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.tpautoloc.domain.Reservation;
import tn.esprit.tpautoloc.exception.ResourceNotFoundException;
import tn.esprit.tpautoloc.repository.IContratRepository;
import tn.esprit.tpautoloc.repository.IReservationRepository;
import tn.esprit.tpautoloc.service.IReservationService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements IReservationService {

    private final IReservationRepository reservationRepository;
    private final IContratRepository contratRepository;

    @Override
    public Reservation create(Reservation r) {
        if (r.getIdReservation() != null) throw new IllegalArgumentException("Une nouvelle réservation ne doit pas avoir d'identifiant");
        if (r.getDateDebut() != null && r.getDateFin() != null && r.getDateFin().isBefore(r.getDateDebut())) {
            throw new IllegalArgumentException("La date de fin ne peut pas être antérieure à la date de début");
        }
        return reservationRepository.save(r);
    }

    @Override
    public Reservation findById(Long id) {
        return reservationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Reservation", id));
    }

    @Override
    public List<Reservation> findAll() { return reservationRepository.findAll(); }

    @Override
    public Reservation update(Long id, Reservation r) {
        Reservation existant = findById(id);
        existant.setDateDebut(r.getDateDebut());
        existant.setDateFin(r.getDateFin());
        existant.setStatut(r.getStatut());
        return reservationRepository.save(existant);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Reservation r = findById(id);
        if (r.getContrat() != null) {
            r.getContrat().setReservation(null);
            contratRepository.save(r.getContrat());
        }
        reservationRepository.delete(r);
    }
}