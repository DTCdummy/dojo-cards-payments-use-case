package com.cognizant.customer.app.exceptions;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cognizant.customer.app.payload.ApiResponse;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {

	// ResourceNotFoundException
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
		String message = ex.getMessage();
		ApiResponse apiResponse = new ApiResponse(message, false);
		log.error("ResourceNotFoundException - Message: {}", message);
		
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
	}
	
	
	// MethodArgumentNotValidException
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
		Map<String, String> resp = new HashMap<>();
		resp.put("MESSAGE", "Validation Error");
		
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError)error).getField();
			String message = error.getDefaultMessage();
			
			resp.put(fieldName, message);
		});
		
		log.error("MethodArgumentNotValidException - Message: {}", ex.getMessage());
		log.error(resp.toString());
		
		return new ResponseEntity<Map<String, String>>(resp, HttpStatus.BAD_REQUEST);
	}
	
	
	// SQLIntegrityConstraintViolationException
	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public ResponseEntity<ApiResponse> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex){
		String message = ex.getMessage();
		message = message.substring(0, message.indexOf(" for key "));
		
		ApiResponse apiResponse = new ApiResponse(message, false);
		log.error("SQLIntegrityConstraintViolationException - Message: {}", message);
		
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.CONFLICT);
	}
	
	
	
}
