package spring.crut.administration.controllers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import spring.crut.administration.dto.CreateUserDTO;
import spring.crut.administration.dto.UpdateUserDTO;
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
    public ResponseEntity<?> registerUser(@RequestBody CreateUserDTO userDTO) {
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
        return ResponseEntity.ok(usersService.getAllUsers());
    }
    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(usersService.getUserById(id));
    }
    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUserById(@PathVariable Long id, @RequestBody UpdateUserDTO userDTO) {
        usersService.updateUserById(id, userDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        usersService.deleteUserById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
