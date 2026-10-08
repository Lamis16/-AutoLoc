package tn.esprit.tpautoloc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.tpautoloc.domain.Employee;

public interface IEmployeeRepository extends JpaRepository<Employee, Long> {
}