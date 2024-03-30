package spring.crut.content.services;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import spring.crut.administration.security.CrutUserDetails;
import spring.crut.content.models.Article;
import spring.crut.content.repositories.ArticlesRepository;
import java.sql.Timestamp;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ArticlesService {
    private final ArticlesRepository articlesRepository;

    public Article getArticleById(Integer id) {
        Optional<Article> article = articlesRepository.findById(id);
        if (article.isEmpty()) {
            throw new IllegalArgumentException("Wrong id for dish");
        }
        return article.get();
    }

    public void createArticle(Article article) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CrutUserDetails userDetails = (CrutUserDetails) authentication.getPrincipal();
        article.setOwner(userDetails.getUser());
        article.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        article.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        articlesRepository.save(article);
    }

    public List<Article> getAllArticles() {
        var articles = articlesRepository.findAll();
        return articles;
    }
}
