package tn.esprit.tpautoloc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.tpautoloc.domain.Agence;
import tn.esprit.tpautoloc.domain.Maintenance;
import tn.esprit.tpautoloc.domain.Reservation;
import tn.esprit.tpautoloc.domain.Vehicule;
import tn.esprit.tpautoloc.exception.ResourceNotFoundException;
import tn.esprit.tpautoloc.repository.IAgenceRepository;
import tn.esprit.tpautoloc.repository.IMaintenanceRepository;
import tn.esprit.tpautoloc.repository.IReservationRepository;
import tn.esprit.tpautoloc.repository.IVehiculeRepository;
import tn.esprit.tpautoloc.service.IVehiculeService;
import tn.esprit.tpautoloc.web.dto.VehiculeDTO;
import tn.esprit.tpautoloc.web.mapper.VehiculeMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehiculeServiceImpl implements IVehiculeService {

    private final IVehiculeRepository vehiculeRepository;
    private final IReservationRepository reservationRepository;
    private final IMaintenanceRepository maintenanceRepository;
    private final IAgenceRepository agenceRepository;
    private final VehiculeMapper vehiculeMapper;

    @Override
    public VehiculeDTO create(VehiculeDTO dto) {
        if (dto.getId() != null) throw new IllegalArgumentException("Un nouveau véhicule ne doit pas avoir d'identifiant");
        Vehicule v = vehiculeMapper.toEntity(dto);
        if (dto.getAgenceId() != null) {
            Agence a = agenceRepository.findById(dto.getAgenceId()).orElseThrow(() -> new ResourceNotFoundException("Agence", dto.getAgenceId()));
            v.setAgence(a);
        }
        return vehiculeMapper.toDto(vehiculeRepository.save(v));
    }

    @Override
    public VehiculeDTO findById(Long id) {
        return vehiculeMapper.toDto(vehiculeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Vehicule", id)));
    }

    @Override
    public List<VehiculeDTO> findAll() {
        return vehiculeRepository.findAll().stream().map(vehiculeMapper::toDto).toList();
    }

    @Override
    public VehiculeDTO update(Long id, VehiculeDTO dto) {
        Vehicule existant = vehiculeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Vehicule", id));
        existant.setImmatriculation(dto.getImmatriculation());
        existant.setMarque(dto.getMarque());
        existant.setModele(dto.getModele());
        existant.setCategorie(dto.getCategorie());
        existant.setTarifJournalier(dto.getTarifJournalier());
        existant.setStatut(dto.getStatut());
        return vehiculeMapper.toDto(vehiculeRepository.save(existant));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Vehicule v = vehiculeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Vehicule", id));
        if (v.getReservations() != null) {
            for (Reservation r : v.getReservations()) { r.setVehicule(null); reservationRepository.save(r); }
        }
        if (v.getMaintenances() != null) {
            for (Maintenance m : v.getMaintenances()) { m.setVehicule(null); maintenanceRepository.save(m); }
        }
        if (v.getEquipements() != null) { v.getEquipements().clear(); vehiculeRepository.save(v); }
        vehiculeRepository.delete(v);
    }

    @Override
    @Transactional
    public void affecterVehiculeAAgence(Long vehiculeId, Long agenceId) {
        Vehicule v = vehiculeRepository.findById(vehiculeId).orElseThrow(() -> new ResourceNotFoundException("Vehicule", vehiculeId));
        Agence a = agenceRepository.findById(agenceId).orElseThrow(() -> new ResourceNotFoundException("Agence", agenceId));
        v.setAgence(a);
        vehiculeRepository.save(v);
    }

    @Override
    @Transactional
    public void desaffecterVehiculeDeAgence(Long vehiculeId) {
        Vehicule v = vehiculeRepository.findById(vehiculeId).orElseThrow(() -> new ResourceNotFoundException("Vehicule", vehiculeId));
        if (v.getAgence() == null) throw new IllegalArgumentException("Le véhicule n'est rattaché à aucune agence.");
        v.setAgence(null);
        vehiculeRepository.save(v);
    }
}