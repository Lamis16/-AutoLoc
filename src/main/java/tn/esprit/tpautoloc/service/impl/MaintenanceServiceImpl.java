package tn.esprit.tpautoloc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.tpautoloc.domain.Maintenance;
import tn.esprit.tpautoloc.exception.ResourceNotFoundException;
import tn.esprit.tpautoloc.repository.IMaintenanceRepository;
import tn.esprit.tpautoloc.service.IMaintenanceService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaintenanceServiceImpl implements IMaintenanceService {

    private final IMaintenanceRepository maintenanceRepository;

    @Override
    public Maintenance create(Maintenance m) {
        if (m.getIdMaintenance() != null) throw new IllegalArgumentException("Une nouvelle maintenance ne doit pas avoir d'identifiant");
        if (m.getDateDebut() != null && m.getDateFin() != null && m.getDateFin().isBefore(m.getDateDebut())) {
            throw new IllegalArgumentException("La date de fin ne peut pas être antérieure à la date de début");
        }
        return maintenanceRepository.save(m);
    }

    @Override
    public Maintenance findById(Long id) {
        return maintenanceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Maintenance", id));
    }

    @Override
    public List<Maintenance> findAll() { return maintenanceRepository.findAll(); }

    @Override
    public Maintenance update(Long id, Maintenance m) {
        Maintenance existant = findById(id);
        existant.setDateDebut(m.getDateDebut());
        existant.setDateFin(m.getDateFin());
        existant.setDescription(m.getDescription());
        return maintenanceRepository.save(existant);
    }

    @Override
    public void deleteById(Long id) {
        if (!maintenanceRepository.existsById(id)) throw new ResourceNotFoundException("Maintenance", id);
        maintenanceRepository.deleteById(id); // Pas de cascade, suppression simple
    }
}