package com.rosoa0475.springboot.article.dto.request;

import com.rosoa0475.springboot.article.entity.Article;
import com.rosoa0475.springboot.user.entity.LocalUser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleRequestDto {
    private String subject;
    private String content;
    private Integer userId;

    public Article toEntity(LocalUser user) {
        return Article.builder()
                .subject(subject)
                .content(content)
                .writer(user)
                .build();
    }
}
