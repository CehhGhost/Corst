package spring.crut.administration.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthInfoDTO {
    private String name;
    private String surname;
    private List<SimpleGrantedAuthority> authorities;
}
