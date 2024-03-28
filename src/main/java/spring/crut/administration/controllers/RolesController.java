package spring.crut.administration.controllers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.crut.administration.dto.AuthorityDTO;
import spring.crut.administration.dto.RoleDTO;
import spring.crut.administration.models.Authority;
import spring.crut.administration.models.Role;
import spring.crut.administration.services.RolesService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/roles")
public class RolesController {
    private final RolesService rolesService;
    private final ModelMapper modelMapper;
    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles() {
        return ResponseEntity.ok(rolesService.getAllRoles());
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<HttpStatus> setAuthoritiesForRole(@PathVariable Integer id, @RequestBody List<AuthorityDTO> authoritiesDTO) {
        List<Authority> authorities = new ArrayList<>();
        for (var authorityDTO : authoritiesDTO) {
            authorities.add(modelMapper.map(authorityDTO, Authority.class));
        }
        rolesService.setAuthorities(id, authorities);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @PostMapping("/create")
    public ResponseEntity<HttpStatus> createRole(@RequestBody RoleDTO roleDTO) {
        Role role = modelMapper.map(roleDTO, Role.class);
        rolesService.createRole(role);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteRole(@PathVariable Integer id) {
        rolesService.deleteRole(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
