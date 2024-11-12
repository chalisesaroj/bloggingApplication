package com.udemy.learn.blogging.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.udemy.learn.blogging.entity.EmailDetails;
import com.udemy.learn.blogging.service.EmailService;


@RestController

public class EmailController {

 @Autowired 
 private EmailService emailService;


 @PostMapping("/sendMail")
 public String
 sendMail(@RequestBody EmailDetails details)
 {

     String code
         = emailService.sendSimpleMail(details);
  
     
     return code;
 }

 // Sending email with attachment
 @PostMapping("/sendMailWithAttachment")
 public String sendMailWithAttachment(
     @RequestBody EmailDetails details)
 {
     String status
         = emailService.sendMailWithAttachment(details);

     return status;
 }
}
//Step 6: Running the Spring Boot Application and hitting http://localhos

