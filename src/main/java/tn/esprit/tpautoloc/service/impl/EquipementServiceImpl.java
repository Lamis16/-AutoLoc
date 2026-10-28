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

import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipementServiceImpl implements IEquipementService {

    private final IEquipementRepository equipementRepository;
    private final IVehiculeRepository vehiculeRepository;

    @Override
    public Equipement create(Equipement e) {
        if (e.getIdEquipement() != null) throw new IllegalArgumentException("Un nouvel équipement ne doit pas avoir d'identifiant");
        if (e.getLibelle() == null || e.getLibelle().isBlank()) throw new IllegalArgumentException("Le libellé est obligatoire");
        return equipementRepository.save(e);
    }

    @Override
    public Equipement findById(Long id) {
        return equipementRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Equipement", id));
    }

    @Override
    public List<Equipement> findAll() { return equipementRepository.findAll(); }

    @Override
    public Equipement update(Long id, Equipement e) {
        Equipement existant = findById(id);
        existant.setLibelle(e.getLibelle());
        return equipementRepository.save(existant);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Equipement e = findById(id);
        // Retirer cet équipement de tous les véhicules qui le possèdent
        if (e.getVehicules() != null) {
            for (Vehicule v : e.getVehicules()) {
                v.getEquipements().remove(e);
                vehiculeRepository.save(v);
            }
        }
        equipementRepository.delete(e);
    }
}