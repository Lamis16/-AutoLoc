package tn.esprit.tpautoloc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.tpautoloc.domain.Client;
import tn.esprit.tpautoloc.domain.Reservation;
import tn.esprit.tpautoloc.domain.Vehicule;
import tn.esprit.tpautoloc.domain.enums.StatutReservation;
import tn.esprit.tpautoloc.exception.ResourceNotFoundException;
import tn.esprit.tpautoloc.repository.IClientRepository;
import tn.esprit.tpautoloc.repository.IContratRepository;
import tn.esprit.tpautoloc.repository.IReservationRepository;
import tn.esprit.tpautoloc.repository.IVehiculeRepository;
import tn.esprit.tpautoloc.service.IReservationService;
import tn.esprit.tpautoloc.web.dto.ReservationRequestDTO;
import tn.esprit.tpautoloc.web.dto.ReservationResponseDTO;
import tn.esprit.tpautoloc.web.mapper.ReservationMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements IReservationService {

    private final IReservationRepository reservationRepository;
    private final IClientRepository clientRepository;
    private final IVehiculeRepository vehiculeRepository;
    private final IContratRepository contratRepository;
    private final ReservationMapper reservationMapper;

    @Override
    public ReservationResponseDTO create(ReservationRequestDTO dto) {
        Client client = clientRepository.findById(dto.getClientId()).orElseThrow(() -> new ResourceNotFoundException("Client", dto.getClientId()));
        Vehicule v = vehiculeRepository.findById(dto.getVehiculeId()).orElseThrow(() -> new ResourceNotFoundException("Vehicule", dto.getVehiculeId()));

        Reservation r = reservationMapper.toEntity(dto);
        r.setClient(client);
        r.setVehicule(v);
        r.setStatut(StatutReservation.EN_ATTENTE);

        return reservationMapper.toDto(reservationRepository.save(r));
    }

    @Override
    public ReservationResponseDTO findById(Long id) {
        return reservationMapper.toDto(reservationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Reservation", id)));
    }

    @Override
    public List<ReservationResponseDTO> findAll() {
        return reservationRepository.findAll().stream().map(reservationMapper::toDto).toList();
    }

    @Override
    public ReservationResponseDTO update(Long id, ReservationRequestDTO dto) {
        Reservation existant = reservationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Reservation", id));
        existant.setDateDebut(dto.getDateDebut());
        existant.setDateFin(dto.getDateFin());
        return reservationMapper.toDto(reservationRepository.save(existant));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Reservation r = reservationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Reservation", id));
        if (r.getContrat() != null) {
            r.getContrat().setReservation(null);
            contratRepository.save(r.getContrat());
        }
        reservationRepository.delete(r);
    }
}