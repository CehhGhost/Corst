package spring.crut.administration.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import spring.crut.administration.models.Authority;
import spring.crut.administration.repositories.AuthoritiesRepository;
import spring.crut.administration.security.CrutUserDetails;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthoritiesService {
    private final AuthoritiesRepository authoritiesRepository;
    private final CrutUserDetailsService crutUserDetailsService;
    public List<Authority> getAllAuthorities() {
        return authoritiesRepository.findAll();
    }

    public List<SimpleGrantedAuthority> getAuthoritiesForUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CrutUserDetails userDetails = (CrutUserDetails) authentication.getPrincipal();
        return crutUserDetailsService.getAuthorities(userDetails.getUser());
    }
}
