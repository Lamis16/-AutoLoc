package tn.esprit.tpautoloc.service;

import tn.esprit.tpautoloc.web.dto.MaintenanceDTO;
import java.util.List;

public interface IMaintenanceService {
    MaintenanceDTO create(MaintenanceDTO dto);
    MaintenanceDTO findById(Long id);
    List<MaintenanceDTO> findAll();
    MaintenanceDTO update(Long id, MaintenanceDTO dto);
    void deleteById(Long id);
}