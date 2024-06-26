package com.sanjog.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sanjog.blog.entities.Category;
import com.sanjog.blog.entities.Post;
import com.sanjog.blog.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer> {
	List<Post>findByUser(User u);
	List<Post>findByCategory(Category cat);
	//List<Post>findByTitleContaining(String title); this is the data jpa method
	
//	// this is a custom method
	@Query("select p from Post p where p.Title like :key")
	List<Post>searchByTitle(@Param("key")String Title);
}
