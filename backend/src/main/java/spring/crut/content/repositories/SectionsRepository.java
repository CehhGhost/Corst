package spring.crut.content.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.crut.content.models.Section;

@Repository
public interface SectionsRepository extends JpaRepository<Section, Long> {
}
