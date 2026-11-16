package tn.esprit.tpautoloc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.tpautoloc.domain.Equipement;
import tn.esprit.tpautoloc.domain.Vehicule;
import tn.esprit.tpautoloc.exception.ResourceNotFoundException;
import tn.esprit.tpautoloc.repository.IEquipementRepository;
import tn.esprit.tpautoloc.repository.IVehiculeRepository;
import tn.esprit.tpautoloc.service.IEquipementService;
import tn.esprit.tpautoloc.web.dto.EquipementDTO;
import tn.esprit.tpautoloc.web.mapper.EquipementMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipementServiceImpl implements IEquipementService {

    private final IEquipementRepository equipementRepository;
    private final IVehiculeRepository vehiculeRepository;
    private final EquipementMapper equipementMapper;

    @Override
    public EquipementDTO create(EquipementDTO dto) {
        if (dto.getId() != null) throw new IllegalArgumentException("Un nouvel équipement ne doit pas avoir d'identifiant");
        if (dto.getLibelle() == null || dto.getLibelle().isBlank()) throw new IllegalArgumentException("Le libellé est obligatoire");
        return equipementMapper.toDto(equipementRepository.save(equipementMapper.toEntity(dto)));
    }

    @Override
    public EquipementDTO findById(Long id) {
        return equipementMapper.toDto(equipementRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Equipement", id)));
    }

    @Override
    public List<EquipementDTO> findAll() {
        return equipementRepository.findAll().stream().map(equipementMapper::toDto).toList();
    }

    @Override
    public EquipementDTO update(Long id, EquipementDTO dto) {
        Equipement existant = equipementRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Equipement", id));
        existant.setLibelle(dto.getLibelle());
        return equipementMapper.toDto(equipementRepository.save(existant));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Equipement e = equipementRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Equipement", id));
        if (e.getVehicules() != null) {
            for (Vehicule v : e.getVehicules()) {
                v.getEquipements().remove(e);
                vehiculeRepository.save(v);
            }
        }
        equipementRepository.delete(e);
    }
}