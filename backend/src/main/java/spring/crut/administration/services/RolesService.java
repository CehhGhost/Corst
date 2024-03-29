package spring.crut.administration.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.crut.administration.dto.RoleDTO;
import spring.crut.administration.models.Authority;
import spring.crut.administration.models.Role;
import spring.crut.administration.repositories.AuthoritiesRepository;
import spring.crut.administration.repositories.RolesRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RolesService {
    private final RolesRepository rolesRepository;
    private final AuthoritiesRepository authoritiesRepository;

    public void setAuthorities(Integer id, List<Authority> authorities) {
        var role = rolesRepository.findById(id);
        if (role.isEmpty()) {
            throw new IllegalArgumentException("There is no such role");
        }
        for (var authority : authorities) {
            var actualAuthority = authoritiesRepository.findByName(authority.getName());
            if (actualAuthority.isEmpty()) {
                throw new IllegalArgumentException("There is no such authority");
            }
            role.get().getAuthorities().add(actualAuthority.get());
        }
        rolesRepository.save(role.get());
    }

    public void createRole(Role role) {
        if (rolesRepository.findByName(role.getName()).isPresent()) {
            throw new IllegalArgumentException("This role is already existing");
        }
        List<Authority> authorities = new ArrayList<>();
        for (var authority : role.getAuthorities()) {
            var actualAuthority = authoritiesRepository.findByName(authority.getName());
            if (actualAuthority.isEmpty()) {
                throw new IllegalArgumentException("There is no such authority");
            }
            authorities.add(actualAuthority.get());
        }
        role.setAuthorities(authorities);
        rolesRepository.save(role);
    }

    public void deleteRole(Integer id) {
        var role = rolesRepository.findById(id);
        if (role.isEmpty()) {
            throw new IllegalArgumentException("There is no such role");
        }
        for (var user : role.get().getUsers()) {
            user.setRole(null);
        }
        rolesRepository.delete(role.get());
    }

    public List<Role> getAllRoles() {
        return rolesRepository.findAll();
    }

    @Transactional
    public Role getRoleWithAuthorities(Integer id) {
        return rolesRepository.findByIdWithAuthorities(id).orElseThrow(() -> new RuntimeException("Role not found"));
    }
}