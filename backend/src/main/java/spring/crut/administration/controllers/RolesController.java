package spring.crut.administration.controllers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@PreAuthorize("hasAuthority('CREATE_UPDATE_DELETE_ROLES')")
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
    @GetMapping("/{id}")
    public ResponseEntity<?> getRoleById(@PathVariable Long id) {
        var role = rolesService.getRoleById(id);
        return ResponseEntity.ok(modelMapper.map(role, RoleDTO.class));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateRoleById(@PathVariable Long id, @RequestBody CreateUpdateRoleDTO roleDTO) {
        rolesService.updateRoleById(id, roleDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @PostMapping("/create")
    public ResponseEntity<HttpStatus> createRole(@RequestBody CreateUpdateRoleDTO roleDTO) {
        rolesService.createRole(roleDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteRole(@PathVariable Long id) {
        rolesService.deleteRole(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
