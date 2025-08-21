package ac.za.cput.controller;

import ac.za.cput.domain.User;
import ac.za.cput.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<User> create(@RequestBody User user) {
        User createdUser = service.create(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/read/{userId}")
    public ResponseEntity<User> read(@PathVariable String userId) {
        User user = service.read(userId);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update") // FIX 1: Use @PutMapping for updates.
    public ResponseEntity<User> update(@RequestBody User user) {
        User updatedUser = service.update(user);
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/find/{username}")
    public Optional<User> findByUsername(@PathVariable String username) {
        return service.findByUsername(username);
    }


    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getAll() {
        List<User> users = service.getAll();
        return ResponseEntity.ok(users);
    }
}