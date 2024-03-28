package spring.crut.corpus.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.crut.corpus.models.Token;

public interface TokensRepository extends JpaRepository<Token, Integer> {
}
