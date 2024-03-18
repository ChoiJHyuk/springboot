package com.rosoa0475.springboot.article.dto.request;

import java.time.LocalDateTime;

import com.rosoa0475.springboot.user.entity.LocalUser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleResponseDto {
    private String subject;
    private String content;
    private Long id;
    private LocalUser writer;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
