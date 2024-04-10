package spring.crut.content.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SectionDTO {
    private Long id;
    private Integer number;
    private String headerRus;
    private String headerEng;
    private String textRus;
    private String textEng;
}
