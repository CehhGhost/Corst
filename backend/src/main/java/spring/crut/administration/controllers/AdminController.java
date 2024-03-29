package spring.crut.administration.controllers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import spring.crut.administration.dto.RoleDTO;
import spring.crut.administration.dto.UserDTO;
import spring.crut.administration.models.Role;
import spring.crut.administration.models.User;
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

    @PostMapping("/register")
    public ResponseEntity<HttpStatus> registerUser(@RequestBody UserDTO userDTO) {
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        var user = modelMapper.map(userDTO, User.class);
        usersService.createUser(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @PutMapping("/set_role/{id}")
    public ResponseEntity<HttpStatus> setRoleForUser(@PathVariable Integer id, @RequestBody Map<String, String> requestBody) {
        usersService.setRoleForUser(id, requestBody.get("role"));
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @PutMapping("/change_password/{id}")
    public ResponseEntity<HttpStatus> changePasswordForUser(@PathVariable Integer id, @RequestBody Map<String, String> requestBody) {
        usersService.changePasswordForUser(id, requestBody.get("oldPassword"), requestBody.get("newPassword"));
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @GetMapping("/user")
    public ResponseEntity<?> getAllUsers() {
        List<UserDTO> usersDTO = new ArrayList<>();
        for (var user : usersService.getAllUsers()) {
            usersDTO.add(modelMapper.map(user, UserDTO.class));
        }
        return ResponseEntity.ok(usersDTO);
    }
}
