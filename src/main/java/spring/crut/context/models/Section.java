package spring.crut.context.models;

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
    private Integer id;

    @Column(name = "number")
    private Integer number;

    @Column(name = "header_rus")
    private String headerRus;

    @Column(name = "header_eng")
    private String headerEng;

    @Column(name = "text_rus")
    private String textRus;

    @Column(name = "text_eng")
    private String textEng;
}
