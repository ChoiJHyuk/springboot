package com.rosoa0475.springboot.article.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rosoa0475.springboot.article.entity.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    Article findBySubject(String subject);
    List<Article> findBySubjectLike(String subject);
    @SuppressWarnings("null")
    Page<Article> findAll(Pageable pageable);
}