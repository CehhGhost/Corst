package spring.crut.corpus.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spring.crut.corpus.models.Document;
import spring.crut.corpus.models.Sentence;

import java.util.List;

public interface SentencesRepository extends JpaRepository<Sentence, Integer> {
}
