package com.rosoa0475.springboot.image.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rosoa0475.springboot.image.entity.ProfileImage;

public interface ImageRepository extends JpaRepository<ProfileImage, Long>{
    
}
