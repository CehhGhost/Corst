package spring.crut.corpus.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.crut.administration.models.User;
import spring.crut.corpus.models.info.ErrorTag;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "_annotations")
public class Annotation {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "annotation_info", columnDefinition = "TEXT")
    String annotationInfo;

    @Column(name = "string_id")
    String stringId;

    @ManyToMany
    @JoinTable(name = "_error_tags_annotations",
            joinColumns = @JoinColumn(name = "annotation_id"),
            inverseJoinColumns = @JoinColumn(name = "error_tag_id"))
    private List<ErrorTag> errorTags;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "sentence_id", referencedColumnName = "id", nullable = false)
    private Sentence sentence;
}
