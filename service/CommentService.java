package com.udemy.learn.blogging.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.udemy.learn.blogging.payload.CommentDto;


public interface CommentService {
	CommentDto createComment(long post_id,CommentDto CommentDto);
	List<CommentDto>findCommentByID(long post_id);
	CommentDto updateComment(long post_id,long comment_id,CommentDto CommentDto);
	void deleteComment(long post_id, long comment_id);
	

}
