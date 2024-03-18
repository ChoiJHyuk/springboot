package com.rosoa0475.springboot.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rosoa0475.springboot.user.entity.LocalUser;

public interface UserRepository extends JpaRepository<LocalUser, Integer>{
    public Optional<LocalUser> findByUsernameOrNickname(String username, String nickname);
    public Optional<LocalUser> findByUsername(String username);
}
