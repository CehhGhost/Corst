package spring.crut.administration.controllers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import spring.crut.administration.dto.CreateUpdateUserDTO;
import spring.crut.administration.dto.UserDTO;
import spring.crut.administration.models.User;
import spring.crut.administration.repositories.UsersRepository;
import spring.crut.administration.services.UsersService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final UsersService usersService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UsersRepository usersRepository;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody CreateUpdateUserDTO userDTO) {
        if (usersRepository.existsUserByUsername(userDTO.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This username is already existed");
        }
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        var user = modelMapper.map(userDTO, User.class);
        usersService.createUser(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @PatchMapping("/set_role/{id}")
    public ResponseEntity<HttpStatus> setRoleForUser(@PathVariable Long id, @RequestBody Map<String, String> requestBody) {
        usersService.setRoleForUser(id, requestBody.get("role"));
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        List<UserDTO> usersDTOs = new ArrayList<>();
        for (var user : usersService.getAllUsers()) {
            var userDTO = modelMapper.map(user, UserDTO.class);
            userDTO.setRole(user.getRole().getName());
            usersDTOs.add(userDTO);
        }
        return ResponseEntity.ok(usersDTOs);
    }
}
