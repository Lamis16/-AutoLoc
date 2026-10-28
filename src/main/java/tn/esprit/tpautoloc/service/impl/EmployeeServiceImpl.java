package tn.esprit.tpautoloc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.tpautoloc.domain.Employee;
import tn.esprit.tpautoloc.exception.ResourceNotFoundException;
import tn.esprit.tpautoloc.repository.IEmployeeRepository;
import tn.esprit.tpautoloc.service.IEmployeeService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements IEmployeeService {

    private final IEmployeeRepository employeeRepository;

    @Override
    public Employee create(Employee e) {
        if (e.getIdEmployee() != null) throw new IllegalArgumentException("Un nouvel employé ne doit pas avoir d'identifiant");
        if (e.getNom() == null || e.getNom().isBlank()) throw new IllegalArgumentException("Le nom est obligatoire");
        return employeeRepository.save(e);
    }

    @Override
    public Employee findById(Long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee", id));
    }

    @Override
    public List<Employee> findAll() { return employeeRepository.findAll(); }

    @Override
    public Employee update(Long id, Employee e) {
        Employee existant = findById(id);
        existant.setNom(e.getNom());
        existant.setPrenom(e.getPrenom());
        existant.setRole(e.getRole());
        return employeeRepository.save(existant);
    }

    @Override
    public void deleteById(Long id) {
        if (!employeeRepository.existsById(id)) throw new ResourceNotFoundException("Employee", id);
        employeeRepository.deleteById(id);
    }
}