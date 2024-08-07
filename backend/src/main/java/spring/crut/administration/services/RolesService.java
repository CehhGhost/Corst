package spring.crut.administration.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.crut.administration.dto.CreateUpdateRoleDTO;
import spring.crut.administration.dto.RoleDTO;
import spring.crut.administration.models.Authority;
import spring.crut.administration.models.Role;
import spring.crut.administration.repositories.AuthoritiesRepository;
import spring.crut.administration.repositories.RolesRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RolesService {
    private final RolesRepository rolesRepository;
    private final AuthoritiesService authoritiesService;

    @Transactional
    public void setAuthorities(Long id, List<Authority> authorities) {
        var role = rolesRepository.findById(id);
        if (role.isEmpty()) {
            throw new IllegalArgumentException("There is no such role");
        }
        for (var authority : authorities) {
            var actualAuthority = authoritiesService.getByName(authority.getName());
            role.get().getAuthorities().add(actualAuthority);
        }
        rolesRepository.save(role.get());
    }

    @Transactional
    public void createRole(CreateUpdateRoleDTO roleDTO) {
        if (rolesRepository.findByName(roleDTO.getName()).isPresent()) {
            throw new IllegalArgumentException("This role is already existing");
        }
        Role role = new Role();
        role.setName(roleDTO.getName());
        Set<Authority> authorities = new HashSet<>();
        for (var authority : roleDTO.getAuthorities()) {
            var actualAuthority = authoritiesService.getByName(authority);
            authorities.add(actualAuthority);
        }
        role.setAuthorities(authorities);
        rolesRepository.save(role);
    }

    @Transactional
    public void deleteRole(Long id) {
        var role = rolesRepository.findById(id);
        if (role.isEmpty()) {
            throw new IllegalArgumentException("There is no such role");
        }
        for (var user : role.get().getUsers()) {
            user.setRole(null);
        }
        rolesRepository.delete(role.get());
    }

    @Transactional
    public List<Role> getAllRoles() {
        return rolesRepository.findAll();
    }

    @Transactional
    public Role getRoleWithAuthorities(Long id) {
        return rolesRepository.findByIdWithAuthorities(id).orElseThrow(() -> new RuntimeException("Role not found"));
    }

    @Transactional
    public void updateRoleById(Long id, CreateUpdateRoleDTO roleDTO) {
        var role = rolesRepository.findById(id);
        if (role.isEmpty()) {
            throw new IllegalArgumentException("There is no such role");
        }
        var checkRole = rolesRepository.findByName(roleDTO.getName());
        if (checkRole.isPresent() && !checkRole.get().getId().equals(role.get().getId())) {
            throw new IllegalArgumentException("There is already a role with such name!");
        }
        role.get().setName(roleDTO.getName());
        Set<Authority> authorities = new HashSet<>();
        for (var authority : roleDTO.getAuthorities()) {
            var actualAuthority = authoritiesService.getByName(authority);
            authorities.add(actualAuthority);
        }
        role.get().setAuthorities(authorities);
        rolesRepository.save(role.get());
    }

    @Transactional
    public void createDefaultRoles() {
        if (rolesRepository.findByName("ROLE_ADMIN").isEmpty()) {
            var admin = new Role();
            admin.setName("ROLE_ADMIN");
            Set<Authority> authorities = new HashSet<>();
            for (var authority : authoritiesService.getAllAuthoritiesNames()) {
                var actualAuthority = authoritiesService.getByName(authority);
                authorities.add(actualAuthority);
            }
            admin.setAuthorities(authorities);
            rolesRepository.save(admin);
        }
        if (rolesRepository.findByName("ROLE_DEFAULT_USER").isEmpty()) {
            var defaultUser = new Role();
            defaultUser.setName("ROLE_DEFAULT_USER");
            defaultUser.setAuthorities(new HashSet<>());
            rolesRepository.save(defaultUser);
        }
    }

    public String getRolesName(Role usersRole) {
        if (usersRole == null) {
            return "NO_ROLES";
        }
        return usersRole.getName();
    }

    public Role getRoleById(Long id) {
        if (!rolesRepository.existsById(id)) {
            throw new IllegalArgumentException("No roles with such id!");
        }
        return rolesRepository.getById(id);
    }

    public Role getRoleByName(String roleName) {
        var role = rolesRepository.findByName(roleName);
        if (role.isEmpty()) {
            throw new IllegalArgumentException("No roles with such name!");
        }
        return role.get();
    }
}
