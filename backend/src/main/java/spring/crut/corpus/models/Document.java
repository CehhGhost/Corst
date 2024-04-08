package spring.crut.corpus.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import spring.crut.administration.models.User;
import spring.crut.corpus.dto.SubcorpusDataDTO;
import spring.crut.corpus.enums.Gender;
import spring.crut.corpus.enums.Status;
import spring.crut.corpus.models.info.AcademicMajor;
import spring.crut.corpus.models.info.Course;
import spring.crut.corpus.models.info.Domain;
import spring.crut.corpus.models.info.Genre;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;
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
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "text", columnDefinition = "TEXT")
    private String text;

    @ManyToOne
    @JoinColumn(name = "genre_id", referencedColumnName = "id")
    private Genre genre;

    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "domain_id", referencedColumnName = "id")
    private Domain domain;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "authors_gender")
    @Enumerated(EnumType.ORDINAL)
    private Gender authorsGender;

    @ManyToOne
    @JoinColumn(name = "authors_course_id", referencedColumnName = "id")
    private Course authorsCourse;

    @ManyToOne
    @JoinColumn(name = "authors_academ_maj_id", referencedColumnName = "id")
    private AcademicMajor authorsAcademicMajor;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id", nullable = false)
    private User owner;

    @OneToMany
    @Cascade({org.hibernate.annotations.CascadeType.DELETE, org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private List<Sentence> sentences;
}
