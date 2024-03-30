package spring.crut.corpus.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.crut.corpus.dto.CertainSearchSentenceDTO;
import spring.crut.corpus.dto.CreateSentenceDTO;
import spring.crut.corpus.models.Document;
import spring.crut.corpus.models.Sentence;
import spring.crut.corpus.repositories.DocumentsRepository;
import spring.crut.corpus.repositories.SentencesRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SentencesService {
    private final SentencesRepository sentencesRepository;
    private final TokensService tokensService;
    private final DocumentsRepository documentsRepository;
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;
    @Transactional
    public void createSentences(List<CreateSentenceDTO> sentenceDTOList, Document document) {
        for (var sentenceDTO : sentenceDTOList) {
            Sentence sentence = new Sentence();
            sentence.setText(sentenceDTO.getText());
            sentence.setNum(sentenceDTO.getNum());
            sentence.setDocument(document);
            sentence.setTokens(new ArrayList<>());
            sentence = sentencesRepository.save(sentence);
            document.getSentences().add(sentence);
            tokensService.createTokens(sentenceDTO.getTokens(), sentence);
            documentsRepository.save(document);
        }
    }

    @Transactional
    public List<CertainSearchSentenceDTO> getByCertainSearch(List<Document> documents, String wordform) {
        wordform = wordform.toLowerCase();
        List<Sentence> resultSentences = new ArrayList<>();
        for (var document : documents) {
            List<Sentence> sentences = document.getSentences();
            for (var sentence : sentences) {
                for (var token : sentence.getTokens()) {
                    var lemma = token.getLemma();
                    if (lemma.equals(wordform)) {
                        resultSentences.add(sentence);
                        break;
                    }
                }
            }
        }
        List<CertainSearchSentenceDTO> sentencesDTO = new ArrayList<>();
        for (var sentence : resultSentences) {
            var sentenceDTO = modelMapper.map(sentence, CertainSearchSentenceDTO.class);
            sentenceDTO.setDocumentTitle(sentence.getDocument().getTitle());
            for (int i = 0; i < sentence.getTokens().size(); ++i) {
                try {
                    var attrs = objectMapper.readValue(sentence.getTokens().get(i).getAttrs(), new TypeReference<Map<String, String>>() {});
                    sentenceDTO.getTokens().get(i).setAttrs(attrs);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
            sentencesDTO.add(sentenceDTO);
        }
        return sentencesDTO;
    }
}
