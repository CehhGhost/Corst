package spring.crut.content.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.crut.content.models.Article;

@Repository
public interface ArticlesRepository extends JpaRepository<Article, Integer> {
}
