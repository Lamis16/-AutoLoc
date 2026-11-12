package tn.esprit.tpautoloc.web.mapper;

import org.mapstruct.Mapper;
import tn.esprit.tpautoloc.domain.Agence;
import tn.esprit.tpautoloc.web.dto.AgenceDTO;

@Mapper(componentModel = "spring")
public interface AgenceMapper {
    AgenceDTO toDto(Agence agence);
    Agence toEntity(AgenceDTO dto);
}