package tn.esprit.tpautoloc.web.mapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tn.esprit.tpautoloc.domain.Employee;
import tn.esprit.tpautoloc.web.dto.EmployeeDTO;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    @Mapping(source = "agence.idAgence", target = "agenceId")
    EmployeeDTO toDto(Employee employee);

    @Mapping(target = "agence", ignore = true)
    Employee toEntity(EmployeeDTO dto);
}