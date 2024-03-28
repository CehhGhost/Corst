package spring.crut.corpus.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import spring.crut.administration.models.User;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "_documents")
public class Document {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "text")
    private String text;

    @Column(name = "genre")
    private String genre;

    @Column(name = "status")
    private Integer status;

    @Column(name = "domain")
    private String domain;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "authors_gender")
    private String authorsGender;

    @Column(name = "authors_course")
    private String authorsCourse;

    @Column(name = "authors_academ_maj")
    private String authorsAcademicMajor;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id", nullable = false)
    private User owner;

    @OneToMany
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    private List<Annotation> annotations;

    @OneToMany
    @Cascade({org.hibernate.annotations.CascadeType.DELETE, org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private List<Sentence> sentences;

    public boolean equalsSubcorpus(Document other) {
        return this.genre.equals(other.genre) && this.domain.equals(other.domain)
                && this.authorsGender.equals(other.authorsGender) && this.authorsCourse.equals(other.authorsCourse)
                && this.authorsAcademicMajor.equals(other.authorsAcademicMajor);
    }
}
