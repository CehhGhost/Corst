package spring.crut.administration.controllers;

import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import spring.crut.administration.dto.AuthDTO;
import spring.crut.administration.dto.JwtDTO;
import spring.crut.administration.security.JWTUtil;
import spring.crut.administration.services.AuthoritiesService;
import spring.crut.administration.services.CrutUserDetailsService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final CrutUserDetailsService crutUserDetailsService;
    private final AuthoritiesService authoritiesService;

    @PostMapping
    public ResponseEntity<?> loginUser(@RequestBody AuthDTO authDTO) {
        UsernamePasswordAuthenticationToken authInputToken = new UsernamePasswordAuthenticationToken(authDTO.getUsername(), authDTO.getPassword());
        try {
            authenticationManager.authenticate(authInputToken);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect credentials!");
        }
        String jwt = jwtUtil.generateToken(authDTO.getUsername());
        JwtDTO jwtDTO = new JwtDTO(jwt);
        return ResponseEntity.ok(jwtDTO);
    }
    @GetMapping("/check_login")
    ResponseEntity<?> checkValidationOfToken(@RequestParam(name = "jwt") String jwt) {
        try {
            var username = jwtUtil.validateTokenAndRetrieveClaim(jwt);
            crutUserDetailsService.loadUserByUsername(username);
        } catch (JWTVerificationException | UsernameNotFoundException exc) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }
    // TODO протестировать при разных исходах работы с токеном
    @GetMapping("/get_auth_info")
    ResponseEntity<?> getAuthInfo() {
        return ResponseEntity.ok(authoritiesService.getAuthInfo());
    }
}
