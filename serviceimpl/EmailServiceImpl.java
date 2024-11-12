package com.udemy.learn.blogging.serviceimpl;

//Importing required classes

import java.io.File;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;



import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.udemy.learn.blogging.entity.EmailDetails;
import com.udemy.learn.blogging.entity.User;
import com.udemy.learn.blogging.exception.DataMismatch;
import com.udemy.learn.blogging.exception.RescourceNotFound;
import com.udemy.learn.blogging.repository.UserRepository;
import com.udemy.learn.blogging.service.EmailService;

//Annotation
@Service
//Class
//Implementing EmailService interface
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	UserRepository userRepository;

	@Value("${spring.mail.username}")
	private String sender;

	// Method 1
	// To send a simple email
	@Override
	public boolean checkifcodematches(String code,String email) {
		User user = userRepository.findByEmail(email).orElseThrow(()->new RescourceNotFound("Provided email id is not registered"));
		if(!user.getResetCode().equals(code)) {
			throw new DataMismatch("Code doesnot matches");
		}
		if(user.getCodeexpirytime().isBefore(LocalDateTime.now())) {
			throw new DataMismatch("Code Expired");
		}
		
		return (user.getResetCode().equals(code)&&user.getCodeexpirytime().isAfter(LocalDateTime.now()));
		
	}
	public String sendSimpleMail(EmailDetails details) {

		// Try block to check for exceptions
		try {

			// Creating a simple mail message
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			String code = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8);
			User user = userRepository.findByEmail(details.getRecipient()).orElseThrow(()->new RescourceNotFound("Provided email id is not registered"));
			System.out.println(user);
			user.setResetCode(code);
			user.setCodeexpirytime(calculateExpiryTime());
			userRepository.save(user);

			// Setting up necessary details
			mailMessage.setFrom(sender);
			mailMessage.setTo(details.getRecipient());
			mailMessage.setText("Your password reset code is    " + code + " The code will expire in 5 minutes");
			mailMessage.setSubject("Reset Password: SarojdaikoBlog");

			// Sending the mail
			javaMailSender.send(mailMessage);
			return "mail sent";
		}

		// Catch block to handle the exceptions
		catch (Exception e) {
			return e.getMessage();
		}
	}
    public static LocalDateTime calculateExpiryTime() {
        // Set the expiry time (e.g., 10 minutes from now)
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiryTime = now.plus(10, ChronoUnit.MINUTES); // Adjust duration if needed
        return expiryTime;
    }

	@Override
	public String sendMailWithAttachment(EmailDetails details) {
		// TODO Auto-generated method stub
		return null;
	}

}
//Step 5: Creating a Rest Controller EmailController which defines various API for se