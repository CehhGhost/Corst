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
    private final CrutUserDetailsService crutUserDetailsService;
    public List<Authority> getAllAuthorities() {
        return authoritiesRepository.findAll();
    }

    public AuthInfoDTO getAuthInfo() {
        AuthInfoDTO authInfoDTO = new AuthInfoDTO();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CrutUserDetails userDetails = (CrutUserDetails) authentication.getPrincipal();
        authInfoDTO.setAuthorities(crutUserDetailsService.getAuthorities(userDetails.getUser()));
        authInfoDTO.setName(userDetails.getUser().getName());
        authInfoDTO.setSurname(userDetails.getUser().getSurname());
        return authInfoDTO;
    }
}
