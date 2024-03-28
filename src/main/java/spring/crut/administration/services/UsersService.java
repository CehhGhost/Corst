package spring.crut.administration.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.crut.administration.models.User;
import spring.crut.administration.repositories.RolesRepository;
import spring.crut.administration.repositories.UsersRepository;

@Service
@RequiredArgsConstructor
public class UsersService {
    private final UsersRepository usersRepository;
    private final RolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;

    public void createUser(User user) {
        usersRepository.save(user);
    }

    public void setRoleForUser(Integer id, String roleName) {
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

    public void changePasswordForUser(Integer id, String oldPassword, String newPassword) {
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
}
