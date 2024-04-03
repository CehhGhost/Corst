package spring.crut.corpus.services;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import spring.crut.corpus.dto.CertainSearchSentenceDTO;
import spring.crut.corpus.dto.CreateSentenceDTO;

import spring.crut.corpus.models.Document;
import spring.crut.corpus.models.Sentence;
import spring.crut.corpus.repositories.DocumentsRepository;
import spring.crut.corpus.repositories.SentencesRepository;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class SentencesService {
    private final SentencesRepository sentencesRepository;
    private final TokensService tokensService;
    private final DocumentsRepository documentsRepository;
    private final ModelMapper modelMapper;
    private final RestTemplate restTemplate;

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
        List<CertainSearchSentenceDTO> sentencesDTO = new ArrayList<>();
        if (wordform == null || wordform.isEmpty()) {
            return sentencesDTO;
        }

        String natashaServiceUrl = "http://127.0.0.1:5000/lemmatize";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>("{\"text\": \"" + wordform + "\"}", headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(natashaServiceUrl, HttpMethod.POST, requestEntity, String.class);
        String responseBody = responseEntity.getBody();
        LemmatizedWordform lemmatizedWordform;
        try {
            lemmatizedWordform = objectMapper.readValue(responseBody, LemmatizedWordform.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        List<Sentence> resultSentences = new ArrayList<>();
        for (var document : documents) {
            List<Sentence> sentences = document.getSentences();
            for (var sentence : sentences) {
                if (sentence.getLemmatizedText().contains(lemmatizedWordform.getText())) {
                    resultSentences.add(sentence);
                }
            }
        }
        for (var sentence : resultSentences) {
            var sentenceDTO = modelMapper.map(sentence, CertainSearchSentenceDTO.class);
            sentenceDTO.setDocumentTitle(sentence.getDocument().getTitle());
            tokensService.setAttrsForTokensDTO(sentenceDTO.getTokens(), sentence.getTokens());
            sentencesDTO.add(sentenceDTO);
        }
        return sentencesDTO;
    }

    public Sentence getSentenceById(Integer id) {
        var sentence = sentencesRepository.findById(id);
        if (sentence.isEmpty()) {
            throw new IllegalArgumentException("No such sentence with this id!");
        }
        return sentence.get();
    }

    static class LemmatizedWordform {
        @JsonProperty("lemmatized_string")
        private String text;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}
