 package com.sanjog.blog.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sanjog.blog.payloads.ApiResponse;
import com.sanjog.blog.payloads.PostDTO;
import com.sanjog.blog.payloads.PostResponse;
import com.sanjog.blog.services.FileService;
import com.sanjog.blog.services.PostService;

import jakarta.servlet.http.HttpServletResponse;



@RestController
@RequestMapping("/api/")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileservice;
	
	@Value("${project.image}")
	private String path;
	
	@PostMapping("/user/{userID}/category/{categoryID}/posts")
	public ResponseEntity<PostDTO>createPost(
			@RequestBody PostDTO postDto, @PathVariable Integer userID,
			@PathVariable Integer categoryID){
		PostDTO postdto=this.postService.createPost(postDto, userID, categoryID);
		return new ResponseEntity<PostDTO>(postdto,HttpStatus.CREATED);

}
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDTO>> getPostsByUser(@PathVariable Integer userId){
		List<PostDTO>list=this.postService.getPostsByUser(userId);
		return new ResponseEntity<List<PostDTO>>(list, HttpStatus.OK);
	}
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDTO>> getPostsByCategory(@PathVariable Integer categoryId){
		List<PostDTO>list=this.postService.getPostsByCategory(categoryId);
		return new ResponseEntity<List<PostDTO>>(list, HttpStatus.OK);
	}
	
	@DeleteMapping("/post/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId){
		this.postService.deletePost(postId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("deleted post",true), HttpStatus.OK);
	}
	
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value="pageNumber",defaultValue="0",required=false)Integer pageNumber,
			@RequestParam(value="pageSize",defaultValue="3",required=false)Integer pageSize,
			@RequestParam(value="sortBy",defaultValue="postId",required=false)String sortBy,
			@RequestParam(value="sortDir",defaultValue="asc",required=false)String sortDir
			){
		PostResponse list=this.postService.getAllPosts(pageNumber,pageSize,sortBy,sortDir);
		return new ResponseEntity<PostResponse>(list,HttpStatus.OK);
	}
	
	@GetMapping("/posts/{id}")
	public ResponseEntity<PostDTO> getPostById(@PathVariable Integer id){
		PostDTO p=this.postService.getPostById(id);
		return new ResponseEntity<PostDTO>(p,HttpStatus.OK);
	}
	
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO postdto, @PathVariable Integer postId){
		PostDTO p=this.postService.updatePost(postdto, postId);
		return new ResponseEntity<PostDTO>(p,HttpStatus.OK);
	}
	@GetMapping("/posts/search/{keywords}")
	public ResponseEntity<List<PostDTO>> searchByTitle(@PathVariable String keywords){
		List<PostDTO> p=this.postService.searchPosts(keywords);
		return new ResponseEntity<List<PostDTO>>(p,HttpStatus.OK);
	}
	@PostMapping("/posts/image/upload/{postId}")
	public ResponseEntity<PostDTO> uploadPostImage(
			@RequestParam("image")MultipartFile image,
			@PathVariable Integer postId
			) throws IOException{
		PostDTO postdto=this.postService.getPostById(postId);
		String FileName=this.fileservice.uploadImage(path, image);
		
		postdto.setImageName(FileName);
		PostDTO updatedPost=this.postService.updatePost(postdto, postId);
		return new ResponseEntity<PostDTO>(postdto,HttpStatus.OK);
		
	}
	@GetMapping(value="/posts/image/{imageName}",produces=MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable String imageName,HttpServletResponse response) throws IOException {
		InputStream resource=this.fileservice.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
	
	
}
