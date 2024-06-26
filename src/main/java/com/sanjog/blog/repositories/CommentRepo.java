package com.sanjog.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sanjog.blog.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer>{

}
