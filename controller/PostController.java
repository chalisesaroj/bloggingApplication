package com.udemy.learn.blogging.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.udemy.learn.blogging.entity.Post;
import com.udemy.learn.blogging.payload.LoginDto;
import com.udemy.learn.blogging.payload.PageResponse;
import com.udemy.learn.blogging.payload.PostDto;
import com.udemy.learn.blogging.payload.PostUpdateResponse;
import com.udemy.learn.blogging.repository.PostRepository;
import com.udemy.learn.blogging.service.PostService;
import com.udemy.learn.blogging.serviceimpl.PostServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/post")
public class PostController {
	@Autowired
	private PostRepository postRepository;
	@Autowired
	private PostService postService;

	/**
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@GetMapping("/byDate")
	public List<PostDto> findByDate(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {

		// Convert the LocalDate values to LocalDateTime with time set to midnight
		LocalDateTime startDateTime = startDate.atStartOfDay();
		LocalDateTime endDateTime = endDate.atStartOfDay().plusHours(23).plusMinutes(59).plusSeconds(59);

		List<Post> listofPostsBetweenDates = postRepository.findByDateCreatedBetween(startDateTime, endDateTime);

		return listofPostsBetweenDates.stream().map(p -> postService.mapToDto(p)).collect(Collectors.toList());
	}

	/**
	 * This method is used to search the post object.Any post having matching
	 * keyword in content/description/title of the post will be returned
	 * 
	 * @param keyword
	 * @return
	 */
	@GetMapping("/searchPost")
	public PageResponse searchbykeyword(
			@RequestParam(value = "keyword", defaultValue = "", required = false) String keyword,
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
			@RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection) {

		return postService.searchbykeyword(keyword, pageNumber, pageSize, sortBy, sortDirection);
	}

	/**
	 * This method is used to create a new post Post object is provided in request
	 * Body
	 * 
	 * @param postDto
	 * @return
	 */

	@PostMapping()
	public ResponseEntity<String> createPost(@Valid @RequestBody PostDto postDto) {
		try {
			postService.createPost(postDto);
			return new ResponseEntity<>("Succesfully Created", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping("/createlike")
	public ResponseEntity<String> createLike(@RequestParam long post_id, @RequestParam long user_id) {

		postService.createlike(post_id, user_id);
		return new ResponseEntity<>("Succesfully Liked", HttpStatus.OK);

	}

	
	
	
	@GetMapping("/findByCategoryName")
	public PageResponse findByCategoryName (
			@RequestParam String categoryName,
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
			@RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection) {
 

	
		return postService.findPostByCategoryName(categoryName, pageNumber, pageSize, sortBy, sortDirection);

	}
	
	@GetMapping("/findByPostId")
	public PageResponse findByPostId (
			@RequestParam long postid,
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
			@RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection) {
 

	
		return postService.searchPostByPostId(postid, pageNumber, pageSize, sortBy, sortDirection);

	}
	/**
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @param sortBy
	 * @param sortDirection
	 * @return
	 */
	@GetMapping("/allPosts")
	public PageResponse getPost(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
			@RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection) {

		return postService.getAllPost(pageNumber, pageSize, sortBy, sortDirection);
	}

	/**
	 * This method is used to get post from the id.
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public PostDto getPostbyId(@PathVariable("id") long id) {

		return postService.getPostById(id);

	}

	/**
	 * This method is used to update the existing post
	 * 
	 * @param postDto
	 * @param id
	 * @return
	 */
	@PutMapping("/{id}")
	public PostUpdateResponse updatePostbyId(@Valid @RequestBody PostDto postDto, @PathVariable("id") long id) {
		return postService.updatePost(postDto, id);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletebyId(@PathVariable("id") long id) {
		try {
			postService.deletePost(id);
			return new ResponseEntity<>("Succesfully Deleted", HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("error om deleting");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	@GetMapping("/findByUsername")
	public PageResponse searchPostbyUsername(
			@RequestParam(value = "username", defaultValue = "", required = true) String username,
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
			@RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection) {

		return postService.searchPostByUsername(username, pageNumber, pageSize, sortBy, sortDirection);
	}


}
