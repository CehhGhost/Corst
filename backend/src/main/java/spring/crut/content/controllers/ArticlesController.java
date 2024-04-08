package spring.crut.content.controllers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.crut.content.dto.ArticleDTO;
import spring.crut.content.dto.CreateUpdateArticleDTO;
import spring.crut.content.models.Article;
import spring.crut.content.services.ArticlesService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/articles")
public class ArticlesController {
    private final ModelMapper modelMapper;
    private final ArticlesService articlesService;

    @PostMapping("/create")
    public ResponseEntity<HttpStatus> createArticle(@RequestBody CreateUpdateArticleDTO articleDTO) {
        var article = modelMapper.map(articleDTO, Article.class);
        articlesService.createArticle(article);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<?> getAllArticles() {
        List<ArticleDTO> articlesDTO = new ArrayList<>();
        for (var article : articlesService.getAllArticles()) {
            articlesDTO.add(modelMapper.map(article, ArticleDTO.class));
        }
        return ResponseEntity.ok(articlesDTO);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getArticleById(@PathVariable Long id) {
        return ResponseEntity.ok(modelMapper.map(articlesService.getArticleById(id), ArticleDTO.class));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteArticleById(@PathVariable Long id) {
        articlesService.deleteArticleById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateArticleById(@PathVariable Long id, @RequestBody CreateUpdateArticleDTO articleDTO) {
        var article = modelMapper.map(articleDTO, Article.class);
        articlesService.updateArticleById(id, article);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
