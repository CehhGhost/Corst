package spring.crut.corpus.controllers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.crut.corpus.dto.DocumentInfoDTO;
import spring.crut.corpus.dto.InfoDTO;
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
    private final ModelMapper modelMapper;

    @GetMapping ("/document")
    public ResponseEntity<?> getAllDocumentInfo() {
        DocumentInfoDTO documentInfoDTO = new DocumentInfoDTO();
        documentInfoDTO.setDomains(domainsService.getAllNames());
        documentInfoDTO.setCourses(coursesService.getAllNames());
        documentInfoDTO.setGenres(genresService.getAllNames());
        documentInfoDTO.setAcademicMajors(academicMajorsService.getAllNames());
        return ResponseEntity.ok(documentInfoDTO);
    }
    @PostMapping("/error_tags/create")
    public ResponseEntity<?> createErrorTag(@RequestBody InfoDTO infoDTO) {
        errorTagsService.create(infoDTO.getName());
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
