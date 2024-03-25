package spring.crut.context.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.crut.administration.models.User;
import spring.crut.context.models.Article;

import java.util.Optional;

@Repository
public interface ArticlesRepository extends JpaRepository<Article, Integer> {
}
