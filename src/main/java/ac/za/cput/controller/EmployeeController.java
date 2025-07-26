package ac.za.cput.controller;

import ac.za.cput.domain.Employee;
import ac.za.cput.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/create")
    public Employee create(@RequestBody Employee employee) {
        return employeeService.create(employee);
    }

    @GetMapping("/read/{employeeNumber}")
    public ResponseEntity<Employee> read(@PathVariable Integer employeeNumber) {
        Optional<Employee> employee = Optional.ofNullable(employeeService.read(employeeNumber));
        return employee.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/update")
    public Employee update(@RequestBody Employee employee) {
        return employeeService.update(employee);
    }

    @GetMapping("/all")
    public List<Employee> getAll() {
        return employeeService.getAll();
    }

    @DeleteMapping("/delete/{employeeNumber}")
    public ResponseEntity<Void> delete(@PathVariable Integer employeeNumber) {
        employeeService.delete(employeeNumber);
        return ResponseEntity.noContent().build();
    }
}

