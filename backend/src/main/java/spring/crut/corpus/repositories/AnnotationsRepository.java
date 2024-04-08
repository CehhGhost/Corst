package spring.crut.corpus.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.crut.corpus.models.Annotation;

@Repository
public interface AnnotationsRepository extends JpaRepository<Annotation, Long> {
}
