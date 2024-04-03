package spring.crut.corpus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubcorpusDataDTO {
    private List<String> genres;
    private List<String> domain;
    private List<String> authorsGenders;
    private List<String> authorsCourses;
    private List<String> authorsAcademicMajors;
    private Integer periodFrom;
    private Integer periodTo;
}
