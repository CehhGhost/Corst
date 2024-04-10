package spring.crut.content.controllers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.crut.content.dto.CreateUpdateSectionDTO;
import spring.crut.content.dto.SectionDTO;
import spring.crut.content.models.Section;
import spring.crut.content.services.SectionsService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sections")
public class SectionsController {
    private final SectionsService sectionsService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<?> getAllSections() {
        var sections = sectionsService.getAllSections();
        List<SectionDTO> sectionDTOs = new ArrayList<>();
        for (var section : sections) {
            sectionDTOs.add(modelMapper.map(section, SectionDTO.class));
        }
        return ResponseEntity.ok(sectionDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSectionById(@PathVariable Long id) {
        var section = sectionsService.getSectionById(id);
        return ResponseEntity.ok(modelMapper.map(section, SectionDTO.class));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createSection(@RequestBody CreateUpdateSectionDTO sectionDTO) {
        var section = modelMapper.map(sectionDTO, Section.class);
        sectionsService.createSection(section);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSectionById(@PathVariable Long id) {
        sectionsService.deleteSectionById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSectionById(@PathVariable Long id, @RequestBody CreateUpdateSectionDTO sectionDTO) {
        var section = modelMapper.map(sectionDTO, Section.class);
        sectionsService.updateSectionById(id, section);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/{id}/set_num/{num}")
    public ResponseEntity<?> setSectionsNumberById(@PathVariable Long id, @PathVariable Integer num) {
        sectionsService.updateSectionsNumById(id, num);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
