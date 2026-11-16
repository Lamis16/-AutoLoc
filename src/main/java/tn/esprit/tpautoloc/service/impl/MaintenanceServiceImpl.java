package tn.esprit.tpautoloc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.tpautoloc.domain.Maintenance;
import tn.esprit.tpautoloc.domain.Vehicule;
import tn.esprit.tpautoloc.exception.ResourceNotFoundException;
import tn.esprit.tpautoloc.repository.IMaintenanceRepository;
import tn.esprit.tpautoloc.repository.IVehiculeRepository;
import tn.esprit.tpautoloc.service.IMaintenanceService;
import tn.esprit.tpautoloc.web.dto.MaintenanceDTO;
import tn.esprit.tpautoloc.web.mapper.MaintenanceMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaintenanceServiceImpl implements IMaintenanceService {

    private final IMaintenanceRepository maintenanceRepository;
    private final IVehiculeRepository vehiculeRepository;
    private final MaintenanceMapper maintenanceMapper;

    @Override
    public MaintenanceDTO create(MaintenanceDTO dto) {
        if (dto.getId() != null) throw new IllegalArgumentException("Une nouvelle maintenance ne doit pas avoir d'identifiant");
        Maintenance m = maintenanceMapper.toEntity(dto);
        if (dto.getVehiculeId() != null) {
            Vehicule v = vehiculeRepository.findById(dto.getVehiculeId()).orElseThrow(() -> new ResourceNotFoundException("Vehicule", dto.getVehiculeId()));
            m.setVehicule(v);
        }
        return maintenanceMapper.toDto(maintenanceRepository.save(m));
    }

    @Override
    public MaintenanceDTO findById(Long id) {
        return maintenanceMapper.toDto(maintenanceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Maintenance", id)));
    }

    @Override
    public List<MaintenanceDTO> findAll() {
        return maintenanceRepository.findAll().stream().map(maintenanceMapper::toDto).toList();
    }

    @Override
    public MaintenanceDTO update(Long id, MaintenanceDTO dto) {
        Maintenance existant = maintenanceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Maintenance", id));
        existant.setDateDebut(dto.getDateDebut());
        existant.setDateFin(dto.getDateFin());
        existant.setDescription(dto.getDescription());
        return maintenanceMapper.toDto(maintenanceRepository.save(existant));
    }

    @Override
    public void deleteById(Long id) {
        if (!maintenanceRepository.existsById(id)) throw new ResourceNotFoundException("Maintenance", id);
        maintenanceRepository.deleteById(id);
    }
}