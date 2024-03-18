package com.rosoa0475.springboot.article.dto.request;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import com.rosoa0475.springboot.article.entity.Article;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleListResponseDto {
    private List<ArticleResponseDto> articleList;
    private int pageNumber;
    private int totalPages;
    private int pageSize;
    private long totalElement;

    public static ArticleListResponseDto createArticleListResponseDto(Page<Article> paginator) {
        return ArticleListResponseDto.builder()
                .articleList(
                    paginator.getContent().stream().map(article -> article.toArticleResponseDto()).collect(
                        Collectors.toList()))
                .pageNumber(paginator.getNumber())
                .totalPages(paginator.getTotalPages())
                .pageSize(paginator.getSize())
                .totalElement(paginator.getTotalElements())
                .build();

    }
}