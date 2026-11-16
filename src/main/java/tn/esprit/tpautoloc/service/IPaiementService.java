package tn.esprit.tpautoloc.service;

import tn.esprit.tpautoloc.web.dto.PaiementDTO;
import java.util.List;

public interface IPaiementService {
    PaiementDTO findById(Long id);
    List<PaiementDTO> findAll();
}