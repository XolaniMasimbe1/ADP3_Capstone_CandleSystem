package ac.za.cput.service;

import ac.za.cput.domain.Employee;
import java.util.List;

/*
 * IEmployeeService.java
 * Service Interface for Employee
 * Author: Siphosenkosi Mbala
 * Student Number: 221140700
 * Date: 26 July 2025
 */

public interface IEmployeeService extends IService<Employee, Integer> {
    void delete(Integer employeeNumber);

    List<Employee> getAll();
}
