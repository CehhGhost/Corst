package spring.crut.administration.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import spring.crut.administration.dto.AuthInfoDTO;
import spring.crut.administration.models.Authority;
import spring.crut.administration.repositories.AuthoritiesRepository;
import spring.crut.administration.security.CrutUserDetails;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthoritiesService {
    private final AuthoritiesRepository authoritiesRepository;
    public List<Authority> getAllAuthorities() {
        return authoritiesRepository.findAll();
    }

    public Authority getByName(String authority) {
        var actualAuthority =  authoritiesRepository.findByName(authority);
        if (actualAuthority.isEmpty()) {
            throw new IllegalArgumentException("There is no such authority");
        }
        return actualAuthority.get();
    }
    public List<String> getAllAuthoritiesNames() {
        List<String> result = new ArrayList<>();
        for (var authority : authoritiesRepository.findAll()) {
            result.add(authority.getName());
        }
        return result;
    }
}
