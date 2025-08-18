package ac.za.cput.service.Imp;

import ac.za.cput.domain.User;
import ac.za.cput.service.IService;

import java.util.List;
import java.util.Optional;

public interface IUserService extends IService<User, String> {
    List<User> getAll();
    Optional<User> findByUsername(String username);
}