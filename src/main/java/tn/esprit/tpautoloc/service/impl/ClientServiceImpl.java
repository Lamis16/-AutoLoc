package tn.esprit.tpautoloc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.tpautoloc.domain.Client;
import tn.esprit.tpautoloc.domain.Reservation;
import tn.esprit.tpautoloc.exception.ResourceNotFoundException;
import tn.esprit.tpautoloc.repository.IClientRepository;
import tn.esprit.tpautoloc.repository.IReservationRepository;
import tn.esprit.tpautoloc.service.IClientService;
import tn.esprit.tpautoloc.web.dto.ClientDTO;
import tn.esprit.tpautoloc.web.mapper.ClientMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements IClientService {

    private final IClientRepository clientRepository;
    private final IReservationRepository reservationRepository;
    private final ClientMapper clientMapper;

    @Override
    public ClientDTO create(ClientDTO dto) {
        if (dto.getId() != null) throw new IllegalArgumentException("Un nouveau client ne doit pas avoir d'identifiant");
        if (dto.getNom() == null || dto.getNom().isBlank()) throw new IllegalArgumentException("Le nom est obligatoire");
        if (dto.getEmail() == null || dto.getEmail().isBlank()) throw new IllegalArgumentException("L'email est obligatoire");
        Client saved = clientRepository.save(clientMapper.toEntity(dto));
        return clientMapper.toDto(saved);
    }

    @Override
    public ClientDTO findById(Long id) {
        Client c = clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Client", id));
        return clientMapper.toDto(c);
    }

    @Override
    public List<ClientDTO> findAll() {
        return clientRepository.findAll().stream().map(clientMapper::toDto).toList();
    }

    @Override
    public ClientDTO update(Long id, ClientDTO dto) {
        Client existant = clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Client", id));
        existant.setNom(dto.getNom());
        existant.setPrenom(dto.getPrenom());
        existant.setEmail(dto.getEmail());
        existant.setTelephone(dto.getTelephone());
        existant.setNumPermis(dto.getNumPermis());
        return clientMapper.toDto(clientRepository.save(existant));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Client", id));
        if (client.getReservations() != null) {
            for (Reservation r : client.getReservations()) {
                r.setClient(null);
                reservationRepository.save(r);
            }
        }
        clientRepository.delete(client);
    }
}