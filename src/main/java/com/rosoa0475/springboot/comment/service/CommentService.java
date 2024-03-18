package com.rosoa0475.springboot.comment.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.rosoa0475.springboot.article.entity.Article;
import com.rosoa0475.springboot.comment.entity.Comment;
import com.rosoa0475.springboot.comment.repository.CommentRepository;
import com.rosoa0475.springboot.exception.DataNotFoundException;
import com.rosoa0475.springboot.user.entity.LocalUser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public void saveComment(Article article, String content, LocalUser writer) {
        Comment c = new Comment();
        c.setArticle(article);
        c.setContent(content);
        c.setWriter(writer);
        c.setCreatedAt(LocalDateTime.now());
        this.commentRepository.save(c);
    }

    @SuppressWarnings("null")
    public Comment getComment(Long id){
        Optional<Comment> oc = this.commentRepository.findById(id);
        if(oc.isPresent()){
            return oc.get();
        }else{
            throw new DataNotFoundException("comment not found");
        }
    }

    public void updateComment(Comment comment, String content){
        comment.setContent(content);
        comment.setUpdatedAt(LocalDateTime.now());
        this.commentRepository.save(comment);
    }

    @SuppressWarnings("null")
    public void deleteComment(Comment comment){
        this.commentRepository.delete(comment);
    }
}
