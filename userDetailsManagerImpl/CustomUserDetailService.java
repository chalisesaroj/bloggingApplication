package com.udemy.learn.blogging.userDetailsManagerImpl;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.udemy.learn.blogging.entity.User;
import com.udemy.learn.blogging.repository.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String userNameOrEmail) throws UsernameNotFoundException {
		User userdb = userRepository.findByUserNameOrEmail(userNameOrEmail, userNameOrEmail).orElseThrow();
		System.out.println(userdb);
		userdb.getRole().forEach((role) -> System.out.println(role.getName()));
		Collection<GrantedAuthority> auth = userdb.getRole().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());
		System.out.println(auth);
		org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User(
				userdb.getUserName(), userdb.getPassword(), true, true, true, true, auth);
		System.out.println(user);
		return user;
	}

}
