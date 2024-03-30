package spring.crut.corpus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentDTO {
    private String title;
    private String text;
    private String genre;
    private String domain;
    private String authorsGender;
    private String authorsCourse;
    private String authorsAcademicMajor;
    private Timestamp createdAt;
    private Integer status;
    private String ownerUsername;
}
