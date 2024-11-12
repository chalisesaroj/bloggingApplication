package com.udemy.learn.blogging.serviceimpl;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.udemy.learn.blogging.entity.User;
import com.udemy.learn.blogging.exception.DataAlreadyExists;
import com.udemy.learn.blogging.exception.PasswordRequirementNotMet;
import com.udemy.learn.blogging.exception.RescourceNotFound;
import com.udemy.learn.blogging.exception.UserNameAlreadyExists;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.udemy.learn.blogging.configuration.JwtTokenProvider;
import com.udemy.learn.blogging.entity.Role;
import com.udemy.learn.blogging.payload.LoginDto;
import com.udemy.learn.blogging.payload.RegisterDto;
import com.udemy.learn.blogging.payload.RoleDto;
import com.udemy.learn.blogging.payload.StrongPasswordChecker;
import com.udemy.learn.blogging.payload.UserDto;
import com.udemy.learn.blogging.repository.RoleRepository;
import com.udemy.learn.blogging.repository.UserRepository;
import com.udemy.learn.blogging.service.AuthenticationService;
import com.udemy.learn.blogging.service.EmailService;
import com.udemy.learn.blogging.service.FileUploadService;

@Service

public class AuthImpl implements AuthenticationService {
	private String path = "C:/Users/chali/Desktop/blogdefn/routing-app/public";
	private AuthenticationManager authenticationManager;
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private PasswordEncoder passwordEncoder;
	private StrongPasswordChecker checker;
	private FileUploadService fileUploadService;
	private ObjectMapper mapper;
	private EmailService emailService;
	private JwtTokenProvider tokenProvider;

	public AuthImpl(AuthenticationManager authenticationManager, UserRepository userRepository,
			RoleRepository roleRepository, PasswordEncoder passwordEncoder, StrongPasswordChecker checker,
			ObjectMapper mapper, EmailService emailService, FileUploadService fileUploadService,
			JwtTokenProvider tokenProvider) {
		super();
		this.authenticationManager = authenticationManager;
		this.passwordEncoder = passwordEncoder;
		this.roleRepository = roleRepository;
		this.userRepository = userRepository;
		this.checker = checker;
		this.fileUploadService = fileUploadService;
		this.mapper = mapper;
		this.emailService = emailService;
		this.tokenProvider = tokenProvider;
	}

	@Override
	public UserDto mapusertouserdto(User user) {
		UserDto userDto = new UserDto();
		userDto.setName(user.getName());
		userDto.setId(user.getId());
		userDto.setEmail(user.getEmail());
		userDto.setUserName(user.getUserName());
		userDto.setInterestAndExpertise(user.getInterestAndExpertise());
		userDto.setProfession(user.getProfession());
		userDto.setImageId(user.getImageId());
		// userDto.setPosts(user.getPost());
		return userDto;

	}

	@Override
	public void changePassword(String newpassword, String Code, String email) {
		boolean codecheck = emailService.checkifcodematches(Code, email);

		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new RescourceNotFound("Provided email id is not registered"));
		user.setPassword(passwordEncoder.encode(newpassword));
		userRepository.save(user);

	}

	@Override
	public String login(LoginDto loginDto) throws AuthenticationException {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginDto.getUserNameOrEmail(), loginDto.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = tokenProvider.generateToken(authentication);
		return token;
	}

	@Override
	@Transactional
	public void register(String registerdto, MultipartFile image) throws IOException {
		RegisterDto registerDto = mapper.readValue(registerdto, RegisterDto.class);

		if (userRepository.existsByEmail(registerDto.getEmail())) {
			throw new UserNameAlreadyExists("Email already exists");
		}
		if (userRepository.existsByUserName(registerDto.getUserName())) {
			throw new UserNameAlreadyExists("Username already exists");
		}

		User user = new User();
		String filename = fileUploadService.uploadfile(this.path, image);
		System.out.println(registerDto.getInterstAndExpertise());
		System.out.println(registerDto.getProfession());
		user.setImageId(filename);
		user.setEmail(registerDto.getEmail());
		user.setName(registerDto.getName());
		user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
		user.setUserName(registerDto.getUserName());
		user.setProfession(registerDto.getProfession());
		user.setInterestAndExpertise(registerDto.getInterstAndExpertise());
		userRepository.save(user);

		Set<Role> rolesToAssociate = new HashSet<>();
		for (Role role : registerDto.getRole()) {
			Role associaterole = roleRepository.findByName(role.getName())
					.orElseGet(() -> roleRepository.save(new Role(role.getName())));
			rolesToAssociate.add(associaterole);
		}
		user.setRole(rolesToAssociate);
		user.setImageId(filename);
		userRepository.save(user);

	}

	@Override
	public void passwordRequirementChecker(String password) {
		if (!checker.isStrongPassword(password)) {
			throw new PasswordRequirementNotMet("The password Requirement doesnot meet");
		}
	}

	@Override
	public UserDto getUser(String username) {
		User user = userRepository.findByUserNameOrEmail(username, username).orElseThrow();
		UserDto userdto = mapusertouserdto(user);
		return userdto;
	}

}
