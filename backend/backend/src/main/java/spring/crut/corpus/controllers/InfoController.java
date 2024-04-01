package spring.crut.corpus.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.crut.corpus.dto.DocumentInfoDTO;
import spring.crut.corpus.services.info.*;

@RestController
@RequestMapping("/info")
@RequiredArgsConstructor
public class InfoController {
    private final AcademicMajorsService academicMajorsService;
    private final CoursesService coursesService;
    private final DomainsService domainsService;
    private final GenresService genresService;
    private final ErrorTagsService errorTagsService;

    @GetMapping ("/document")
    public ResponseEntity<?> getAllDocumentInfo() {
        DocumentInfoDTO documentInfoDTO = new DocumentInfoDTO();
        documentInfoDTO.setDomains(domainsService.getAll());
        documentInfoDTO.setCourses(coursesService.getAll());
        documentInfoDTO.setGenres(genresService.getAll());
        documentInfoDTO.setAcademicMajors(academicMajorsService.getAll());
        return ResponseEntity.ok(documentInfoDTO);
    }
}
