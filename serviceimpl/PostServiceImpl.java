package com.udemy.learn.blogging.serviceimpl;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.udemy.learn.blogging.entity.Category;
import com.udemy.learn.blogging.entity.Post;
import com.udemy.learn.blogging.entity.User;
import com.udemy.learn.blogging.exception.RescourceNotFound;
import com.udemy.learn.blogging.payload.PageResponse;
import com.udemy.learn.blogging.payload.PostDto;
import com.udemy.learn.blogging.payload.PostUpdateResponse;
import com.udemy.learn.blogging.payload.UserDto;
import com.udemy.learn.blogging.repository.CategoryRepository;
import com.udemy.learn.blogging.repository.PostRepository;
import com.udemy.learn.blogging.repository.UserRepository;
import com.udemy.learn.blogging.service.AuthenticationService;
import com.udemy.learn.blogging.service.CategoryService;
import com.udemy.learn.blogging.service.CommentService;
import com.udemy.learn.blogging.service.PostService;

@Service
public class PostServiceImpl implements PostService {
	@Autowired
	PostRepository postRepository;
	@Autowired
	CommentService commentService;
	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	CategoryService categoryService;
	@Autowired
	AuthenticationService authenticationService;

	@Override
	@Transactional
	public void createPost(PostDto postDto) {
		Post post = mapToEntity(postDto);
		Post savedPost = postRepository.save(post);
		Optional<User> userPost = userRepository.findByUserNameOrEmail(postDto.getUser(), postDto.getUser());
		savedPost.setAuthor(userPost.get());
		
		if (postDto.getCategory() != null) {
			Optional<Category> category = categoryRepository.findByName(postDto.getCategory().getName());
			if (category.isPresent()) {
				savedPost.setCategory(category.get());
				postRepository.save(savedPost);
			}

		}
	}


	public User mapToEntityUser(UserDto userDto) {
		User user = new User();
		user.setEmail(userDto.getEmail());
		user.setName(userDto.getName());
		user.setPassword(userDto.getPassword());
		return null;
	}

	@Override
	public PostDto mapToDto(Post post) {
		PostDto postDto = new PostDto();
		postDto.setContent(post.getContent());
		postDto.setDescription(post.getDescription());
		postDto.setTitle(post.getTitle());
		postDto.setId(post.getId());
		postDto.setDateCreated(post.getDateCreated());
		postDto.setListOfComment(commentService.findCommentByID(post.getId()));
		postDto.setUser(post.getAuthor().getUserName());
		postDto.setLikedBy(post.getLikedby().stream().map((eachrole)->authenticationService.mapusertouserdto(eachrole)).collect(Collectors.toList()));
		Category category=categoryRepository.findByName(post.getCategory().getName()).get();
		postDto.setCategory(categoryService.maptoDto(category));
		return postDto;
	}

	@Override
	public Post mapToEntity(PostDto postDto) {
		Post post = new Post();
		post.setContent(postDto.getContent());
		post.setDescription(postDto.getDescription());
		post.setTitle(postDto.getTitle());
		post.setId(postDto.getId());

		return post;
	}

