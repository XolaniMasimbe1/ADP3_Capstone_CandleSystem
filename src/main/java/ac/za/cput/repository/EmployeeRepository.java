package ac.za.cput.repository;

import ac.za.cput.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
 * EmployeeRepository.java
 * Repository Interface for Employee
 * Author: Siphosenkosi Mbala
 * Student Number: 221140700
 * Date: 26 July 2025
 */


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}

