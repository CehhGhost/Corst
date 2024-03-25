package spring.crut.context.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
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
