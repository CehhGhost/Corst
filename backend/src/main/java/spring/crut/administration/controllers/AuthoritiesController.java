package spring.crut.administration.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.crut.administration.models.Authority;
import spring.crut.administration.services.AuthoritiesService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/authorities")
public class AuthoritiesController {
    private final AuthoritiesService authoritiesService;
    @GetMapping
    ResponseEntity<List<Authority>> getAllAuthorities() {
        return ResponseEntity.ok(authoritiesService.getAllAuthorities());
    }
}
