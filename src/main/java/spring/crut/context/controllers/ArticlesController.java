package spring.crut.context.controllers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import spring.crut.administration.security.CrutUserDetails;
import spring.crut.context.dto.ArticleDTO;
import spring.crut.context.models.Article;
import spring.crut.context.services.ArticlesService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/articles")
public class ArticlesController {
    private final ModelMapper modelMapper;
    private final ArticlesService articlesService;

    @PostMapping("/create")
    public ResponseEntity<HttpStatus> createArticle(@RequestBody ArticleDTO articleDTO) {
        var article = modelMapper.map(articleDTO, Article.class);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CrutUserDetails userDetails = (CrutUserDetails) authentication.getPrincipal();
        article.setOwner(userDetails.getUser());
        articlesService.saveArticle(article);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
