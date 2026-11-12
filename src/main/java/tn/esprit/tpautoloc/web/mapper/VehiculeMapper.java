package tn.esprit.tpautoloc.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tn.esprit.tpautoloc.domain.Vehicule;
import tn.esprit.tpautoloc.web.dto.VehiculeDTO;

@Mapper(componentModel = "spring")
public interface VehiculeMapper {

    @Mapping(source = "agence.idAgence", target = "agenceId")
    VehiculeDTO toDto(Vehicule vehicule);

    @Mapping(target = "agence", ignore = true) // L'agence est gérée à part
    Vehicule toEntity(VehiculeDTO dto);
}