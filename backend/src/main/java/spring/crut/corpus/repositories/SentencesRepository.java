package spring.crut.corpus.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.crut.corpus.models.Sentence;

import java.util.List;

@Repository
public interface SentencesRepository extends JpaRepository<Sentence, Integer> {
}
