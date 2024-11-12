package com.udemy.learn.blogging.payload;

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
public class PageResponse {
	List<PostDto> contents;
	int pageNumber;
	int pageSize;
	int noOfPage;
	int noOfElements;
	boolean isLast;
}
