package tn.esprit.tpautoloc.service;

import tn.esprit.tpautoloc.web.dto.ReservationRequestDTO;
import tn.esprit.tpautoloc.web.dto.ReservationResponseDTO;
import java.util.List;

public interface IReservationService {
    ReservationResponseDTO create(ReservationRequestDTO requestDTO);
    ReservationResponseDTO findById(Long id);
    List<ReservationResponseDTO> findAll();
    ReservationResponseDTO update(Long id, ReservationRequestDTO requestDTO);
    void deleteById(Long id);
}