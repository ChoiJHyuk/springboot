package com.rosoa0475.springboot.user.controller;

import java.io.IOException;
import java.security.Principal;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rosoa0475.springboot.image.entity.ProfileImage;
import com.rosoa0475.springboot.image.form.ImageForm;
import com.rosoa0475.springboot.image.service.ImageService;
import com.rosoa0475.springboot.user.entity.LocalUser;
import com.rosoa0475.springboot.user.form.SignupForm;
import com.rosoa0475.springboot.user.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;



@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    private final ImageService imageService;

    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

    @GetMapping("/signup")
    public String form(@ModelAttribute SignupForm signupForm) {
        return "user/signup";
    }

    @PostMapping("/signup")
    public String create(@Valid SignupForm signupForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user/signup";
        }
        if (!signupForm.getPassword1().equals(signupForm.getPassword2())) {
            bindingResult.rejectValue("password2", "differentPassword",
                    "패스워드가 일치하지 않습니다.");
            return "user/signup";
        }
        try{
            this.userService.saveUser(signupForm.getUsername(), signupForm.getPassword1(), signupForm.getNickname());
            return "redirect:/";
        } catch(DataIntegrityViolationException e){
            bindingResult.reject("alreadyInUser", "중복된 아이디 또는 닉네임이 존재합니다.");
            return "user/signup";
        } catch(Exception e){
            log.error(e.getMessage(), e);
            e.printStackTrace();
            bindingResult.reject("unexpectedError", "알 수 없는 에러가 발생했습니다.");
            return "user/signup";
        }
    }

    @GetMapping("/mypage")
    @PreAuthorize("isAuthenticated()")
    public String mypage(Model model, Principal principal) {
        LocalUser user = this.userService.getUser(principal.getName());
        model.addAttribute("user", user);
        return "user/mypage";
    }
    
    @GetMapping("/modify")
    @PreAuthorize("isAuthenticated()")
    public String modify(ImageForm imageForm) {
        return "user/form";
    }
    
    @PostMapping("/modify")
    @PreAuthorize("isAuthenticated()")
    public String modify(ImageForm imageForm, BindingResult bindingResult, Principal principal) throws IOException {
        LocalUser user = this.userService.getUser(principal.getName());

        try{
            ProfileImage profileImage = this.imageService.uploadImage(imageForm.getImageFile());
            this.userService.updateProfileImg(user, profileImage);
        } catch(DataIntegrityViolationException e){
            bindingResult.reject("imageError", "이미지를 업로드해주세요.");
            return "user/form";
        }
        return "redirect:/user/mypage";
    }

}
