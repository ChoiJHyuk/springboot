package com.rosoa0475.springboot.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rosoa0475.springboot.comment.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>{
    
}
