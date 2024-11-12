package com.udemy.learn.blogging.payload;

import java.time.LocalDateTime;
import java.util.List;

import com.udemy.learn.blogging.entity.Post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
	long Id;
	String name;
	String Description;
	LocalDateTime dateCreated;

	
	
}
