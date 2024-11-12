package com.udemy.learn.blogging.payload;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
	long id;
	String email_id;
	String name;
	String body;
	LocalDateTime dateCreated;
}
