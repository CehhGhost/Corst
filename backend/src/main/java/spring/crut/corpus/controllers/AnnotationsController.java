package spring.crut.corpus.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.crut.corpus.dto.AnnotationDTO;
import spring.crut.corpus.dto.CreateUpdateAnnotationDTO;
import spring.crut.corpus.models.Annotation;
import spring.crut.corpus.services.AnnotationsService;

import java.util.List;

@RestController
@RequestMapping("/annotations")
@RequiredArgsConstructor
public class AnnotationsController {
    private final AnnotationsService annotationsService;
    @PostMapping("/create")
    public ResponseEntity<?> createAnnotation(@RequestBody CreateUpdateAnnotationDTO createUpdateAnnotationDTO) {
        annotationsService.createAnnotation(createUpdateAnnotationDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getAnnotationById(@PathVariable Long id) {
        Annotation annotation = annotationsService.getAnnotationById(id);
        return ResponseEntity.ok(annotation);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAnnotationById(@PathVariable Long id) {
        annotationsService.deleteAnnotationById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAnnotationsById(@PathVariable Long id, @RequestBody CreateUpdateAnnotationDTO createUpdateAnnotationDTO) {
        annotationsService.updateAnnotationById(id, createUpdateAnnotationDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
