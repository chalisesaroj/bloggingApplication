package com.udemy.learn.blogging.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.udemy.learn.blogging.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment,Long> {
	List<Comment>findByPostId(long id);

}
