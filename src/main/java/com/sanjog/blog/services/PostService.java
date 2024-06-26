package com.sanjog.blog.services;

import java.util.List;

import com.sanjog.blog.entities.Post;
import com.sanjog.blog.payloads.PostDTO;
import com.sanjog.blog.payloads.PostResponse;

public interface PostService {
	
	PostDTO createPost(PostDTO postDto, Integer categoryId, Integer UserId);
	PostDTO updatePost(PostDTO postDto, Integer postId);
	void deletePost(Integer postId);
	PostResponse getAllPosts(Integer pageNumber, Integer pageSize,String sortBy,String sortDir);
	PostDTO getPostById(Integer postId);
	List<PostDTO>getPostsByUser(Integer UserId);
	List<PostDTO>getPostsByCategory(Integer categoryId);
	List<PostDTO>searchPosts(String keyword);
	

}
