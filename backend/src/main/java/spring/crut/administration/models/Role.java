package spring.crut.administration.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

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
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany
    @JoinTable(name = "_roles_authorities",
            joinColumns = @JoinColumn(name = "roles_id"),
            inverseJoinColumns = @JoinColumn(name = "authorities_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"roles_id", "authorities_id"}))
    private Set<Authority> authorities;

    @OneToMany
    private List<User> users;
}
