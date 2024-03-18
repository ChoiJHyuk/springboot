package com.rosoa0475.springboot.article.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.rosoa0475.springboot.article.dto.request.ArticleRequestDto;
import com.rosoa0475.springboot.article.dto.request.ArticleResponseDto;
import com.rosoa0475.springboot.article.entity.Article;
import com.rosoa0475.springboot.article.repository.ArticleRepository;
import com.rosoa0475.springboot.exception.DataNotFoundException;
import com.rosoa0475.springboot.user.entity.LocalUser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    public List<Article> getList(){
        return this.articleRepository.findAll();
    }

    @SuppressWarnings("null")
    public Article getArticle(Long id){
        Optional<Article> oa = this.articleRepository.findById(id);
        if(oa.isPresent()){
            Article article = oa.get();
            return article;
        }else{
            throw new DataNotFoundException("article not found");
        }
    }

    public void saveArticle(String subject, String content, LocalUser writer) {
        Article article = new Article();
        article.setSubject(subject);
        article.setContent(content);
        article.setWriter(writer);
        article.setCreatedAt(LocalDateTime.now());
        this.articleRepository.save(article);
    }

    @SuppressWarnings("null")
    public ArticleResponseDto saveArticle(ArticleRequestDto articleRequestDto, LocalUser user){
        Article article = articleRequestDto.toEntity(user);
        this.articleRepository.save(article);
        return article.toArticleResponseDto();
    }

    public Page<Article> getList(int page){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createdAt"));
        Pageable pageable = PageRequest.of(page,10, Sort.by(sorts));
        return this.articleRepository.findAll(pageable);
    }

    public void updateArticle(Article article, String subject, String content){
        article.setSubject(subject);
        article.setContent(content);
        article.setUpdatedAt(LocalDateTime.now());
        this.articleRepository.save(article);
    }

    @SuppressWarnings("null")
    public void deleteArticle(Article article){
        this.articleRepository.delete(article);
    }

    public void likeArticle(Article article, LocalUser user){
        Set<LocalUser> likes = article.getLikes();
        if(likes.contains(user)){
            likes.remove(user);
        }else{
            likes.add(user);
        }
        this.articleRepository.save(article);
    }
    
}
