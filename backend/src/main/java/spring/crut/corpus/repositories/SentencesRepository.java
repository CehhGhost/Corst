package spring.crut.corpus.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.crut.corpus.models.Document;
import spring.crut.corpus.models.Sentence;
import spring.crut.corpus.models.Token;

import java.util.List;

@Repository
public interface SentencesRepository extends JpaRepository<Sentence, Long> {
    List<Sentence> findAllByDocument(Document document);
}
