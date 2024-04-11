package spring.crut.administration.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDTO {
    private String name;
    private String surname;
    private String username;
    private String password;
    private String usersRole;
}
