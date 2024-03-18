package com.rosoa0475.springboot.article.controller;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rosoa0475.springboot.article.dto.request.ArticleListResponseDto;
import com.rosoa0475.springboot.article.dto.request.ArticleRequestDto;
import com.rosoa0475.springboot.article.dto.request.ArticleResponseDto;
import com.rosoa0475.springboot.article.entity.Article;
import com.rosoa0475.springboot.article.service.ArticleService;
import com.rosoa0475.springboot.user.entity.LocalUser;
import com.rosoa0475.springboot.user.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;



@RequestMapping("/api/article")
@RequiredArgsConstructor
@RestController
public class ArticleApiController {
    private final ArticleService articleService;
    private final UserService userService;

    @PostMapping("/")
    public ArticleResponseDto create(@Valid @RequestBody ArticleRequestDto articleRequestDto) {
        LocalUser writer = this.userService.findUserWithId(articleRequestDto.getUserId());
        return this.articleService.saveArticle(articleRequestDto, writer);
    }
    
    @GetMapping("/")
    public ArticleListResponseDto list(@RequestParam(value="page", defaultValue = "0") int page) {
        Page<Article> paginator = this.articleService.getList(page);
        return ArticleListResponseDto.createArticleListResponseDto(paginator);
    }
    
}
