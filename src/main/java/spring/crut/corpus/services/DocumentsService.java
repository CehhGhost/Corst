package spring.crut.corpus.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import spring.crut.corpus.dto.SentenceDTO;
import spring.crut.corpus.dto.CertainSearchDTO;
import spring.crut.corpus.models.Document;
import spring.crut.corpus.repositories.DocumentsRepository;
import com.fasterxml.jackson.core.type.TypeReference;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentsService {
    private final DocumentsRepository documentsRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final SentencesService sentencesService;
    private final ModelMapper modelMapper;
    @Transactional
    public void createDocument(Document document) {
        document.setSentences(new ArrayList<>());
        document.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        document.setStatus(0);
        document = documentsRepository.save(document);
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
        sentencesService.createSentences(sentences.getSentences(), document);
    }

    public List<Document> specifySubcorpus(CertainSearchDTO certainSearchDTO) {
        var specifiedDocument = modelMapper.map(certainSearchDTO, Document.class);
        var documents = documentsRepository.findAll();
        List<Document> specifiedDocuments = new ArrayList<>();
        for (var document : documents) {
            if (document.equalsSubcorpus(specifiedDocument)) {
                specifiedDocuments.add(document);
            }
        }
        return specifiedDocuments;
    }

    public void deleteDocument(Integer id) {
        if (!documentsRepository.existsById(id)) {
            throw new IllegalArgumentException("No such document with that id!");
        }
        documentsRepository.deleteById(id);
    }

    public static class SentenceResponse {
        private List<SentenceDTO> sentences;

        public List<SentenceDTO> getSentences() {
            return sentences;
        }

        public void setSentences(List<SentenceDTO> sentences) {
            this.sentences = sentences;
        }
    }
}
