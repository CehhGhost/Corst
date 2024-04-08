package spring.crut.corpus.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.crut.administration.security.CrutUserDetails;
import spring.crut.corpus.dto.AnnotationDTO;
import spring.crut.corpus.dto.CreateUpdateAnnotationDTO;
import spring.crut.corpus.models.Annotation;
import spring.crut.corpus.repositories.AnnotationsRepository;
import spring.crut.corpus.services.info.ErrorTagsService;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AnnotationsService {
    private final AnnotationsRepository annotationsRepository;
    private final ErrorTagsService errorTagsService;
    private final ObjectMapper objectMapper;
    private final SentencesService sentencesService;
    private final DocumentsService documentsService;
    @Transactional
    public void createAnnotation(CreateUpdateAnnotationDTO createUpdateAnnotationDTO) {
        String json = null;
        try {
            json = objectMapper.writeValueAsString(createUpdateAnnotationDTO.getInfo());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        Annotation annotation = new Annotation();



        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CrutUserDetails userDetails = (CrutUserDetails) authentication.getPrincipal();
        annotation.setOwner(userDetails.getUser());
        annotation.setAnnotationInfo(json);
        var errorTagsNames = extractErrorTags(createUpdateAnnotationDTO.getInfo());
        annotation.setErrorTags(errorTagsService.getAllByNames(errorTagsNames).stream().toList());
        annotationsRepository.save(annotation);

        sentencesService.setAnnotationForSentenceById(annotation, createUpdateAnnotationDTO.getSentenceId());
    }
    private List<String> extractErrorTags(Map<String, Object> annotationMap) {
        List<Map<String, Object>> bodyList = (List<Map<String, Object>>) annotationMap.get("body");
        List<String> errorTagsNames = new ArrayList<>();
        if (bodyList != null) {
            for (Map<String, Object> body : bodyList) {
                String purpose = (String) body.get("purpose");
                String value = (String) body.get("value");
                if ("tagging".equals(purpose)) {
                    errorTagsNames.add(value);
                }
            }
        }
        return errorTagsNames;
    }

    public Annotation getAnnotationById(Long id) {
        var annotation = annotationsRepository.findById(id);
        if (annotation.isEmpty()) {
            throw new IllegalArgumentException("No such annotation with this id!");
        }
        return annotation.get();
    }

    @Transactional
    public void deleteAnnotationById(Long id) {
        var annotation = annotationsRepository.findById(id);
        if (annotation.isEmpty()) {
            throw new IllegalArgumentException("No such annotation with this id!");
        }
        sentencesService.removeAnnotation(annotation.get());
        annotationsRepository.delete(annotation.get());
    }

    @Transactional
    public List<AnnotationDTO> getAnnotationsByTheirDocumentId(Long documentId) {
        var document = documentsService.getDocumentByID(documentId);
        List<AnnotationDTO> annotationDTOs = new ArrayList<>();
        for (var sentence : document.getSentences()) {
            for (var annotation : sentence.getAnnotations()) {
                Map<String, Object> annotationInfo = null;
                try {
                    annotationInfo = objectMapper.readValue(annotation.getAnnotationInfo(), new TypeReference<>() {});
                    objectMapper.writeValueAsString(annotationInfo);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
                AnnotationDTO annotationDTO = new AnnotationDTO();
                annotationDTO.setInfo(annotationInfo);
                annotationDTOs.add(annotationDTO);
            }
        }
        return annotationDTOs;
    }

    @Transactional
    public List<AnnotationDTO> getAnnotationsByTheirSentenceId(Long sentenceId) {
        var sentence = sentencesService.getSentenceById(sentenceId);
        List<AnnotationDTO> annotationDTOs = new ArrayList<>();
        for (var annotation : sentence.getAnnotations()) {
            Map<String, Object> annotationInfo = null;
            try {
                annotationInfo = objectMapper.readValue(annotation.getAnnotationInfo(), new TypeReference<>() {});
                objectMapper.writeValueAsString(annotationInfo);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            AnnotationDTO annotationDTO = new AnnotationDTO();
            annotationDTO.setInfo(annotationInfo);
            annotationDTOs.add(annotationDTO);
        }
        return annotationDTOs;
    }
}
