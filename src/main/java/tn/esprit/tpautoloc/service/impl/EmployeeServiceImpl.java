package tn.esprit.tpautoloc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.tpautoloc.domain.Agence;
import tn.esprit.tpautoloc.domain.Employee;
import tn.esprit.tpautoloc.exception.ResourceNotFoundException;
import tn.esprit.tpautoloc.repository.IAgenceRepository;
import tn.esprit.tpautoloc.repository.IEmployeeRepository;
import tn.esprit.tpautoloc.service.IEmployeeService;
import tn.esprit.tpautoloc.web.dto.EmployeeDTO;
import tn.esprit.tpautoloc.web.mapper.EmployeeMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements IEmployeeService {

    private final IEmployeeRepository employeeRepository;
    private final IAgenceRepository agenceRepository;
    private final EmployeeMapper employeeMapper;

    @Override
    public EmployeeDTO create(EmployeeDTO dto) {
        if (dto.getId() != null) throw new IllegalArgumentException("Un nouvel employé ne doit pas avoir d'identifiant");
        if (dto.getNom() == null || dto.getNom().isBlank()) throw new IllegalArgumentException("Le nom est obligatoire");
        Employee e = employeeMapper.toEntity(dto);
        if (dto.getAgenceId() != null) {
            Agence a = agenceRepository.findById(dto.getAgenceId()).orElseThrow(() -> new ResourceNotFoundException("Agence", dto.getAgenceId()));
            e.setAgence(a);
        }
        return employeeMapper.toDto(employeeRepository.save(e));
    }

    @Override
    public EmployeeDTO findById(Long id) {
        return employeeMapper.toDto(employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee", id)));
    }

    @Override
    public List<EmployeeDTO> findAll() {
        return employeeRepository.findAll().stream().map(employeeMapper::toDto).toList();
    }

    @Override
    public EmployeeDTO update(Long id, EmployeeDTO dto) {
        Employee existant = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee", id));
        existant.setNom(dto.getNom());
        existant.setPrenom(dto.getPrenom());
        existant.setRole(dto.getRole());
        return employeeMapper.toDto(employeeRepository.save(existant));
    }

    @Override
    public void deleteById(Long id) {
        if (!employeeRepository.existsById(id)) throw new ResourceNotFoundException("Employee", id);
        employeeRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void affecterEmployeeAAgence(Long employeeId, Long agenceId) {
        Employee e = employeeRepository.findById(employeeId).orElseThrow(() -> new ResourceNotFoundException("Employee", employeeId));
        Agence a = agenceRepository.findById(agenceId).orElseThrow(() -> new ResourceNotFoundException("Agence", agenceId));
        e.setAgence(a);
        employeeRepository.save(e);
    }

    @Override
    @Transactional
    public void desaffecterEmployeeDeAgence(Long employeeId) {
        Employee e = employeeRepository.findById(employeeId).orElseThrow(() -> new ResourceNotFoundException("Employee", employeeId));
        if (e.getAgence() == null) throw new IllegalArgumentException("L'employé n'est rattaché à aucune agence.");
        e.setAgence(null);
        employeeRepository.save(e);
    }
}