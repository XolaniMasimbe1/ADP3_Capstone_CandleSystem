package ac.za.cput.service;

import ac.za.cput.domain.Employee;
import ac.za.cput.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * EmployeeService.java
 * Service for Employee
 * Author: Siphosenkosi Mbala
 * Student Number: 221140700
 * Date: 26 July 2025
 */

@Service
public class EmployeeService implements IEmployeeService {

    private EmployeeRepository repository;

    @Autowired
    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Employee create(Employee employee) {
        return this.repository.save(employee);
    }

    @Override
    public Employee read(Integer employeeNumber) {
        return this.repository.findById(employeeNumber).orElse(null);
    }

    @Override
    public Employee update(Employee employee) {
        return this.repository.save(employee);
    }

    @Override
    public void delete(Integer employeeNumber) {
        this.repository.deleteById(employeeNumber);
    }

    @Override
    public List<Employee> getAll() {
        return this.repository.findAll();
    }


}
