package com.rosoa0475.springboot.article.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.rosoa0475.springboot.article.dto.request.ArticleResponseDto;
import com.rosoa0475.springboot.comment.entity.Comment;
import com.rosoa0475.springboot.user.entity.LocalUser;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Article {
    public ArticleResponseDto toArticleResponseDto(){
        return ArticleResponseDto.builder()
                .id(id)
                .subject(subject)
                .content(content)
                .writer(writer)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createdAt;

    @ManyToOne
    private LocalUser writer;

    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    @ManyToMany
    private Set<LocalUser> likes;
}
