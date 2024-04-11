package spring.crut.administration.services;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.crut.administration.dto.CreateUserDTO;
import spring.crut.administration.dto.UpdateUserDTO;
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
    private final ModelMapper modelMapper;

    @Transactional
    public void createUser(CreateUserDTO userDTO) {
        var user = modelMapper.map(userDTO, User.class);
        if (usersRepository.existsUserByUsername(userDTO.getUsername())) {
            throw new IllegalArgumentException("This username is already existed");
        }
        usersRepository.save(user);
        var role = rolesRepository.findByName(userDTO.getUsersRole());
        if (role.isEmpty()) {
            throw new IllegalArgumentException("There is no such role");
        }
        if (user.getRole() != null && user.getRole().equals(role.get())) {
            return;
        }
        user.setRole(role.get());
        role.get().getUsers().add(user);
        usersRepository.save(user);
        rolesRepository.save(role.get());
    }
    @Transactional
    public void setRoleForUserById(Long id, String roleName) {
        var role = rolesRepository.findByName(roleName);
        if (role.isEmpty()) {
            throw new IllegalArgumentException("There is no such role");
        }
        var user = usersRepository.findById(id);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        var current_role = user.get().getRole();
        if (current_role != null) {
            if (current_role.equals(role.get())) {
                return;
            }
            current_role.getUsers().remove(user.get());
        }
        user.get().setRole(role.get());
        role.get().getUsers().add(user.get());
        usersRepository.save(user.get());
        rolesRepository.save(role.get());
    }

    @Transactional
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

    public void updateUserById(Long id, UpdateUserDTO userDTO) {
        if (!usersRepository.existsById(id)) {
            throw new IllegalArgumentException("No user with such id!");
        }
        if (usersRepository.existsUserByUsername(userDTO.getUsername())) {
            throw new IllegalArgumentException("This username is already existed");
        }
        this.setRoleForUserById(id, userDTO.getUsersRole());
        var user = usersRepository.getById(id);
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setUsername(userDTO.getUsername());
        usersRepository.save(user);
    }
}
