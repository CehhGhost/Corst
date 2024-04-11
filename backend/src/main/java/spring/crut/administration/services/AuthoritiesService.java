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

import java.util.List;

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
}
