package spring.crut.content.services;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import spring.crut.content.models.Section;
import spring.crut.content.repositories.SectionsRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class SectionsService {
    private final SectionsRepository sectionsRepository;
    public List<Section> getAllSections() {
        return sectionsRepository.findAll(Sort.by("number"));
    }

    public Section getSectionById(Long id) {
        var section = sectionsRepository.findById(id);
        if (section.isEmpty()) {
            throw new IllegalArgumentException("No sections with such id!");
        }
        return section.get();
    }

    public void createSection(Section section) {
        this.updateSectionsNumById(sectionsRepository.save(section).getId(), section.getNumber());
    }
    public void updateSectionsNumById(Long id, Integer num) {
        if (!sectionsRepository.existsById(id)) {
            throw new IllegalArgumentException("No sections with such id!");
        }
        var sections = sectionsRepository.findAll(Sort.by("number"));
        int currentNum = num;
        for (var section : sections) {
            if (section.getId().equals(id)) {
                section.setNumber(num);
            } else if (section.getNumber() == currentNum) {
                section.setNumber(currentNum);
                currentNum++;
            }
        }
        sectionsRepository.saveAll(sections);
    }

    public void deleteSectionById(Long id) {
        if (!sectionsRepository.existsById(id)) {
            throw new IllegalArgumentException("No sections with such id!");
        }
        sectionsRepository.deleteById(id);
    }

    public void updateSectionById(Long id, Section section) {
        var currentSection = sectionsRepository.findById(id);
        if (currentSection.isEmpty()) {
            throw new IllegalArgumentException("No sections with such id!");
        }
        currentSection.get().setNumber(section.getNumber());
        currentSection.get().setTextRus(section.getTextRus());
        currentSection.get().setTextEng(section.getTextEng());
        currentSection.get().setHeaderRus(section.getHeaderRus());
        currentSection.get().setHeaderEng(section.getHeaderEng());
        sectionsRepository.save(currentSection.get());
        this.updateSectionsNumById(id, currentSection.get().getNumber());
    }
}
