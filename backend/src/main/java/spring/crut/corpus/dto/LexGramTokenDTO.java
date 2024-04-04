package spring.crut.corpus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LexGramTokenDTO {
    Integer from;
    Integer to;
    private String wordform;
    private String partOfSpeech;
    private Map<String, String> grammar;
    private Map<String, String> errors;
}
