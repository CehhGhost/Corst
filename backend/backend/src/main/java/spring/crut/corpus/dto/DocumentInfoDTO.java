package spring.crut.corpus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.crut.corpus.models.info.AcademicMajor;
import spring.crut.corpus.models.info.Course;
import spring.crut.corpus.models.info.Domain;
import spring.crut.corpus.models.info.Genre;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentInfoDTO {
    private List<Genre> genres;
    private List<Domain> domains;
    private List<Course> courses;
    private List<AcademicMajor> academicMajors;
}
