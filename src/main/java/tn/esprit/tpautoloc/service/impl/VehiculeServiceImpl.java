package tn.esprit.tpautoloc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.tpautoloc.domain.Maintenance;
import tn.esprit.tpautoloc.domain.Reservation;
import tn.esprit.tpautoloc.domain.Vehicule;
import tn.esprit.tpautoloc.exception.ResourceNotFoundException;
import tn.esprit.tpautoloc.repository.IMaintenanceRepository;
import tn.esprit.tpautoloc.repository.IReservationRepository;
import tn.esprit.tpautoloc.repository.IVehiculeRepository;
import tn.esprit.tpautoloc.service.IVehiculeService;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VehiculeServiceImpl implements IVehiculeService {

    private final IVehiculeRepository vehiculeRepository;
    private final IReservationRepository reservationRepository;
    private final IMaintenanceRepository maintenanceRepository;

    @Override
    public Vehicule create(Vehicule vehicule) {
        if (vehicule.getIdVehicule() != null) throw new IllegalArgumentException("Un nouveau véhicule ne doit pas avoir d'identifiant");
        if (vehicule.getTarifJournalier() != null && vehicule.getTarifJournalier().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Le tarif ne peut pas être négatif");
        }
        return vehiculeRepository.save(vehicule);
    }

    @Override
    public Vehicule findById(Long id) {
        return vehiculeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Vehicule", id));
    }

    @Override
    public List<Vehicule> findAll() { return vehiculeRepository.findAll(); }

    @Override
    public Vehicule update(Long id, Vehicule vehicule) {
        Vehicule existant = findById(id);
        existant.setImmatriculation(vehicule.getImmatriculation());
        existant.setMarque(vehicule.getMarque());
        existant.setModele(vehicule.getModele());
        existant.setCategorie(vehicule.getCategorie());
        existant.setTarifJournalier(vehicule.getTarifJournalier());
        existant.setStatut(vehicule.getStatut());
        return vehiculeRepository.save(existant);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Vehicule v = findById(id);

        // Détacher les réservations
        if (v.getReservations() != null) {
            for (Reservation r : v.getReservations()) {
                r.setVehicule(null);
                reservationRepository.save(r);
            }
        }
        // Détacher les maintenances
        if (v.getMaintenances() != null) {
            for (Maintenance m : v.getMaintenances()) {
                m.setVehicule(null);
                maintenanceRepository.save(m);
            }
        }
        // Retirer les équipements de la table de jointure
        if (v.getEquipements() != null) {
            v.getEquipements().clear();
            vehiculeRepository.save(v);
        }

        vehiculeRepository.delete(v);
    }
}