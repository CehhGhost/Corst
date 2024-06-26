package spring.crut.corpus.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.crut.corpus.dto.AnnotationDTO;
import spring.crut.corpus.dto.CreateUpdateAnnotationDTO;
import spring.crut.corpus.models.Annotation;
import spring.crut.corpus.services.AnnotationsService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/annotations")
@RequiredArgsConstructor
public class AnnotationsController {
    private final AnnotationsService annotationsService;
    private final ObjectMapper objectMapper;
    @PostMapping("/create")
    public ResponseEntity<?> createAnnotation(@RequestBody CreateUpdateAnnotationDTO createUpdateAnnotationDTO) {
        annotationsService.createAnnotation(createUpdateAnnotationDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getAnnotationById(@PathVariable UUID id) {
        Annotation annotation = annotationsService.getAnnotationById(id.toString());
        Map<String, Object> annotationInfo = null;
        try {
            annotationInfo = objectMapper.readValue(annotation.getAnnotationInfo(), new TypeReference<>() {});
            objectMapper.writeValueAsString(annotationInfo);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        AnnotationDTO annotationDTO = new AnnotationDTO();
        annotationDTO.setInfo(annotationInfo);
        return ResponseEntity.ok(annotationInfo);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAnnotationById(@PathVariable UUID id) {
        annotationsService.deleteAnnotationById(id.toString());
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAnnotationsById(@PathVariable UUID id, @RequestBody CreateUpdateAnnotationDTO createUpdateAnnotationDTO) {
        annotationsService.updateAnnotationById(id.toString(), createUpdateAnnotationDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @GetMapping("/get_by_sentence/{id}")
    public ResponseEntity<?> getAnnotationsBySentenceId(@PathVariable Long id) {
        List<AnnotationDTO> annotations = annotationsService.getAnnotationsBySentenceId(id);
        List<Map<String, Object>> result = new ArrayList<>();
        for (var annotation : annotations) {
            result.add(annotation.getInfo());
        }
        return ResponseEntity.ok(result);
    }
    @GetMapping("/get_by_document/{id}")
    public ResponseEntity<?> getAnnotationsByDocumentId(@PathVariable Long id) {
        List<AnnotationDTO> annotations = annotationsService.getAnnotationsByDocumentId(id);
        List<Map<String, Object>> result = new ArrayList<>();
        for (var annotation : annotations) {
            result.add(annotation.getInfo());
        }
        return ResponseEntity.ok(result);
    }
}
