package com.udemy.learn.blogging.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.udemy.learn.blogging.entity.Comment;
import com.udemy.learn.blogging.payload.CommentDto;
import com.udemy.learn.blogging.service.CommentService;
import com.udemy.learn.blogging.serviceimpl.CommentServiceImpl;

@RestController
@RequestMapping("/api")
public class CommentController {
	@Autowired
	CommentServiceImpl commentserviceimplementation;
	@Autowired
	CommentService commentService;

	@PostMapping("/post/{post_id}/comments")
	public ResponseEntity<CommentDto> createComment(@PathVariable("post_id") long post_id,
			@RequestBody CommentDto commentDTO) {
		return new ResponseEntity(commentService.createComment(post_id, commentDTO), HttpStatus.CREATED);
	}

	@GetMapping("/post/{post_id}/comments")
	public List<CommentDto> readComment(@PathVariable("post_id") long post_id) {
		return commentService.findCommentByID(post_id);
	}
/**
 * This Methods Updates the Comments, with the given post_id and comment_id
 * as path variables
 * @param post_id
 * @param commentDto
 * @param comment_id
 * @return CommentDto Object
 */
	@PutMapping("/post/{post_id}/comments/{comment_id}")
	public CommentDto updateComment(@PathVariable("post_id") long post_id, @RequestBody CommentDto commentDto,
			@PathVariable("comment_id") long comment_id) {
		return commentService.updateComment(post_id, comment_id, commentDto);
	}

	@DeleteMapping("/post/{post_id}/comments/{comment_id}")
	public ResponseEntity<String> deleteComment(@PathVariable("post_id") long post_id,
			@PathVariable("comment_id") long comment_id) {
		commentService.deleteComment(post_id, comment_id);
		return new ResponseEntity("Succesfully Deleted", HttpStatus.OK);
	}
}
