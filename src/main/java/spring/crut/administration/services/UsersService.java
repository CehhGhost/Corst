package spring.crut.administration.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import spring.crut.administration.models.User;
import spring.crut.administration.repositories.RolesRepository;
import spring.crut.administration.repositories.UsersRepository;

@Service
@RequiredArgsConstructor
public class UsersService {
    private final UsersRepository usersRepository;
    private final RolesRepository rolesRepository;

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
        user.get().setRole(role.get());
        usersRepository.save(user.get());
    }
}
