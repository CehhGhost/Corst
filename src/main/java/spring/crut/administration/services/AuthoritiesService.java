package spring.crut.administration.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.crut.administration.models.Authority;
import spring.crut.administration.repositories.AuthoritiesRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthoritiesService {
    private final AuthoritiesRepository authoritiesRepository;
    public List<Authority> getAllAuthorities() {
        return authoritiesRepository.findAll();
    }
}
