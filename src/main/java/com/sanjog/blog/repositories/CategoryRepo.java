package com.sanjog.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sanjog.blog.entities.Category;

public interface CategoryRepo extends JpaRepository<Category,Integer> {

}
