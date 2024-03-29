package spring.crut.content.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDTO {
    private String headerRus;
    private String headerEng;
    private String textRus;
    private String textEng;
    private Date date;
}