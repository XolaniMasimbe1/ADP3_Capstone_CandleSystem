package ac.za.cput.controller;

import ac.za.cput.domain.User;
import ac.za.cput.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public User create(@RequestBody User user) {
        return service.create(user);
    }

    @GetMapping("/read/{userId}")
    public User read(@PathVariable String userId) {
        return service.read(userId);
    }

    @PostMapping("/update")
    public User update(@RequestBody User user) {
        return service.update(user);
    }

    @GetMapping("/find/{username}")
    public Optional<User> findByUsername(@PathVariable String username) {
        return service.findByUsername(username);
    }

    @GetMapping("/all")
    public List<User> getAll() {
        return service.getAll();
    }
}