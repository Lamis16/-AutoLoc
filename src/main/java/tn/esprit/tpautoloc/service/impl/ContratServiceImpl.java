package tn.esprit.tpautoloc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.tpautoloc.domain.Contrat;
import tn.esprit.tpautoloc.domain.Reservation;
import tn.esprit.tpautoloc.exception.ResourceNotFoundException;
import tn.esprit.tpautoloc.repository.IContratRepository;
import tn.esprit.tpautoloc.repository.IReservationRepository;
import tn.esprit.tpautoloc.service.IContratService;
import tn.esprit.tpautoloc.web.dto.ContratDTO;
import tn.esprit.tpautoloc.web.mapper.ContratMapper;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContratServiceImpl implements IContratService {

    private final IContratRepository contratRepository;
    private final IReservationRepository reservationRepository;
    private final ContratMapper contratMapper;

    @Override
    public ContratDTO create(ContratDTO dto) {
        if (dto.getId() != null) throw new IllegalArgumentException("Un nouveau contrat ne doit pas avoir d'identifiant");
        if (dto.getMontantTotal() != null && dto.getMontantTotal().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Le montant total d'un contrat ne peut pas être négatif");
        }
        Contrat c = contratMapper.toEntity(dto);
        if (dto.getReservationId() != null) {
            Reservation r = reservationRepository.findById(dto.getReservationId()).orElseThrow(() -> new ResourceNotFoundException("Reservation", dto.getReservationId()));
            c.setReservation(r);
        }
        return contratMapper.toDto(contratRepository.save(c));
    }

    @Override
    public ContratDTO findById(Long id) {
        return contratMapper.toDto(contratRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Contrat", id)));
    }

    @Override
    public List<ContratDTO> findAll() {
        return contratRepository.findAll().stream().map(contratMapper::toDto).toList();
    }

    @Override
    public ContratDTO update(Long id, ContratDTO dto) {
        Contrat existant = contratRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Contrat", id));
        existant.setDateSignature(dto.getDateSignature());
        existant.setMontantTotal(dto.getMontantTotal());
        existant.setValide(dto.getValide());
        return contratMapper.toDto(contratRepository.save(existant));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Contrat contrat = contratRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Contrat", id));
        if (contrat.getReservation() != null) {
            contrat.getReservation().setContrat(null);
            reservationRepository.save(contrat.getReservation());
        }
        contratRepository.delete(contrat);
    }
}