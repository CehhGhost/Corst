package spring.crut.corpus.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import spring.crut.administration.security.CrutUserDetails;
import spring.crut.corpus.dto.*;
import spring.crut.corpus.enums.Gender;
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
    private final SentencesService sentencesService;
    private final TokensService tokensService;
    private final AcademicMajorsService academicMajorsService;
    private final CoursesService coursesService;
    private final DomainsService domainsService;
    private final GenresService genresService;
    private final ModelMapper modelMapper;

    @Transactional
    public void createDocument(CreateUpdateDocumentDTO createDocumentDTO) {
        createDocumentDTO.setAuthorsGender(createDocumentDTO.getAuthorsGender().toUpperCase());
        var document = modelMapper.map(createDocumentDTO, Document.class);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CrutUserDetails userDetails = (CrutUserDetails) authentication.getPrincipal();
        document.setOwner(userDetails.getUser());

        document.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        document.setStatus(Status.NOT_ANNOTATED);

        this.setInfoAndSentences(document, createDocumentDTO);
    }

    @Transactional
    public void updateDocument(Integer id, CreateUpdateDocumentDTO updateDocumentDTO) {
        if (!documentsRepository.existsById(id)) {
            throw new IllegalArgumentException("No such document with that id!");
        }
        updateDocumentDTO.setAuthorsGender(updateDocumentDTO.getAuthorsGender().toUpperCase());
        var oldDocument = documentsRepository.findById(id).orElseThrow();
        var owner = oldDocument.getOwner();
        var createdAt = oldDocument.getCreatedAt();
        var status = oldDocument.getStatus();
        this.deleteDocument(id);
        var document = modelMapper.map(updateDocumentDTO, Document.class);
        document.setOwner(owner);
        document.setCreatedAt(createdAt);
        document.setStatus(status);
        this.setInfoAndSentences(document, updateDocumentDTO);
    }

    public void setInfoAndSentences(Document document, CreateUpdateDocumentDTO createUpdateDocumentDTO) {
        document.setSentences(new ArrayList<>());
        document.setAuthorsAcademicMajor(academicMajorsService.create(createUpdateDocumentDTO.getAuthorsAcademicMajor()));
        document.setAuthorsCourse(coursesService.create(createUpdateDocumentDTO.getAuthorsCourse()));
        document.setDomain(domainsService.create(createUpdateDocumentDTO.getDomain()));
        document.setGenre(genresService.create(createUpdateDocumentDTO.getGenre()));
        document = documentsRepository.save(document);
        sentencesService.createSentences(document);
        documentsRepository.save(document);
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
}
