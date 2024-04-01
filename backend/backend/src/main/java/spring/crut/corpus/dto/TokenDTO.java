package spring.crut.corpus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenDTO {
    private String text;
    private Integer num;
    private String pos;
    private String lemma;
    private Map<String, String> attrs;
}
