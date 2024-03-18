package com.rosoa0475.springboot.comment.entity;

import java.time.LocalDateTime;

import com.rosoa0475.springboot.article.entity.Article;
import com.rosoa0475.springboot.user.entity.LocalUser;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length=200)
    private String content;

    @ManyToOne
    private Article article;

    @ManyToOne
    private LocalUser writer;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
