package com.udemy.learn.blogging.serviceimpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.udemy.learn.blogging.entity.Comment;
import com.udemy.learn.blogging.entity.Post;
import com.udemy.learn.blogging.exception.RescourceNotFound;
import com.udemy.learn.blogging.payload.CommentDto;
import com.udemy.learn.blogging.repository.CommentRepository;
import com.udemy.learn.blogging.repository.PostRepository;
import com.udemy.learn.blogging.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private PostRepository postRepository;

	@Override
	public CommentDto createComment(long post_id, CommentDto CommentDto) {
		// get post object by ID
		Post foundPost = postRepository.findById(post_id).orElseThrow();
			Comment comment = mapToEntity(CommentDto);
			comment.setPost(foundPost);
			Comment newComment = commentRepository.save(comment);
			return mapToDto(newComment);
	}

	/// Converting entity to dto
	private CommentDto mapToDto(Comment comment) {
		return new CommentDto(comment.getId(), comment.getEmailId(), comment.getName(), comment.getBody(),comment.getDateCreated());
	}

	// Converting dto to entity

	private Comment mapToEntity(CommentDto commentDto) {
		Comment comment=new Comment();
		comment.setBody(commentDto.getBody());
		comment.setEmailId(commentDto.getEmail_id());
		comment.setId(commentDto.getId());
		comment.setName(commentDto.getName());
		return comment;
	}

	@Override
	public List<CommentDto> findCommentByID(long post_id) {
		List<Comment> listofcomment = commentRepository.findByPostId(post_id);
		System.out.println(listofcomment);

		return listofcomment.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
	}

	@Override
	public CommentDto updateComment(long post_id, long comment_id, CommentDto commentDto) {
		Comment comment = commentRepository.findById(comment_id).orElseThrow();
		Post post = postRepository.findById(post_id).orElseThrow();
		comment.setBody(commentDto.getBody());
		comment.setEmailId(commentDto.getEmail_id());
		comment.setName(commentDto.getName());
		Comment newComment = commentRepository.save(comment);
		return mapToDto(newComment);
	}

	@Override
	public void deleteComment(long post_id, long comment_id) {
		Post post=postRepository.findById(post_id).
				orElseThrow(()->new RescourceNotFound
						("Post ID "+post_id +" doesnot exists"));
		
		Comment comment=commentRepository.findById(comment_id).
				orElseThrow(()->new RescourceNotFound
						("Comment ID "+comment_id +" doesnot exists"));
		
		if (post_id!=comment.getPost().getId()) {
			throw new RescourceNotFound
			("comment id "+ comment_id +" doesnot belongs to postid "
					+ " "+post_id);
		}
		commentRepository.deleteById(comment_id);

	}
}
  