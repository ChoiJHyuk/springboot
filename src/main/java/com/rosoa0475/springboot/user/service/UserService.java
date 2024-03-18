package com.rosoa0475.springboot.user.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rosoa0475.springboot.exception.DataNotFoundException;
import com.rosoa0475.springboot.image.entity.ProfileImage;
import com.rosoa0475.springboot.user.entity.LocalUser;
import com.rosoa0475.springboot.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public LocalUser saveUser(String username, String password, String nickname){
        validDuplicatedUser(username, nickname);
        LocalUser u = new LocalUser();
        u.setUsername(username);
        u.setPassword(this.passwordEncoder.encode(password));
        u.setNickname(nickname);
        u.setCreatedAt(LocalDateTime.now());
        this.userRepository.save(u);
        return u;
    }

    public Integer getLastId() {
        List<LocalUser> users = this.userRepository.findAll();
        if(users.size()==0){
            return 0;
        }
        return users.get(users.size()-1).getId();
    }
    @SuppressWarnings("null")
    public LocalUser findUserWithId(Integer id) {
        Optional<LocalUser> oa = this.userRepository.findById(id);
        if(oa.isPresent()){
            return oa.get();
        } else{
            throw new DataNotFoundException("user not found");
        }
    }

    private void validDuplicatedUser(String username, String nickname){
        this.userRepository.findByUsernameOrNickname(username, nickname)
        .ifPresent((e) -> {
            throw new DataIntegrityViolationException("이미 존재하는 회원입니다.");
        });
    }

    public LocalUser getUser(String username){
        Optional<LocalUser> ou = this.userRepository.findByUsername(username);
        if(ou.isPresent()){
            return ou.get();
        }else {
            throw new DataNotFoundException("user not found");
        }
    }

    public void updateProfileImg(LocalUser user, ProfileImage profileImage){
        user.setProfileImage(profileImage);
        this.userRepository.save(user);
    }
}
