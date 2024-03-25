package spring.crut.corpus.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "_tokens")
public class Token {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "text")
    private String text;

    @Column(name = "attrs")
    private String attrs;

    @Column(name = "num")
    private Integer num;

    @Column(name = "pos")
    private String pos;

    @Column(name = "lemma")
    private String lemma;

    @ManyToOne
    @JoinColumn(name = "sentence_id", referencedColumnName = "id", nullable = false)
    private Sentence sentence;

    @ManyToOne
    @JoinColumn(name = "document_id", referencedColumnName = "id", nullable = false)
    private Document document;
}