	@Override
	public PostDto getPostById(long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new RescourceNotFound(
				"Post ID " + id + " doesnot exists in your database. Invailid post_id error"));
		return mapToDto(post);
	}

	@Override
	public PageResponse getAllPost(int pageNumber, int pageSize, String sortBy, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> page = postRepository.findAll(pageable);
		List<Post> listOfPost = page.getContent();
		PageResponse pageResponse = new PageResponse();
		
		List<PostDto>listofPostDto=listOfPost.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
		pageResponse.setLast(page.isLast());
		pageResponse.setNoOfElements(page.getNumberOfElements());
		pageResponse.setNoOfPage(page.getTotalPages());
		pageResponse.setPageSize(page.getSize());
		pageResponse.setContents(listofPostDto);
		return pageResponse;

	}

	@Override
	public PostUpdateResponse updatePost(PostDto postDto, long postId) {
		Post post = postRepository.findById(postId).orElseThrow(() -> new RescourceNotFound(
				"Post ID " + postId + " doesnot exists in your database. invalid post id error"));
		post.setContent(postDto.getContent());
		post.setDescription(postDto.getDescription());
		post.setTitle(postDto.getTitle());
		Post updatedPost = postRepository.save(post);

		PostDto updatedpostDto = mapToDto(updatedPost);
		return new PostUpdateResponse(updatedpostDto, "Succesfully Updated");

	}

	@Override
	public PageResponse searchbykeyword(String keyword,int pageNumber, int pageSize, String sortBy, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> page = postRepository.searchPost(keyword,pageable);
		List<Post> listOfPost = page.getContent();
		PageResponse pageResponse = new PageResponse();
		
		List<PostDto>listofPostDto=listOfPost.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
		pageResponse.setLast(page.isLast());
		pageResponse.setNoOfElements(page.getNumberOfElements());
		pageResponse.setNoOfPage(page.getTotalPages());
		pageResponse.setPageSize(page.getSize());
		pageResponse.setContents(listofPostDto);
		return pageResponse;

	
	}

	@Override
	public void deletePost(long post_id) {
		Post post = postRepository.findById(post_id)
				.orElseThrow(() -> new RescourceNotFound("Post ID " + post_id + " doesnot exists.Nothing to delete"));
		System.out.println(post.getContent());
		postRepository.delete(post);

	}

	@Override
	public void createlike(long post_id, long user_id) {
	User user=userRepository.findById(user_id).orElseThrow();
	Post post=postRepository.findById(post_id).orElseThrow();
	Set<User>listOfUser=post.getLikedby();
	listOfUser.add(user);
	post.setLikedby(listOfUser);
	postRepository.save(post);
	}

	@Override
	public PageResponse searchPostByUsername(String username,int pageNumber, int pageSize, String sortBy, String sortDirection) {
		User user=userRepository.findByUserNameOrEmail(username, username).orElseThrow();
		long userid=user.getId();
		Sort sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> page = postRepository.searchPostByUserId(userid,pageable);
		List<Post> listOfPost = page.getContent();
		PageResponse pageResponse = new PageResponse();
		
		List<PostDto>listofPostDto=listOfPost.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
		pageResponse.setLast(page.isLast());
		pageResponse.setNoOfElements(page.getNumberOfElements());
		pageResponse.setNoOfPage(page.getTotalPages());
		pageResponse.setPageSize(page.getSize());
		pageResponse.setContents(listofPostDto);
		return pageResponse;

	}


	@Override
	public PageResponse findPostByCategoryName(String categoryName, int pageNumber, int pageSize, String sortBy,
			String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> page = postRepository.searchPostByCategoryName(categoryName,pageable);
		List<Post> listOfPost = page.getContent();
		PageResponse pageResponse = new PageResponse();
		
		List<PostDto>listofPostDto=listOfPost.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
		pageResponse.setLast(page.isLast());
		pageResponse.setNoOfElements(page.getNumberOfElements());
		pageResponse.setNoOfPage(page.getTotalPages());
		pageResponse.setPageSize(page.getSize());
		pageResponse.setContents(listofPostDto);
		return pageResponse;


	}


	@Override
	public PageResponse searchPostByPostId(long postid, int pageNumber, int pageSize, String sortBy,
			String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> page = postRepository.searchPostByPostId(postid,pageable);
		List<Post> listOfPost = page.getContent();
		PageResponse pageResponse = new PageResponse();
		
		List<PostDto>listofPostDto=listOfPost.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
		pageResponse.setLast(page.isLast());
		pageResponse.setNoOfElements(page.getNumberOfElements());
		pageResponse.setNoOfPage(page.getTotalPages());
		pageResponse.setPageSize(page.getSize());
		pageResponse.setContents(listofPostDto);
		return pageResponse;

	}


	


	
}
