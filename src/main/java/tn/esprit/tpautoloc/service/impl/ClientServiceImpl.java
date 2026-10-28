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

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements IClientService {

    private final IClientRepository clientRepository;
    private final IReservationRepository reservationRepository;

    @Override
    public Client create(Client client) {
        if (client.getIdClient() != null) throw new IllegalArgumentException("Un nouveau client ne doit pas avoir d'identifiant");
        if (client.getNom() == null || client.getNom().isBlank()) throw new IllegalArgumentException("Le nom est obligatoire");
        if (client.getEmail() == null || client.getEmail().isBlank()) throw new IllegalArgumentException("L'email est obligatoire");
        return clientRepository.save(client);
    }

    @Override
    public Client findById(Long id) {
        return clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Client", id));
    }

    @Override
    public List<Client> findAll() { return clientRepository.findAll(); }

    @Override
    public Client update(Long id, Client client) {
        Client existant = findById(id);
        existant.setNom(client.getNom());
        existant.setPrenom(client.getPrenom());
        existant.setEmail(client.getEmail());
        existant.setTelephone(client.getTelephone());
        existant.setNumPermis(client.getNumPermis());
        return clientRepository.save(existant);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Client client = findById(id);
        if (client.getReservations() != null) {
            for (Reservation r : client.getReservations()) {
                r.setClient(null);
                reservationRepository.save(r);
            }
        }
        clientRepository.delete(client);
    }
}