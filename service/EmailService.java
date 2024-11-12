
package com.udemy.learn.blogging.service;

import com.udemy.learn.blogging.entity.EmailDetails;


public interface EmailService {


 String sendSimpleMail(EmailDetails details);
	public boolean checkifcodematches(String code,String email);

 
 String sendMailWithAttachment(EmailDetails details);
}
