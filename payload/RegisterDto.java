package com.udemy.learn.blogging.payload;

import java.util.Set;

import com.udemy.learn.blogging.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {
	String name;
	String userName;
	String email;
	String password;
	Set<Role> role;
	String profession;
	String interstAndExpertise;

}
