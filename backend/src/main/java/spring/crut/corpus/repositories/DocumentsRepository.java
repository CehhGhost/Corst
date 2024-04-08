package spring.crut.corpus.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.crut.corpus.models.Document;

@Repository
public interface DocumentsRepository extends JpaRepository<Document, Long> {
}
