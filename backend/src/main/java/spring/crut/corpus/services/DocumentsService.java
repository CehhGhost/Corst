package spring.crut.corpus.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import spring.crut.corpus.dto.*;
import spring.crut.corpus.models.Document;
import spring.crut.corpus.repositories.DocumentsRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import spring.crut.corpus.enums.Status;
import spring.crut.corpus.services.info.AcademicMajorsService;
import spring.crut.corpus.services.info.CoursesService;
import spring.crut.corpus.services.info.DomainsService;
import spring.crut.corpus.services.info.GenresService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentsService {
    private final DocumentsRepository documentsRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final SentencesService sentencesService;
    private final ModelMapper modelMapper;
    private final TokensService tokensService;
    private final AcademicMajorsService academicMajorsService;
    private final CoursesService coursesService;
    private final DomainsService domainsService;
    private final GenresService genresService;
    @Transactional
    public void createDocument(Document document, CreateDocumentDTO createDocumentDTO) {
        document.setSentences(new ArrayList<>());
        document.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        document.setStatus(Status.NOT_ANNOTATED);
        document.setAuthorsAcademicMajor(academicMajorsService.create(createDocumentDTO.getAuthorsAcademicMajor()));
        document.setAuthorsCourse(coursesService.create(createDocumentDTO.getAuthorsCourse()));
        document.setDomain(domainsService.create(createDocumentDTO.getDomain()));
        document.setGenre(genresService.create(createDocumentDTO.getGenre()));
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

    public List<Document> specifySubcorpus(SubcorpusDataDTO subcorpusDataDTO) {
        var documents = documentsRepository.findAll();
        List<Document> specifiedDocuments = new ArrayList<>();
        for (var document : documents) {
            if (this.equalsSubcorpus(document, subcorpusDataDTO)) {
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

    public List<Document> getAllDocuments() {
        return documentsRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    public Document getDocumentByID(Integer id) {
        var document = documentsRepository.findById(id);
        if (document.isEmpty()) {
            throw new IllegalArgumentException("No such document!");
        }
        return document.get();
    }

    public void setAttrsForTokensInDocumentDTO(DocumentDTO documentDTO) {
        for (var sentence : documentDTO.getSentences()) {
            tokensService.setAttrsForTokensDTO(sentence.getTokens(), sentencesService.getSentenceById(sentence.getId()).getTokens());
        }
    }

    public void setStatusById(Integer id, Integer status) {
        var values = Status.values();
        if (status < 0 || status >= values.length) {
            throw new IllegalArgumentException("wrong status!");
        }
        var document = this.getDocumentByID(id);
        document.setStatus(values[status]);
        documentsRepository.save(document);
    }

    public boolean equalsSubcorpus(Document document, SubcorpusDataDTO subcorpusDataDTO) {
        if (subcorpusDataDTO == null) {
            return true;
        }
        if (subcorpusDataDTO.getStatuses() != null && !subcorpusDataDTO.getStatuses().isEmpty()) {
            subcorpusDataDTO.getStatuses().replaceAll(s -> s.replaceAll(" ", "_").toUpperCase());
        }
        if (subcorpusDataDTO.getAuthorsGenders() != null && !subcorpusDataDTO.getAuthorsGenders().isEmpty()) {
            subcorpusDataDTO.getAuthorsGenders().replaceAll(String::toUpperCase);
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(document.getCreatedAt().getTime());
        int documentCreatedAtYear = calendar.get(Calendar.YEAR);
        return (subcorpusDataDTO.getAuthorsCourses() == null || subcorpusDataDTO.getAuthorsCourses().isEmpty() || subcorpusDataDTO.getAuthorsCourses().contains(document.getAuthorsCourse().getName())) &&
                (subcorpusDataDTO.getAuthorsAcademicMajors() == null || subcorpusDataDTO.getAuthorsAcademicMajors().isEmpty() || subcorpusDataDTO.getAuthorsAcademicMajors().contains(document.getAuthorsAcademicMajor().getName())) &&
                (subcorpusDataDTO.getGenres() == null || subcorpusDataDTO.getGenres().isEmpty() || subcorpusDataDTO.getGenres().contains(document.getGenre().getName())) &&
                (subcorpusDataDTO.getAuthorsGenders() == null || subcorpusDataDTO.getAuthorsGenders().isEmpty() || subcorpusDataDTO.getAuthorsGenders().contains(document.getAuthorsGender().name())) &&
                (subcorpusDataDTO.getDomains() == null || subcorpusDataDTO.getDomains().isEmpty() || subcorpusDataDTO.getDomains().contains(document.getDomain().getName())) &&
                (subcorpusDataDTO.getStatuses() == null || subcorpusDataDTO.getStatuses().isEmpty() || subcorpusDataDTO.getStatuses().contains(document.getStatus().name())) &&
                (subcorpusDataDTO.getPeriodFrom() == null || subcorpusDataDTO.getPeriodFrom() <= documentCreatedAtYear) &&
                (subcorpusDataDTO.getPeriodTo() == null || documentCreatedAtYear <= subcorpusDataDTO.getPeriodTo());
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
