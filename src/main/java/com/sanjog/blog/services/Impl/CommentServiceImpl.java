package com.sanjog.blog.services.Impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanjog.blog.entities.Comment;
import com.sanjog.blog.entities.Post;
import com.sanjog.blog.exceptions.ResourceNotFoundException;
import com.sanjog.blog.payloads.CommentDTO;
import com.sanjog.blog.repositories.CommentRepo;
import com.sanjog.blog.repositories.PostRepo;
import com.sanjog.blog.services.CommentService;
@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PostRepo postRepo;
	
	@Override
	public CommentDTO createComment(CommentDTO commentdto, Integer postId) {
		Post post=this.postRepo.findById(postId)
				.orElseThrow(()-> new ResourceNotFoundException("post","postId",postId));
		Comment comment=this.modelMapper.map(commentdto, Comment.class);
		comment.setPost(post);
		Comment savedC=this.commentRepo.save(comment);
		return this.modelMapper.map(savedC, CommentDTO.class);
	}

	@Override
	public void delete(Integer commentId) {
		// TODO Auto-generated method stub
		Comment c=this.commentRepo.findById(commentId)
				.orElseThrow(()->new ResourceNotFoundException("comment","commentid",commentId));
		this.commentRepo.delete(c);
	}

}
