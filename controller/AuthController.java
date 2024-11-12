package com.udemy.learn.blogging.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.udemy.learn.blogging.entity.EmailDetails;
import com.udemy.learn.blogging.entity.User;
import com.udemy.learn.blogging.exception.RescourceNotFound;
import com.udemy.learn.blogging.exception.UserNameAlreadyExists;
import com.udemy.learn.blogging.payload.JwtAuthResponse;
import com.udemy.learn.blogging.payload.LoginDto;
import com.udemy.learn.blogging.payload.RegisterDto;
import com.udemy.learn.blogging.payload.StrongPasswordChecker;
import com.udemy.learn.blogging.payload.UserDto;
import com.udemy.learn.blogging.repository.UserRepository;
import com.udemy.learn.blogging.service.AuthenticationService;
import com.udemy.learn.blogging.service.EmailService;
import com.udemy.learn.blogging.service.FileUploadService;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	private EmailService emailService;
	@Autowired
	AuthenticationService authenticationService;
	@Autowired
	UserRepository userRepository;
	@Autowired
	private FileUploadService fileUploadService;

	@PostMapping("/login")
	@PreAuthorize("permitAll()")
	public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto) {
		JwtAuthResponse jwtauthresponse = new JwtAuthResponse();
		try {
			String token = authenticationService.login(loginDto);

			jwtauthresponse.setAccessToken(token);
			return new ResponseEntity<>(jwtauthresponse, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("the error is" + e.getMessage());
			return new ResponseEntity<>(jwtauthresponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/findUserDetails")

	public ResponseEntity<UserDto> finduserdetails(@RequestParam String username) {
		return new ResponseEntity<>(authenticationService.getUser(username), HttpStatus.OK);

	}

	@GetMapping("/findUserid")

	public ResponseEntity<Long> finduserid(@RequestParam String username) {
		return new ResponseEntity<>(userRepository.findByUserName(username).orElseThrow().getId(), HttpStatus.OK);

	}

	@GetMapping("/findName")

	public ResponseEntity<String> findName(@RequestParam String username) {
		User user = userRepository.findByUserName(username).orElseThrow();
		return new ResponseEntity<>(user.getName(), HttpStatus.OK);

	}

	@GetMapping("/findEverything")
	public ResponseEntity<UserDto> findEverything(@RequestParam String username) {
		try {
			User user = userRepository.findByUserName(username)
					.orElseThrow(() -> new RescourceNotFound("username is invalid"));
			UserDto userdto = authenticationService.mapusertouserdto(user);
			return new ResponseEntity<>(userdto, HttpStatus.OK);
		} catch (RescourceNotFound e) {
			return new ResponseEntity<>(new UserDto(), HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping("/findProfession")
	public ResponseEntity<String> findProfession(@RequestParam String username) {
		User user = userRepository.findByUserName(username).orElseThrow();
		return new ResponseEntity<>(user.getProfession(), HttpStatus.OK);

	}

	@GetMapping("/findInterest")
	public ResponseEntity<String> findInterest(@RequestParam String username) {
		User user = userRepository.findByUserName(username).orElseThrow();
		return new ResponseEntity<>(user.getInterestAndExpertise(), HttpStatus.OK);

	}

	@GetMapping("/findUserimageid")

	public ResponseEntity<String> finduserimageid(@RequestParam String username) {
		User user = userRepository.findByUserName(username).orElseThrow();
		return new ResponseEntity<>(user.getImageId(), HttpStatus.OK);

	}

	@PostMapping(value = "/register")
	public ResponseEntity<String> register(@RequestParam String registerDto, @RequestParam MultipartFile image) {
		try {
			// authenticationService.passwordRequirementChecker(registerDto.getPassword());
			authenticationService.register(registerDto, image);

			return new ResponseEntity<>("Successfully Registered", HttpStatus.OK);
		} catch (UserNameAlreadyExists e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT); // Conflict status for existing user
		} catch (Exception e) {
			return new ResponseEntity<>("Registration failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/sendMail")
	public String sendMail(@RequestBody EmailDetails details) {

		String status = emailService.sendSimpleMail(details);

		return status;
	}

	@GetMapping("/checkcode")
	public String sendMail(@RequestParam String recipientemail, String code) {
		try {
			boolean result = emailService.checkifcodematches(code, recipientemail);

		} catch (Exception e) {
			return e.getMessage();
		}

		return "Code matched";
	}

	@GetMapping("/changepassword")
	public String changePassword(@RequestParam String newpassword, @RequestParam String Code,
			@RequestParam String email) {
		try {
			authenticationService.changePassword(newpassword, Code, email);
			return "Password Changed";
		} catch (Exception e) {
			return e.getMessage();
		}

	}

}
