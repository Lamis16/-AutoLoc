package tn.esprit.tpautoloc.service;

import tn.esprit.tpautoloc.web.dto.ClientDTO;
import java.util.List;

public interface IClientService {
    ClientDTO create(ClientDTO dto);
    ClientDTO findById(Long id);
    List<ClientDTO> findAll();
    ClientDTO update(Long id, ClientDTO dto);
    void deleteById(Long id);
}