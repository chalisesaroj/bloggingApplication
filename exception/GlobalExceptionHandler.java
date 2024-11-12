package com.udemy.learn.blogging.exception;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.udemy.learn.blogging.payload.ErrorDetails;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(RescourceNotFound.class)
	public ResponseEntity<ErrorDetails>RescourceNotFoundHandler
	(WebRequest webrequest,RescourceNotFound exception){
		ErrorDetails errorDetails =new ErrorDetails
				(new Date(),exception.getMessage(),webrequest.getDescription(false));
	return new ResponseEntity<>(errorDetails,HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(DataAlreadyExists.class)
	public ResponseEntity<ErrorDetails>DataAlreadyExistsHandler
	(WebRequest webrequest,DataAlreadyExists exception){
		ErrorDetails errorDetails =new ErrorDetails
				(new Date(),exception.getMessage(),webrequest.getDescription(false));
	return new ResponseEntity<>(errorDetails,HttpStatus.NOT_ACCEPTABLE);
	}
	 @ExceptionHandler(MethodArgumentNotValidException.class)
	    public ResponseEntity<Map<String, List<String>>> handleValidationErrors(MethodArgumentNotValidException ex) {
	        List<String> errors = ex.getBindingResult().getFieldErrors()
	                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
	        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
	    }

	    private Map<String, List<String>> getErrorsMap(List<String> errors) {
	        Map<String, List<String>> errorResponse = new HashMap<>();
	        errorResponse.put("errors", errors);
	        return errorResponse;
	    }

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails>GlobalExceptionHandlermethod
	(WebRequest webrequest,Exception exception){
		ErrorDetails errorDetails =new ErrorDetails
				(new Date(),exception.getMessage(),webrequest.getDescription(true));
	return new ResponseEntity<>(errorDetails,HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
