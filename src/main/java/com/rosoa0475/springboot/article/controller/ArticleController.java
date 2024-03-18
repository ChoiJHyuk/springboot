package com.rosoa0475.springboot.article.controller;

import java.security.Principal;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.rosoa0475.springboot.article.entity.Article;
import com.rosoa0475.springboot.article.form.ArticleForm;
import com.rosoa0475.springboot.article.service.ArticleService;
import com.rosoa0475.springboot.comment.form.CommentForm;
import com.rosoa0475.springboot.user.entity.LocalUser;
import com.rosoa0475.springboot.user.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;



@Controller
@RequiredArgsConstructor
@RequestMapping("/article")
public class ArticleController {
    
    private final ArticleService articleService;
    private final UserService userService;

    @GetMapping("/list")
    public String articleList(Model model, @RequestParam(value = "page", defaultValue = "0") int page, Principal principal) {
        Page<Article> paginator = this.articleService.getList(page);
        model.addAttribute("paginator", paginator);
        if(principal!=null){
            model.addAttribute("user", this.userService.getUser(principal.getName()));
        }
        return "/article/list";
    }

    @GetMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public String form(@ModelAttribute ArticleForm articleForm) {
        return "article/form";
    }

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public String create(@Valid ArticleForm articleForm, BindingResult bindingresult, Principal principal) {
        if(bindingresult.hasErrors()){
            return "article/form";
        }
        LocalUser writer = this.userService.getUser(principal.getName());
        this.articleService.saveArticle(articleForm.getSubject(), articleForm.getContent(), writer);
        return "redirect:/article/list";
    }

    @GetMapping("/modify/{id}")
    @PreAuthorize("isAuthenticated()")
    public String modify(@ModelAttribute ArticleForm articleForm, @PathVariable("id") Long id, Principal principal) {
        Article article = this.articleService.getArticle(id);
        if(!article.getWriter().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }
        articleForm.setSubject(article.getSubject());
        articleForm.setContent(article.getContent());
        return "article/form";
    }

    @PostMapping("/modify/{id}")
    @PreAuthorize("isAuthenticated()")
    public String modify(@Valid ArticleForm articleForm, BindingResult bindingResult, 
                            Principal principal, @PathVariable("id") Long id) {
        if (bindingResult.hasErrors()) {
            return "article/form";
        }
        Article aritcle = this.articleService.getArticle(id);
        if (!aritcle.getWriter().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }
        this.articleService.updateArticle(aritcle, articleForm.getSubject(), articleForm.getContent());
        return String.format("redirect:/article/detail/%s", id) ;
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public String delete(Principal principal, @PathVariable("id") Long id) {
        Article article = this.articleService.getArticle(id);
        if (!article.getWriter().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
        }
        this.articleService.deleteArticle(article);
        return "redirect:/";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Long id, CommentForm commentForm, Principal principal) {
        Article article = this.articleService.getArticle(id);
        model.addAttribute("article", article);
        if(principal!=null){
            LocalUser user = this.userService.getUser(principal.getName());
            model.addAttribute("isLiked", article.getLikes().contains(user));
        } else{
            model.addAttribute("isLiked", false);
        }
        return "article/detail";
    }

    @GetMapping("/like/{id}")
    @PreAuthorize("isAuthenticated()")
    public String like(@PathVariable("id") Long id, Principal principal) {
        Article article = this.articleService.getArticle(id);
        LocalUser user = this.userService.getUser(principal.getName());
        this.articleService.likeArticle(article, user);
        return String.format("redirect:/article/detail/%s", id);
    }
    
    
}
