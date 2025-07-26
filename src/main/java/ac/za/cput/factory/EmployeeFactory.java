package ac.za.cput.factory;

/*
 * EmployeeFactory.java
 * Factory for Employee
 * Author: Siphosenkosi Mbala
 * Student Number: 221140700
 * Date: 26 July 2025
 */

import ac.za.cput.domain.Employee;

public class EmployeeFactory {

    public static Employee createEmployee(int employeeNumber, String firstName, String lastName, String role) {
        if (firstName == null || firstName.trim().isEmpty())
            throw new IllegalArgumentException("First name is required");

        if (lastName == null || lastName.trim().isEmpty())
            throw new IllegalArgumentException("Last name is required");

        if (role == null || role.trim().isEmpty())
            throw new IllegalArgumentException("Role is required");

        return new Employee.Builder()
                .setEmployeeNumber(employeeNumber)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setRole(role)
                .build();
    }
}
