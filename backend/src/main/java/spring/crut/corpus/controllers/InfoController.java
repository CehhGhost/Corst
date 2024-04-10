package spring.crut.corpus.controllers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.crut.corpus.dto.DocumentInfoDTO;
import spring.crut.corpus.dto.InfoDTO;
import spring.crut.corpus.services.info.*;

//    @GetMapping
//    @GetMapping("/{id}")
//    @PostMapping
//    @DeleteMapping
//    @PutMapping

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
        documentInfoDTO.setDomains(domainsService.getAllNames());
        documentInfoDTO.setCourses(coursesService.getAllNames());
        documentInfoDTO.setGenres(genresService.getAllNames());
        documentInfoDTO.setAcademicMajors(academicMajorsService.getAllNames());
        return ResponseEntity.ok(documentInfoDTO);
    }

    @GetMapping("/academic_majors")
    public ResponseEntity<?> getAllAcademicMajors() {
        return ResponseEntity.ok(academicMajorsService.getAll());
    }

    @PostMapping("/academic_majors/create")
    public ResponseEntity<?> createAcademicMajor(@RequestBody InfoDTO infoDTO) {
        academicMajorsService.create(infoDTO.getName());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/academic_majors/{id}")
    public ResponseEntity<?> deleteAcademicMajorById(@PathVariable Long id) {
        academicMajorsService.deleteInfoById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @PutMapping("/academic_majors/{id}")
    public ResponseEntity<?> updateAcademicMajorById(@PathVariable Long id, @RequestBody InfoDTO infoDTO) {
        academicMajorsService.updateInfoById(id, infoDTO.getName());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/courses")
    public ResponseEntity<?> getAllCourses() {
        return ResponseEntity.ok(coursesService.getAll());
    }

    @PostMapping("/courses/create")
    public ResponseEntity<?> createCourse(@RequestBody InfoDTO infoDTO) {
        coursesService.create(infoDTO.getName());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/courses/{id}")
    public ResponseEntity<?> deleteCourseById(@PathVariable Long id) {
        coursesService.deleteInfoById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @PutMapping("/courses/{id}")
    public ResponseEntity<?> updateCourseById(@PathVariable Long id, @RequestBody InfoDTO infoDTO) {
        coursesService.updateInfoById(id, infoDTO.getName());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/domains")
    public ResponseEntity<?> getAllDomains() {
        return ResponseEntity.ok(domainsService.getAll());
    }

    @PostMapping("/domains/create")
    public ResponseEntity<?> createDomain(@RequestBody InfoDTO infoDTO) {
        domainsService.create(infoDTO.getName());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/domains/{id}")
    public ResponseEntity<?> deleteDomainById(@PathVariable Long id) {
        domainsService.deleteInfoById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @PutMapping("/domains/{id}")
    public ResponseEntity<?> updateDomainById(@PathVariable Long id, @RequestBody InfoDTO infoDTO) {
        domainsService.updateInfoById(id, infoDTO.getName());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/genres")
    public ResponseEntity<?> getAllGenres() {
        return ResponseEntity.ok(genresService.getAll());
    }

    @PostMapping("/genres/create")
    public ResponseEntity<?> createGenre(@RequestBody InfoDTO infoDTO) {
        genresService.create(infoDTO.getName());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/genres/{id}")
    public ResponseEntity<?> deleteGenreById(@PathVariable Long id) {
        genresService.deleteInfoById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @PutMapping("/genres/{id}")
    public ResponseEntity<?> updateGenreById(@PathVariable Long id, @RequestBody InfoDTO infoDTO) {
        genresService.updateInfoById(id, infoDTO.getName());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/error_tags/create")
    public ResponseEntity<?> createErrorTag(@RequestBody InfoDTO infoDTO) {
        errorTagsService.create(infoDTO.getName());
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
