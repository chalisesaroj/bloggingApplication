package com.udemy.learn.blogging.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.udemy.learn.blogging.service.FileUploadService;

@RestController
public class FileController {
	@Autowired 
	FileUploadService fileUploadService;
	@PostMapping("/file")
	public ResponseEntity<String>uploadFile(@RequestParam MultipartFile image){
try {
	String path="static/images/";
	 String filename=fileUploadService.uploadfile(path, image);
	 return ResponseEntity.ok("File succesfully uploaded"+filename);
}catch(Exception e) {
	 return new ResponseEntity<>("Error uploading post",HttpStatus.INTERNAL_SERVER_ERROR);
}
		
		
	}
}
