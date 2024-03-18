package com.rosoa0475.springboot.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rosoa0475.springboot.user.entity.LocalUser;
import com.rosoa0475.springboot.user.form.UserRole;
import com.rosoa0475.springboot.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService{
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<LocalUser> _user = this.userRepository.findByUsername(username);
        if(_user.isEmpty()){
            throw new UsernameNotFoundException("가입하지 않은 사용자입니다.");
        }

        LocalUser localUser = _user.get();

        List<GrantedAuthority> authorities = new ArrayList<>();
        if("admin".equals(username)){
            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getCode()));
        }else {
            authorities.add(new SimpleGrantedAuthority(UserRole.USER.getCode()));
        }

        return new User(localUser.getUsername(), localUser.getPassword(), authorities);
    }
    
}
