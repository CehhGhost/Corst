package spring.crut.content.services;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
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

    public Article getArticleById(Long id) {
        Optional<Article> article = articlesRepository.findById(id);
        if (article.isEmpty()) {
            throw new IllegalArgumentException("No articles with such id!");
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
        return articlesRepository.findAll(Sort.by("date").descending());
    }

    public void deleteArticleById(Long id) {
        articlesRepository.deleteById(id);
    }

    public void updateArticleById(Long id, Article newArticle) {
        if (!articlesRepository.existsById(id)) {
            throw new IllegalArgumentException("No articles with such id!");
        }
        var article = articlesRepository.getById(id);
        article.setTextEng(newArticle.getTextEng());
        article.setTextRus(newArticle.getTextRus());
        article.setDate(newArticle.getDate());
        articlesRepository.save(article);
    }
}
