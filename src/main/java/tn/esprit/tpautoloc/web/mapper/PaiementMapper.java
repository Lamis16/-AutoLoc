package tn.esprit.tpautoloc.web.mapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tn.esprit.tpautoloc.domain.Paiement;
import tn.esprit.tpautoloc.web.dto.PaiementDTO;

@Mapper(componentModel = "spring")
public interface PaiementMapper {
    @Mapping(source = "contrat.idContrat", target = "contratId")
    PaiementDTO toDto(Paiement paiement);

    @Mapping(target = "contrat", ignore = true)
    Paiement toEntity(PaiementDTO dto);
}