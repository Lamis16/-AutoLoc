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

import java.util.List;

@Service
@RequiredArgsConstructor
public class AgenceServiceImpl implements IAgenceService {

    private final IAgenceRepository agenceRepository;
    private final IVehiculeRepository vehiculeRepository;
    private final IEmployeeRepository employeeRepository;

    @Override
    public Agence create(Agence agence) {
        if (agence.getIdAgence() != null) throw new IllegalArgumentException("Un nouveau agence ne doit pas avoir d'identifiant");
        if (agence.getNom() == null || agence.getNom().isBlank()) throw new IllegalArgumentException("Le nom de l'agence est obligatoire");
        return agenceRepository.save(agence);
    }

    @Override
    public Agence findById(Long id) {
        return agenceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Agence", id));
    }

    @Override
    public List<Agence> findAll() { return agenceRepository.findAll(); }

    @Override
    public Agence update(Long id, Agence agence) {
        Agence existant = findById(id);
        if (agence.getNom() == null || agence.getNom().isBlank()) throw new IllegalArgumentException("Le nom de l'agence est obligatoire");
        existant.setNom(agence.getNom());
        existant.setVille(agence.getVille());
        existant.setAdresse(agence.getAdresse());
        existant.setTelephone(agence.getTelephone());
        return agenceRepository.save(existant);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Agence agence = findById(id);

        // Détacher les véhicules
        if (agence.getVehicules() != null) {
            for (Vehicule v : agence.getVehicules()) {
                v.setAgence(null);
                vehiculeRepository.save(v);
            }
        }
        // Détacher les employés
        if (agence.getEmployees() != null) {
            for (Employee e : agence.getEmployees()) {
                e.setAgence(null);
                employeeRepository.save(e);
            }
        }
        agenceRepository.delete(agence);
    }
}