package spring.crut.corpus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LexGramTokenDTO {
    Integer from;
    Integer to;
    private String wordform;
    private List<String> partOfSpeech;
    private List<String> grammar;
    private List<String> errors;
}
