package spring.crut.corpus.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.crut.administration.models.User;
import spring.crut.administration.security.CrutUserDetails;
import spring.crut.corpus.dto.*;
import spring.crut.corpus.enums.Gender;
import spring.crut.corpus.models.Document;
import spring.crut.corpus.repositories.DocumentsRepository;
import spring.crut.corpus.enums.Status;
import spring.crut.corpus.services.info.AcademicMajorsService;
import spring.crut.corpus.services.info.CoursesService;
import spring.crut.corpus.services.info.DomainsService;
import spring.crut.corpus.services.info.GenresService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DocumentsService {
    private final DocumentsRepository documentsRepository;
    private final SentencesService sentencesService;
    private final TokensService tokensService;
    private final AcademicMajorsService academicMajorsService;
    private final CoursesService coursesService;
    private final DomainsService domainsService;
    private final GenresService genresService;
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;

    private void getRidOfAllOddSpaces(CreateUpdateDocumentDTO createDocumentDTO) {
        StringBuilder text = new StringBuilder(createDocumentDTO.getText());
        var index = text.indexOf("  ");
        while(index != -1) {
            text.replace(index, index + 2, " ");
            index = text.indexOf("  ");
        }
        createDocumentDTO.setText(text.toString());
    }

    @Transactional
    public void createDocument(CreateUpdateDocumentDTO createDocumentDTO) {
        createDocumentDTO.setAuthorsGender(createDocumentDTO.getAuthorsGender().toUpperCase());
        this.getRidOfAllOddSpaces(createDocumentDTO);
        var document = modelMapper.map(createDocumentDTO, Document.class);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CrutUserDetails userDetails = (CrutUserDetails) authentication.getPrincipal();
        document.setOwner(userDetails.getUser());
        document.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        document.setStatus(Status.NOT_ANNOTATED);
        document.setSentences(new ArrayList<>());
        document = documentsRepository.save(document);
        sentencesService.createSentences(document);
        this.setInfo(document, createDocumentDTO);
        documentsRepository.save(document);
    }

    @Transactional
    public void updateDocument(Long id, CreateUpdateDocumentDTO updateDocumentDTO) {
        if (!documentsRepository.existsById(id)) {
            throw new IllegalArgumentException("No such document with that id!");
        }
        var document = documentsRepository.findById(id).orElseThrow();
        this.getRidOfAllOddSpaces(updateDocumentDTO);
        if (!document.getText().equals(updateDocumentDTO.getText())) {
            document.getSentences().removeAll(document.getSentences());
            sentencesService.deleteSentencesByTheirDocument(document);
            document.setText(updateDocumentDTO.getText());
            document = documentsRepository.save(document);
            sentencesService.createSentences(document);
        }
        updateDocumentDTO.setAuthorsGender(updateDocumentDTO.getAuthorsGender().toUpperCase());
        document.setTitle(updateDocumentDTO.getTitle());
        document.setAuthorsGender(Gender.valueOf(updateDocumentDTO.getAuthorsGender()));
        this.setInfo(document, updateDocumentDTO);
        documentsRepository.save(document);
    }

    @Transactional
    public void setInfo(Document document, CreateUpdateDocumentDTO createUpdateDocumentDTO) {
        document.setAuthorsAcademicMajor(academicMajorsService.create(createUpdateDocumentDTO.getAuthorsAcademicMajor()));
        document.setAuthorsCourse(coursesService.create(createUpdateDocumentDTO.getAuthorsCourse()));
        document.setDomain(domainsService.create(createUpdateDocumentDTO.getDomain()));
        document.setGenre(genresService.create(createUpdateDocumentDTO.getGenre()));
    }

    @Transactional
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

    @Transactional
    public void deleteDocument(Long id) {
        if (!documentsRepository.existsById(id)) {
            throw new IllegalArgumentException("No such document with that id!");
        }
        documentsRepository.deleteById(id);
    }

    @Transactional
    public List<Document> getAllDocuments() {
        return documentsRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    @Transactional
    public Document getDocumentByID(Long id) {
        var document = documentsRepository.findById(id);
        if (document.isEmpty()) {
            throw new IllegalArgumentException("No such document!");
        }
        return document.get();
    }

    @Transactional
    public List<AnnotationDTO> getAnnotationsByTheirDocumentId(Long documentId) {
        var document = this.getDocumentByID(documentId);
        List<AnnotationDTO> annotationDTOs = new ArrayList<>();
        int counter = 0;
        for (var sentence : sentencesService.getSentencesByTheirDocument(document)) {
            while (counter < document.getText().length() && document.getText().charAt(counter) != sentence.getText().charAt(0)){
                counter++;
            }
            for (var annotation : sentence.getAnnotations()) {
                Map<String, Object> annotationInfo = null;
                try {
                    annotationInfo = objectMapper.readValue(annotation.getAnnotationInfo(), new TypeReference<>() {});

                    Map<String, Object> target = (Map<String, Object>) annotationInfo.get("target");
                    List<Map<String, Object>> selectorArray = (List<Map<String, Object>>) target.get("selector");
                    Map<String, Object> selector = selectorArray.get(1);
                    int start = (int) selector.get("start");
                    int end = (int) selector.get("end");

                    start += counter;
                    end += counter;

                    selector.put("start", start);
                    selector.put("end", end);

                    objectMapper.writeValueAsString(annotationInfo);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
                AnnotationDTO annotationDTO = new AnnotationDTO();
                annotationDTO.setInfo(annotationInfo);
                annotationDTOs.add(annotationDTO);
            }
            counter += sentence.getText().length();
        }
        return annotationDTOs;
    }

    public List<Document> getAllDocumentsByUser(User user) {
        return documentsRepository.findAllByOwner(user);
    }

    public void setAttrsForTokensInDocumentDTO(DocumentDTO documentDTO) {
        for (var sentence : documentDTO.getSentences()) {
            tokensService.setAttrsForTokensDTO(sentence.getTokens(), sentencesService.getSentenceById(sentence.getId()).getTokens());
        }
    }

    public void setStatusById(Long id, Integer status) {
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
}
