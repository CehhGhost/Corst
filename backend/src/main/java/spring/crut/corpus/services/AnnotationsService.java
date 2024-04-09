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
        var sentence = sentencesService.getSentenceById(createUpdateAnnotationDTO.getSentenceId());
        annotation.setSentence(sentence);
        annotation.setStringId(((String) createUpdateAnnotationDTO.getInfo().get("id")).replace("#", ""));
        annotationsRepository.save(annotation);
        sentencesService.setAnnotationForSentence(annotation, sentence);
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

    @Transactional
    public Annotation getAnnotationById(String id) {
        var annotation = annotationsRepository.findByStringId(id);
        if (annotation.isEmpty()) {
            throw new IllegalArgumentException("No such annotation with this id!");
        }
        return annotation.get();
    }

    @Transactional
    public void deleteAnnotationById(String id) {
        var annotation = annotationsRepository.findByStringId(id);
        if (annotation.isEmpty()) {
            throw new IllegalArgumentException("No such annotation with this id!");
        }
        sentencesService.removeAnnotation(annotation.get());
        annotationsRepository.delete(annotation.get());
    }

    @Transactional
    public void updateAnnotationById(String id, CreateUpdateAnnotationDTO createUpdateAnnotationDTO) {
        var annotation = annotationsRepository.findByStringId(id);
        if (annotation.isEmpty()) {
            throw new IllegalArgumentException("No such annotation with this id!");
        }
        String json = null;
        try {
            json = objectMapper.writeValueAsString(createUpdateAnnotationDTO.getInfo());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        annotation.get().setAnnotationInfo(json);
        var errorTags = annotation.get().getErrorTags();
        errorTags.clear();
        var errorTagsNames = extractErrorTags(createUpdateAnnotationDTO.getInfo());
        errorTags.addAll(errorTagsService.getAllByNames(errorTagsNames).stream().toList());
        annotationsRepository.save(annotation.get());
    }

    public List<AnnotationDTO> getAnnotationsBySentenceId(Long id) {
        return sentencesService.getAnnotationsByTheirSentenceId(id);
    }

    public List<AnnotationDTO> getAnnotationsByDocumentId(Long id) {
        return documentsService.getAnnotationsByTheirDocumentId(id);
    }
}
