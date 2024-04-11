package spring.crut.administration.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.crut.administration.models.Role;
import spring.crut.administration.models.User;
import spring.crut.administration.repositories.RolesRepository;
import spring.crut.administration.repositories.UsersRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsersService {
    private final UsersRepository usersRepository;
    private final RolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;

    public void createUser(User user) {
        if (usersRepository.existsUserByUsername(user.getUsername())) {
            throw new IllegalArgumentException("This username is already existed");
        }
        usersRepository.save(user);
    }

    @Transactional
    public void setRoleForUser(Long id, String roleName) {
        var role = rolesRepository.findByName(roleName);
        if (role.isEmpty()) {
            throw new IllegalArgumentException("There is no such role");
        }
        var user = usersRepository.findById(id);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        if (user.get().getRole() != null && user.get().getRole().equals(role.get())) {
            return;
        }
        user.get().setRole(role.get());
        role.get().getUsers().add(user.get());
        usersRepository.save(user.get());
        rolesRepository.save(role.get());
    }

    public void changePasswordForUser(Long id, String oldPassword, String newPassword) {
        var user = usersRepository.findById(id);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("There is no such user");
        }
        if (!user.get().getPassword().equals(passwordEncoder.encode(oldPassword))) {
            throw new IllegalArgumentException("Incorrect old password");
        }
        user.get().setPassword(passwordEncoder.encode(newPassword));
        usersRepository.save(user.get());
    }

    public List<User> getAllUsers() {
        return usersRepository.findAll();
    }

    public User getUserById(Long id) {
        var user = usersRepository.findById(id);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("No user with such id!");
        }
        return user.get();
    }
}
