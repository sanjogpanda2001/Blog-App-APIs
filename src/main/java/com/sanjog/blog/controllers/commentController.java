package com.sanjog.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sanjog.blog.payloads.ApiResponse;
import com.sanjog.blog.payloads.CommentDTO;
import com.sanjog.blog.services.CommentService;

@RestController
@RequestMapping("/api/")
public class commentController {
	
	@Autowired
	private CommentService commentService;
	
	@PostMapping("/post/{postId}/comments")
	public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO commentdto,
			@PathVariable Integer postId){
		CommentDTO c=this.commentService.createComment(commentdto, postId);
		return new ResponseEntity<CommentDTO>(c,HttpStatus.CREATED);
	}
	
	
	@DeleteMapping("/post/{postId}/comments")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId){
		this.commentService.delete(commentId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("deleted comment",true),HttpStatus.CREATED);
	}
}
