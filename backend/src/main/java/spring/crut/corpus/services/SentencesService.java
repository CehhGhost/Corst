package spring.crut.corpus.services;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import spring.crut.corpus.dto.SearchSentenceDTO;
import spring.crut.corpus.dto.CreateSentenceDTO;

import spring.crut.corpus.dto.LexGramTokenDTO;
import spring.crut.corpus.models.Document;
import spring.crut.corpus.models.Sentence;
import spring.crut.corpus.repositories.SentencesRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class SentencesService {
    private final SentencesRepository sentencesRepository;
    private final TokensService tokensService;
    private final ModelMapper modelMapper;
    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;
    @Transactional
    public void createSentences(Document document) {
        String natashaServiceUrl = "http://127.0.0.1:5000/analyse";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>("{\"text\": \"" + document.getText() + "\"}", headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(natashaServiceUrl, HttpMethod.POST, requestEntity, String.class);
        String responseBody = responseEntity.getBody();

        SentenceResponse sentences;
        try {
            sentences = objectMapper.readValue(
                    responseBody,
                    new TypeReference<>(){}
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        var sentenceDTOList = sentences.getSentences();
        for (var sentenceDTO : sentenceDTOList) {
            Sentence sentence = new Sentence();
            sentence.setText(sentenceDTO.getText());
            sentence.setNum(sentenceDTO.getNum());
            sentence.setDocument(document);
            sentence.setTokens(new ArrayList<>());
            sentence = sentencesRepository.save(sentence);
            document.getSentences().add(sentence);
            tokensService.createTokens(sentenceDTO.getTokens(), sentence);
        }
    }

    @Transactional
    public List<SearchSentenceDTO> getByCertainSearch(List<Document> documents, String wordform) {
        List<SearchSentenceDTO> sentencesDTO = new ArrayList<>();
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
            var sentenceDTO = modelMapper.map(sentence, SearchSentenceDTO.class);
            sentenceDTO.setDocumentTitle(sentence.getDocument().getTitle());
            tokensService.setAttrsForTokensDTO(sentenceDTO.getTokens(), sentence.getTokens());
            sentencesDTO.add(sentenceDTO);
        }
        return sentencesDTO;
    }

    @Transactional
    public List<SearchSentenceDTO> getByLexGramSearch(List<Document> documents, List<LexGramTokenDTO> lexGramTokensDTO) {
        List<SearchSentenceDTO> sentencesDTO = new ArrayList<>();
        if (lexGramTokensDTO == null || lexGramTokensDTO.isEmpty()) {
            return sentencesDTO;
        }
        Map<Integer, List<Integer>> checkMap = new HashMap<>();
        for (int i = 0; i < lexGramTokensDTO.size(); ++i) {
            var token = lexGramTokensDTO.get(i);
            token.setWordform(token.getWordform().toLowerCase());
            if (token.getTo() < token.getFrom()) {
                var exchange = token.getTo();
                token.setTo(token.getFrom());
                token.setFrom(exchange);
            }
            for (int j = token.getFrom(); j <= token.getTo(); ++j) {
                if (!checkMap.containsKey(j)) {
                    checkMap.put(j, new ArrayList<>());
                }
                checkMap.get(j).add(i);
            }
        }
        Boolean[] tokensArray = new Boolean[lexGramTokensDTO.size()];
        Boolean[] positionArray = new Boolean[checkMap.size()];
        var positions = checkMap.keySet().toArray();
        List<Sentence> resultSentences = new ArrayList<>();
        for (var document : documents) {
            var sentences = document.getSentences();
            for (var sentence : sentences) {
                var tokens = tokensService.getAllBySentenceAndSort(sentence);
                for (int i = 0; i < tokens.size(); ++i) {
                    for (var startingPosition : checkMap.get(0)) {
                        if (tokensService.equalsToLexGramTokenDTO(tokens.get(i), lexGramTokensDTO.get(startingPosition))) {
                            boolean correctSentence = true;
                            for (int j = 0; j < checkMap.size(); ++j) {
                                positionArray[j] = false;
                            }
                            for (int j = 0; j < lexGramTokensDTO.size(); ++j) {
                                tokensArray[j] = false;
                            }
                            int p = 0;
                            for (var position : checkMap.keySet()) {
                                for (var token : checkMap.get(position)) {
                                    if (i + position >= 0 && i + position < tokens.size() &&
                                            tokensService.equalsToLexGramTokenDTO(tokens.get(i + position), lexGramTokensDTO.get(token))) {
                                        positionArray[p] = true;
                                        tokensArray[token] = true;
                                    }
                                }
                                ++p;
                            }
                            for (int j = 0; j < checkMap.size(); ++j) {
                                if (!positionArray[j]) {
                                    for (var token : checkMap.get(positions[j])) {
                                        if (!tokensArray[token]) {
                                            correctSentence = false;
                                            break;
                                        }
                                    }
                                }
                                if (!correctSentence) {
                                    break;
                                }
                            }
                            if (correctSentence) {
                                resultSentences.add(sentence);
                            }
                        }
                    }
                }
            }
        }


        for (var sentence : resultSentences) {
            var sentenceDTO = modelMapper.map(sentence, SearchSentenceDTO.class);
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

    @Transactional
    public void deleteSentences(List<Sentence> sentences) {
        sentencesRepository.deleteAll(sentences);
    }

    @Transactional
    public void deleteSentencesByTheirDocument(Document document) {
        var sentences = sentencesRepository.findAllByDocument(document);
        this.deleteSentences(sentences);
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

    public static class SentenceResponse {
        private List<CreateSentenceDTO> sentences;

        public List<CreateSentenceDTO> getSentences() {
            return sentences;
        }

        public void setSentences(List<CreateSentenceDTO> sentences) {
            this.sentences = sentences;
        }
    }
}
