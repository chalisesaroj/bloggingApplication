package com.udemy.learn.blogging.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.udemy.learn.blogging.entity.Post;
import com.udemy.learn.blogging.payload.PostDto;
@Repository
public interface PostRepository extends JpaRepository<Post, Long>{
List<Post>findByDateCreatedBetween(LocalDateTime startDate,LocalDateTime endDate);

@Query("SELECT p FROM Post p " +
	       "JOIN p.author u " +  // Join the User entity within the Post entity
	       "WHERE " +
	       "p.content LIKE CONCAT('%', :query, '%') OR " +
	       "p.id LIKE CONCAT('%', :query, '%') OR " +
	       "p.description LIKE CONCAT('%', :query, '%') OR " +
	       "p.title LIKE CONCAT('%', :query, '%') OR " +
	       "u.userName LIKE CONCAT('%', :query, '%') OR " +
	       "u.name LIKE CONCAT('%', :query, '%') OR " +
	       "u.email LIKE CONCAT('%', :query, '%')")

Page<Post> searchPost(String query, Pageable pageable);
@Query(value="SELECT p.* from post p JOIN user u on p.author_id=u.id WHERE u.id=:userid",nativeQuery=true)	     
Page<Post> searchPostByUserId(@Param("userid") long userid, Pageable pageable);



@Query("SELECT p FROM Post p " +
	       "JOIN p.category u " +  // Join the User entity within the Post entity
	       "WHERE " +
	       "u.name = :categoryName")
Page<Post> searchPostByCategoryName(String categoryName, Pageable pageable);
@Query("SELECT p FROM Post p " +
	       "WHERE " +
	       "p.id = :postid")
Page<Post> searchPostByPostId(long postid, Pageable pageable);
}
