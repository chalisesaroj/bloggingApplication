package com.udemy.learn.blogging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.udemy.learn.blogging.userDetailsManagerImpl.CustomUserDetailService;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication
@OpenAPIDefinition(
info=@Info(
		title="spring boot blogging rest api",
		description="Description of spring boot",
		version="v1.0.0",
		contact=@Contact(
				name="Saroj Chalise",
				email="chalise@gmail.com",
				url="https://tradengine.com.np"
				),
		license=@License(
				name="Apache 2.0",
				url="https://tradengine.com.np")
		
		)
)
public class BloggingApplication {


	public static void main(String[] args) {
//		 // Create a Spring application context
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
//
//        // Register the configuration class that contains your CustomUserDetailService bean
//        context.register(CustomUserDetailService.class); // Replace with your actual configuration class
//
//        // Refresh the context to load the beans
//        context.refresh();
//	CustomUserDetailService userDetailsService = context.getBean(CustomUserDetailService.class);
//	 String usernameOrEmail = "anjila"; // Replace with the actual username or email
//     try {
//         UserDetails userDetails = userDetailsService.loadUserByUsername(usernameOrEmail);
//         System.out.println("User details: " + userDetails);
//     } catch (UsernameNotFoundException e) {
//         System.err.println("User not found: " + e.getMessage());
//     }
		SpringApplication.run(BloggingApplication.class, args);
	}

}
