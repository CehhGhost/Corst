package spring.crut.corpus.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "_sentences")
public class Sentence {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "text", columnDefinition = "TEXT")
    private String text;

    @Column(name = "num")
    private Integer num;

    @Column(name = "lemmatized_text", columnDefinition = "TEXT")
    private String lemmatizedText;

    @OneToMany
    @Cascade({org.hibernate.annotations.CascadeType.DELETE, org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private List<Token> tokens;

    @ManyToOne
    @JoinColumn(name = "document_id", referencedColumnName = "id", nullable = false)
    private Document document;

    @OneToMany
    @Cascade({org.hibernate.annotations.CascadeType.DELETE, org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private List<Annotation> annotations;
}
