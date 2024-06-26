package com.sanjog.blog.services.Impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sanjog.blog.entities.Category;
import com.sanjog.blog.entities.User;
import com.sanjog.blog.entities.Post;
import com.sanjog.blog.exceptions.ResourceNotFoundException;
import com.sanjog.blog.payloads.ApiResponse;
import com.sanjog.blog.payloads.PostDTO;
import com.sanjog.blog.payloads.PostResponse;
import com.sanjog.blog.repositories.CategoryRepo;
import com.sanjog.blog.repositories.PostRepo;
import com.sanjog.blog.repositories.UserRepo;
import com.sanjog.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	private PostRepo postRepo;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private CategoryRepo categoryRepo;
	

	@Override
	public PostDTO createPost(PostDTO postDto,Integer userId, Integer categoryId) {
		// TODO Auto-generated method stub
		User u=this.userRepo.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("User", "userId", userId));
		Category c=this.categoryRepo.findById(categoryId)
				.orElseThrow(()->new ResourceNotFoundException("Category", "categoryId", categoryId));;
		
		Post p=this.modelMapper.map(postDto, Post.class);
		p.setImageName("default.png");
		p.setAddedDate(new Date());
		p.setCategory(c);
		p.setUser(u);
		Post newPost=this.postRepo.save(p);
		return this.modelMapper.map(newPost , PostDTO.class);
	}

	@Override
	public PostDTO updatePost(PostDTO postDto, Integer postId) {
		// TODO Auto-generated method stub
		Post p=this.postRepo.findById(postId)
				.orElseThrow(()->new ResourceNotFoundException("post","post id",postId));
		p.setTitle(postDto.getTitle());
		p.setContent(postDto.getContent());
		p.setImageName(postDto.getImageName());
		Post newpost=this.postRepo.save(p);
		return this.modelMapper.map(newpost, PostDTO.class);
	}

	@Override
	public void deletePost(Integer postId) {
		// TODO Auto-generated method stub
		
		this.postRepo.deleteById(postId);
		

	}
//
	@Override
	public PostResponse getAllPosts(Integer pageNumber, Integer pageSize,String sortBy,String sortDir) {
		// TODO Auto-generated method stub
		Sort sort=(sortDir.equalsIgnoreCase("asc"))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		
		Pageable p=PageRequest.of(pageNumber, pageSize, sort);
		Page<Post>pagePost=this.postRepo.findAll(p);
		List<Post>list=pagePost.getContent();
		List<PostDTO>postdtos=new ArrayList<>();
		for(Post p0:list) {
			postdtos.add(this.modelMapper.map(p0, PostDTO.class));
		}
		PostResponse postResponse=new PostResponse();
		postResponse.setContent(postdtos);
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setTotalEelements(pagePost.getTotalElements());
		postResponse.setLastPage(pagePost.isLast());
		
		return postResponse;
	}

	@Override
	public PostDTO getPostById(Integer postId) {
		// TODO Auto-generated method stub
		Post p=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("post","post id",postId));
		return this.modelMapper.map(p, PostDTO.class) ;
	}

	@Override
	public List<PostDTO> getPostsByUser(Integer UserId) {
		// TODO Auto-generated method stub
		User u=this.userRepo.findById(UserId)
				.orElseThrow(()->new ResourceNotFoundException("user","user id", UserId));
		List<Post>posts=this.postRepo.findByUser(u);
		List<PostDTO>postdtos=new ArrayList<>();
		for(Post p:posts) {
			postdtos.add(this.modelMapper.map(p, PostDTO.class));
		}
		return postdtos;
		
	}
	@Override
	public List<PostDTO> getPostsByCategory(Integer categoryId) {
		// TODO Auto-generated method stub
		Category cat=this.categoryRepo.findById(categoryId)
				.orElseThrow(()->new ResourceNotFoundException("category","category id", categoryId));
		List<Post>posts=this.postRepo.findByCategory(cat);
		List<PostDTO>postdtos=new ArrayList<>();
		for(Post p:posts) {
			postdtos.add(this.modelMapper.map(p, PostDTO.class));
		}
		return postdtos;
	}

	@Override
	public List<PostDTO> searchPosts(String keywords) {
		// TODO Auto-generated method stub
		List<Post>list=this.postRepo.searchByTitle("%"+keywords+"%");
		List<PostDTO>postdtos=new ArrayList<>();
		for(Post o:list)postdtos.add(this.modelMapper.map(o, PostDTO.class));
		return postdtos;
	}

}
