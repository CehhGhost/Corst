package spring.crut.administration.controllers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.crut.administration.dto.AuthorityDTO;
import spring.crut.administration.models.Authority;
import spring.crut.administration.services.AuthoritiesService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/authorities")
public class AuthoritiesController {
    private final AuthoritiesService authoritiesService;
    private final ModelMapper modelMapper;

    @GetMapping
    ResponseEntity<?> getAllAuthorities() {
        List<AuthorityDTO> result = new ArrayList<>();
        var authorities = authoritiesService.getAllAuthorities();
        for (var authority : authorities) {
            result.add(modelMapper.map(authority, AuthorityDTO.class));
        }
        return ResponseEntity.ok(result);
    }
}
