package com.sanjog.blog.services;

import com.sanjog.blog.payloads.CommentDTO;

public interface CommentService {
	CommentDTO createComment(CommentDTO commentdto,Integer postId);
	void delete(Integer commentId);
}
