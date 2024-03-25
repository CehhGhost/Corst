package spring.crut.context.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.crut.context.models.Section;

@Repository
public interface SectionsRepository extends JpaRepository<Section, Integer> {
}
