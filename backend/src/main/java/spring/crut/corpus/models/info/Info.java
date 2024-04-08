package spring.crut.corpus.models.info;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Info {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Override
    public String toString() {
        return name;
    }
}
