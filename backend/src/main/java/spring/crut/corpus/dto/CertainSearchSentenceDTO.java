package spring.crut.corpus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CertainSearchSentenceDTO {
    private String textTitle;

    private Integer id;
    private String text;
    private Integer num;
    private List<TokenDTO> tokens;
}
