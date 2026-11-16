package tn.esprit.tpautoloc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.tpautoloc.domain.Agence;
import tn.esprit.tpautoloc.domain.Employee;
import tn.esprit.tpautoloc.domain.Vehicule;
import tn.esprit.tpautoloc.exception.ResourceNotFoundException;
import tn.esprit.tpautoloc.repository.IAgenceRepository;
import tn.esprit.tpautoloc.repository.IEmployeeRepository;
import tn.esprit.tpautoloc.repository.IVehiculeRepository;
import tn.esprit.tpautoloc.service.IAgenceService;
import tn.esprit.tpautoloc.web.dto.AgenceDTO;
import tn.esprit.tpautoloc.web.mapper.AgenceMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AgenceServiceImpl implements IAgenceService {

    private final IAgenceRepository agenceRepository;
    private final IVehiculeRepository vehiculeRepository;
    private final IEmployeeRepository employeeRepository;
    private final AgenceMapper agenceMapper;

    @Override
    public AgenceDTO create(AgenceDTO dto) {
        if (dto.getId() != null) throw new IllegalArgumentException("Un nouveau agence ne doit pas avoir d'identifiant");
        if (dto.getNom() == null || dto.getNom().isBlank()) throw new IllegalArgumentException("Le nom de l'agence est obligatoire");
        Agence saved = agenceRepository.save(agenceMapper.toEntity(dto));
        return agenceMapper.toDto(saved);
    }

    @Override
    public AgenceDTO findById(Long id) {
        Agence agence = agenceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Agence", id));
        return agenceMapper.toDto(agence);
    }

    @Override
    public List<AgenceDTO> findAll() {
        return agenceRepository.findAll().stream().map(agenceMapper::toDto).toList();
    }

    @Override
    public AgenceDTO update(Long id, AgenceDTO dto) {
        Agence existant = agenceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Agence", id));
        if (dto.getNom() == null || dto.getNom().isBlank()) throw new IllegalArgumentException("Le nom de l'agence est obligatoire");
        existant.setNom(dto.getNom());
        existant.setVille(dto.getVille());
        existant.setAdresse(dto.getAdresse());
        existant.setTelephone(dto.getTelephone());
        return agenceMapper.toDto(agenceRepository.save(existant));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Agence agence = agenceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Agence", id));
        if (agence.getVehicules() != null) {
            for (Vehicule v : agence.getVehicules()) {
                v.setAgence(null);
                vehiculeRepository.save(v);
            }
        }
        if (agence.getEmployees() != null) {
            for (Employee e : agence.getEmployees()) {
                e.setAgence(null);
                employeeRepository.save(e);
            }
        }
        agenceRepository.delete(agence);
    }
}