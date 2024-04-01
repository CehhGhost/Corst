package spring.crut.content.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDTO {
    private String id;
    private String textRus;
    private String textEng;
    private String date;
}
