package spring.crut.administration.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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

    public void setAuthorities(Integer id, List<String> authorities) {
        var role = rolesRepository.findById(id);
        if (role.isEmpty()) {
            throw new IllegalArgumentException("There is no such role");
        }
        for (var authority : authorities) {
            var actualAuthority = authoritiesRepository.findByName(authority);
            if (actualAuthority.isEmpty()) {
                throw new IllegalArgumentException("There is no such authority");
            }
            role.get().getAuthorities().add(actualAuthority.get());
        }
        rolesRepository.save(role.get());
    }

    public void createRole(RoleDTO roleDTO) {
        if (rolesRepository.findByName(roleDTO.getName()).isPresent()) {
            throw new IllegalArgumentException("This role is already existing");
        }
        Role role = new Role();
        role.setName(roleDTO.getName());
        List<Authority> authorities = new ArrayList<>();
        for (var authority : roleDTO.getAuthorities()) {
            var actualAuthority = authoritiesRepository.findByName(authority);
            if (actualAuthority.isEmpty()) {
                throw new IllegalArgumentException("There is no such authority");
            }
            authorities.add(actualAuthority.get());
        }
        role.setAuthorities(authorities);
        rolesRepository.save(role);
    }
}
