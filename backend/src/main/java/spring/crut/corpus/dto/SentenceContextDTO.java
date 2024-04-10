package spring.crut.corpus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SentenceContextDTO {
    private List<String> context;
    private Integer origin_num;
}
