package spring.crut.corpus.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.crut.administration.models.User;
import spring.crut.corpus.models.Document;

import java.util.List;

@Repository
public interface DocumentsRepository extends JpaRepository<Document, Long> {
    public List<Document> findAllByOwner(User owner);
    public List<Document> findAllByOwnerOrderByIdAsc(User owner);
}
