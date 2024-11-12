package com.udemy.learn.blogging.service;

import java.util.List;

import com.udemy.learn.blogging.entity.Post;
import com.udemy.learn.blogging.payload.PageResponse;
import com.udemy.learn.blogging.payload.PostDto;
import com.udemy.learn.blogging.payload.PostUpdateResponse;

public interface PostService {
	void createPost(PostDto postDto);
	PostDto getPostById(long id);
PageResponse getAllPost(int pageNumber,int pageSize,String sortBy,String sortDirection);
	PostUpdateResponse updatePost(PostDto postDto,long postId);
	PostDto mapToDto(Post post);
	 PageResponse findPostByCategoryName(String categoryname,int pageNumber,int pageSize, String sortBy, String sortDirection) ;
	Post mapToEntity(PostDto postDto);
	PageResponse searchbykeyword(String keyword,int pageNumber, int pageSize, String sortBy, String sortDirection);
	void deletePost(long post_id);
	void createlike(long post_id,long user_id);
	PageResponse searchPostByUsername(String username, int pageNumber, int pageSize, String sortBy,
			String sortDirection);
	PageResponse searchPostByPostId(long postid, int pageNumber, int pageSize, String sortBy,
			String sortDirection);
	
	
}
