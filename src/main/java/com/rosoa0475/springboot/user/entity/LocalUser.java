package com.rosoa0475.springboot.user.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.rosoa0475.springboot.article.entity.Article;
import com.rosoa0475.springboot.comment.entity.Comment;
import com.rosoa0475.springboot.image.entity.ProfileImage;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class LocalUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String username;

    @Column
    private String password;

    @Column(unique = true)
    private String nickname;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "writer", cascade = CascadeType.REMOVE)
    private List<Article> articles;

    @OneToMany(mappedBy = "writer", cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    @OneToOne
    private ProfileImage ProfileImage;
}
