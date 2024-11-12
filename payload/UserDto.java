package com.udemy.learn.blogging.payload;

import java.util.Set;

import com.udemy.learn.blogging.entity.Post;
import com.udemy.learn.blogging.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
	long id;
	String name;
	String userName;
	String email;
	String password;
	String profession;
	String interestAndExpertise;
	String imageId;

	Set<RoleDto>role;
	Set<Post>posts;
	Set<Post>likedPosts;

}
