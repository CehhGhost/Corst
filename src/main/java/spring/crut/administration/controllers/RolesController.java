package spring.crut.administration.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.crut.administration.dto.RoleDTO;
import spring.crut.administration.services.RolesService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/roles")
public class RolesController {
    private final RolesService rolesService;
    @PutMapping("/update/{id}")
    public ResponseEntity<HttpStatus> setAuthoritiesForRole(@PathVariable Integer id, @RequestParam("authorities") List<String> authorities) {
        rolesService.setAuthorities(id, authorities);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @PostMapping("/create")
    public ResponseEntity<HttpStatus> createRole(@RequestBody RoleDTO roleDTO) {
        rolesService.createRole(roleDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
