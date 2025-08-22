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

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public User create(User user) {
        if (user.getRetailStore() != null) {
            user.getRetailStore().setUser(user);
        }
        return repository.save(user);
    }
    @Override
    public User read(String userId) { return this.repository.findById(userId).orElse(null); }

    @Override
    @Transactional
    public User update(User user) {
        Optional<User> existingUserOpt = repository.findById(user.getUserId());
        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();

            // Check if the username is not null before updating
            if (user.getUsername() != null) {
                existingUser.setUsername(user.getUsername());
            }

            if (user.getPasswordHash() != null) {
                existingUser.setPasswordHash(user.getPasswordHash());
            }

            if (user.getRole() != null) {
                existingUser.setRole(user.getRole());
            }

            if (user.getRetailStore() != null) {
                existingUser.setRetailStore(user.getRetailStore());
            }

            return repository.save(existingUser);
        }
        return null;
    }

    @Override
    public List<User> getAll() { return this.repository.findAll(); }

    @Override
    public Optional<User> findByUsername(String username) {
        return this.repository.findByUsername(username);
    }
}