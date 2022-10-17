package com.payment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestControllerAdvice
public class HandleException {
	
	@ExceptionHandler(value = { HttpClientErrorException.class })
	public ResponseEntity<Object> handleHttpClientErrorException(HttpClientErrorException ex,
			WebRequest request) {
		
		log.error("HttpClientErrorException - Message: Invalid data, Payment failed!!!");
		return new ResponseEntity<>("Invalid data, Payment failed!!!", HttpStatus.CONFLICT);
	}
	
	
}
