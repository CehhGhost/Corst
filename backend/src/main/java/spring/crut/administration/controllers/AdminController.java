package spring.crut.administration.controllers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import spring.crut.administration.dto.CreateUpdateUserDTO;
import spring.crut.administration.dto.UserDTO;
import spring.crut.administration.services.UsersService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//    @GetMapping
//    @GetMapping("/{id}")
//    @PostMapping
//    @DeleteMapping
//    @PutMapping

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final UsersService usersService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody CreateUpdateUserDTO userDTO) {
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        usersService.createUser(userDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @PatchMapping("/set_role/{id}")
    public ResponseEntity<HttpStatus> setRoleForUser(@PathVariable Long id, @RequestBody Map<String, String> requestBody) {
        usersService.setRoleForUserById(id, requestBody.get("role"));
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        List<UserDTO> usersDTOs = new ArrayList<>();
        for (var user : usersService.getAllUsers()) {
            var userDTO = modelMapper.map(user, UserDTO.class);
            userDTO.setUsersRole(user.getRolesName());
            usersDTOs.add(userDTO);
        }
        return ResponseEntity.ok(usersDTOs);
    }
    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        var user = usersService.getUserById(id);
        var userDTO = modelMapper.map(user, UserDTO.class);
        userDTO.setUsersRole(user.getRolesName());
        return ResponseEntity.ok(userDTO);
    }
}
