package tn.esprit.tpautoloc.web.mapper;
import org.mapstruct.Mapper;
import tn.esprit.tpautoloc.domain.Equipement;
import tn.esprit.tpautoloc.web.dto.EquipementDTO;

@Mapper(componentModel = "spring")
public interface EquipementMapper {
    EquipementDTO toDto(Equipement equipement);
    Equipement toEntity(EquipementDTO dto);
}