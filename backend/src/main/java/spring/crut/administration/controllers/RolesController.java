package spring.crut.administration.controllers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.crut.administration.dto.AuthorityDTO;
import spring.crut.administration.dto.CreateUpdateRoleDTO;
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
    public ResponseEntity<?> getAllRoles() {
        List<RoleDTO> roleDTOs = new ArrayList<>();
        for (var role : rolesService.getAllRoles()) {
            roleDTOs.add(modelMapper.map(role, RoleDTO.class));
        }
        return ResponseEntity.ok(roleDTOs);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateRoleById(@PathVariable Long id, @RequestBody CreateUpdateRoleDTO roleDTO) {
        rolesService.updateRoleById(id, roleDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    public ResponseEntity<HttpStatus> setAuthoritiesForRole(@PathVariable Long id, @RequestBody List<AuthorityDTO> authoritiesDTO) {
        List<Authority> authorities = new ArrayList<>();
        for (var authorityDTO : authoritiesDTO) {
            authorities.add(modelMapper.map(authorityDTO, Authority.class));
        }
        rolesService.setAuthorities(id, authorities);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @PostMapping("/create")
    public ResponseEntity<HttpStatus> createRole(@RequestBody CreateUpdateRoleDTO roleDTO) {
        rolesService.createRole(roleDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteRole(@PathVariable Long id) {
        rolesService.deleteRole(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
