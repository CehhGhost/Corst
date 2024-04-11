package spring.crut.administration.services;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.crut.administration.dto.CreateUserDTO;
import spring.crut.administration.dto.UpdateUserDTO;
import spring.crut.administration.dto.UserDTO;
import spring.crut.administration.models.User;
import spring.crut.administration.repositories.RolesRepository;
import spring.crut.administration.repositories.UsersRepository;
import spring.crut.corpus.models.Document;
import spring.crut.corpus.services.DocumentsService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsersService {
    private final UsersRepository usersRepository;
    private final RolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final RolesService rolesService;
    private final DocumentsService documentsService;


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

    public List<UserDTO> getAllUsers() {
        List<UserDTO> usersDTOs = new ArrayList<>();
        for (var user : usersRepository.findAll()) {
            var userDTO = new UserDTO(user.getId(), user.getName(), user.getSurname(), user.getUsername(), rolesService.getRolesName(user.getRole()));
            usersDTOs.add(userDTO);
        }
        return usersDTOs;
    }

    public UserDTO getUserById(Long id) {
        var user = usersRepository.findById(id);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("No user with such id!");
        }
        return new UserDTO(user.get().getId(), user.get().getName(), user.get().getSurname(), user.get().getUsername(), rolesService.getRolesName(user.get().getRole()));
    }

    public void updateUserById(Long id, UpdateUserDTO userDTO) {
        if (!usersRepository.existsById(id)) {
            throw new IllegalArgumentException("No user with such id!");
        }
        var current_user = usersRepository.findByUsername(userDTO.getUsername());
        if (current_user.isPresent() && !current_user.get().getId().equals(id)) {
            throw new IllegalArgumentException("This username is already existed");
        }
        this.setRoleForUserById(id, userDTO.getUsersRole());
        var user = usersRepository.getById(id);
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setUsername(userDTO.getUsername());
        usersRepository.save(user);
    }

    // TODO доделать
    public void deleteUserById(Long id) {
        var user = usersRepository.findById(id);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("No user with such id!");
        }
        user.get().getOwningAnnotations().clear();
        user.get().getRole().getUsers().remove(user.get());
        for(var document : documentsService.getAllDocumentsByUser(user.get())) {
            document.setOwner(null);
        }
        usersRepository.delete(user.get());
    }

    @Transactional
    public void createDefaultUsers() {
        if (!usersRepository.existsUserByUsername("admin")) {
            var user = new User();
            user.setPassword(passwordEncoder.encode("12345"));
            user.setName("admin");
            user.setSurname("admin");
            user.setUsername("admin");
            usersRepository.save(user);
            var role = rolesService.getRoleByName("ROLE_ADMIN");
            user.setRole(role);
            role.getUsers().add(user);
            user.setRole(role);
        }
    }
}
