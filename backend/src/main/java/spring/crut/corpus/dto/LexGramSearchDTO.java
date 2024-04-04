package spring.crut.corpus.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LexGramSearchDTO {
    private SubcorpusDataDTO subcorpusData;
    @JsonProperty("lexgramBlocks")
    private List<LexGramTokenDTO> lexGramTokensDTO;
}
