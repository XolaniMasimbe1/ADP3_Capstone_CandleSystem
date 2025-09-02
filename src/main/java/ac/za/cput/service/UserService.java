package ac.za.cput.service;

import ac.za.cput.domain.User;
import ac.za.cput.repository.UserRepository;
import ac.za.cput.service.Imp.IUserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {
    private final UserRepository repository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public User create(User user) {
        return repository.save(user);
    }
    @Override
    public User read(String userId) { return this.repository.findById(userId).orElse(null); }

    @Override
    public User update(User user) {
        return this.repository.save(user);
    }

    @Override
    public List<User> getAll() { return this.repository.findAll(); }

    @Override
    public Optional<User> findByUsername(String username) {
        return this.repository.findByUsername(username);
    }
}