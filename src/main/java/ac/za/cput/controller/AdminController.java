package ac.za.cput.controller;

import ac.za.cput.domain.Admin;
import ac.za.cput.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminService service;

    @Autowired
    public AdminController(AdminService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<Admin> create(@RequestBody Admin admin) {
        Admin createdAdmin = service.create(admin);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAdmin);
    }

    @GetMapping("/read/{adminId}")
    public ResponseEntity<Admin> read(@PathVariable String adminId) {
        Admin admin = service.read(adminId);
        if (admin != null) {
            return ResponseEntity.ok(admin);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/update")
    public ResponseEntity<Admin> update(@RequestBody Admin admin) {
        Admin updatedAdmin = service.update(admin);
        if (updatedAdmin != null) {
            return ResponseEntity.ok(updatedAdmin);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{adminId}")
    public ResponseEntity<Void> delete(@PathVariable String adminId) {
        boolean deleted = service.delete(adminId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/find/{username}")
    public ResponseEntity<Admin> findByUsername(@PathVariable String username) {
        Optional<Admin> admin = service.findByUsername(username);
        return admin.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<Admin>> getAll() {
        List<Admin> allAdmins = service.getAll();
        return ResponseEntity.ok(allAdmins);
    }
}
