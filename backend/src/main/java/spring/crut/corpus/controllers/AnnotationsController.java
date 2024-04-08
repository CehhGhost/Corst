package spring.crut.corpus.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/annotations")
@RequiredArgsConstructor
public class AnnotationsController {
    @PostMapping("/create")
    public ResponseEntity<?> createAnnotation() {
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
