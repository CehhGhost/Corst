package spring.crut.administration.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
// TODO подумать насчет неизменяемости и уникальности роли админа
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "_roles")
public class Role {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @ManyToMany
    @JoinTable(name = "_roles_authorities",
            joinColumns = @JoinColumn(name = "roles_id"),
            inverseJoinColumns = @JoinColumn(name = "authorities_id"))
    private List<Authority> authorities;

    @OneToMany
    private List<User> users;
}
