package ac.za.cput.domain;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/*
 * Employee.java
 *
 * Author: Siphosenkosi Mbala
 * Student Number: 221140700
 * Date: 22 July 2025
 */

@Entity
public class Employee {

    @Id
    private int employeeNumber;
    private String firstName;
    private String lastName;
    private String role;


    protected Employee() {
    }


    private Employee(Builder builder) {
        this.employeeNumber = builder.employeeNumber;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.role = builder.role;
    }


    public int getEmployeeNumber() {
        return employeeNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeNumber=" + employeeNumber +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", role='" + role + '\'' +
                '}';
    }


    public static class Builder {
        private int employeeNumber;
        private String firstName;
        private String lastName;
        private String role;

        public Builder setEmployeeNumber(int employeeNumber) {
            this.employeeNumber = employeeNumber;
            return this;
        }

        public Builder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder setRole(String role) {
            this.role = role;
            return this;
        }

        public Builder copy (Employee employee) {
            this.employeeNumber = employee.employeeNumber;
            this.firstName = employee.firstName;
            this.lastName = employee.lastName;
            this.role = employee.role;
            return this;
        }

        public Employee build() {
            return new Employee(this);
        }
    }
}
