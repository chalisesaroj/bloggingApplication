package com.udemy.learn.blogging.payload;

import java.time.LocalDateTime;
import java.util.List;

import com.udemy.learn.blogging.entity.Category;
import com.udemy.learn.blogging.entity.Comment;
import com.udemy.learn.blogging.entity.User;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
	private long id;
	@NotEmpty
	@Size(min=5, message ="Post-title must have at least two character")
	private String title;
	@NotEmpty
	@Size(min=2)
	private String description;
	@NotEmpty
	private String content;
	List<CommentDto> listOfComment;
	LocalDateTime dateCreated;
	CategoryDto category;
String user;
List<UserDto>likedBy;

}
