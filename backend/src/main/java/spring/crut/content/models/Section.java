package spring.crut.content.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "_sections")
public class Section {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number")
    private Integer number;

    @Column(name = "header_rus")
    private String headerRus;

    @Column(name = "header_eng")
    private String headerEng;

    @Column(name = "text_rus", columnDefinition = "TEXT")
    private String textRus;

    @Column(name = "text_eng", columnDefinition = "TEXT")
    private String textEng;
}
