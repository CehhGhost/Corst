package spring.crut.administration.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {
    private Long id;
    private String name;
    private List<AuthorityDTO> authorities;
}
