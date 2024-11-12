package com.udemy.learn.blogging.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.udemy.learn.blogging.entity.User;
import com.udemy.learn.blogging.payload.LoginDto;
import com.udemy.learn.blogging.payload.RegisterDto;
import com.udemy.learn.blogging.payload.UserDto;

public interface AuthenticationService {
	String login(LoginDto loginDto);
	 void register(String registerDto,MultipartFile image) throws IOException;
	void passwordRequirementChecker(String password);
	public UserDto mapusertouserdto(User user);
	public UserDto getUser(String username);
	public void changePassword(String newpassword,String Code,String username);

}
