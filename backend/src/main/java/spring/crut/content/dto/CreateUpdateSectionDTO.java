package spring.crut.content.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUpdateSectionDTO {
    private Integer number;
    private String headerRus;
    private String headerEng;
    private String textRus;
    private String textEng;
}
