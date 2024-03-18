package com.rosoa0475.springboot.comment.controller;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.rosoa0475.springboot.article.entity.Article;
import com.rosoa0475.springboot.article.service.ArticleService;
import com.rosoa0475.springboot.comment.entity.Comment;
import com.rosoa0475.springboot.comment.form.CommentForm;
import com.rosoa0475.springboot.comment.service.CommentService;
import com.rosoa0475.springboot.user.entity.LocalUser;
import com.rosoa0475.springboot.user.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;






@Controller
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final ArticleService articleService;
    private final UserService userService;
    
    @PostMapping("/create/{id}")
    @PreAuthorize("isAuthenticated()")
    public String createComment(Model model, @Valid CommentForm commentForm, BindingResult bindingResult, Principal principal, @PathVariable("id") Long id) {
        Article article = this.articleService.getArticle(id);
        if(bindingResult.hasErrors()){
            model.addAttribute("article", article);
            return "article/detial";
        }
        LocalUser writer = this.userService.getUser(principal.getName());
        this.commentService.saveComment(article, commentForm.getContent(), writer);
        return String.format("redirect:/article/detail/%s", id);
    }

    @GetMapping("/modify/{id}")
    @PreAuthorize("isAuthenticated()")
    public String modify(CommentForm commentForm, @PathVariable("id") Long id, Principal principal) {
        Comment c = this.commentService.getComment(id);
        if(!c.getWriter().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정 권한이 없습니다.");
        }
        commentForm.setContent(c.getContent());
        return "comment/form";
    }

    @PostMapping("/modify/{id}")
    @PreAuthorize("isAuthenticated()")
    public String modify(@Valid CommentForm commentForm, BindingResult bindingResult, @PathVariable("id") Long id, Principal principal) {
        if(bindingResult.hasErrors()){
            return "comment/form";
        }
        Comment c = this.commentService.getComment(id);
        if(!c.getWriter().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정 권한이 없습니다.");
        }
        this.commentService.updateComment(c, commentForm.getContent());
        return String.format("redirect:/article/detail/%s", c.getArticle().getId());
    }
    @GetMapping("/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public String delete(@PathVariable("id") Long id, Principal principal) {
        Comment c = this.commentService.getComment(id);
        if(!c.getWriter().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"삭제 권한이 없습니다.");
        }
        this.commentService.deleteComment(c);
        return String.format("redirect:/article/detail/%s", c.getArticle().getId());
    }
    
}