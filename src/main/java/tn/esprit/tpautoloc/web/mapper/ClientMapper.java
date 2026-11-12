package tn.esprit.tpautoloc.web.mapper;

import org.mapstruct.Mapper;
import tn.esprit.tpautoloc.domain.Client;
import tn.esprit.tpautoloc.web.dto.ClientDTO;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    ClientDTO toDto(Client client);
    Client toEntity(ClientDTO dto);
}