package com.cards.exception;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.util.NoSuchElementException;

import org.hibernate.HibernateException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestControllerAdvice
public class HandleException extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { SQLIntegrityConstraintViolationException.class })
	public ResponseEntity<Object> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex,
			WebRequest request) {
		ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getLocalizedMessage());
		errorMessage.setMessage("Card already exists!");
		log.error("SQLIntegrityConstraintViolationException - Message: {}", errorMessage);
		
		return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(value = { NoSuchElementException.class })
	public ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException ex,
			WebRequest request) {
		ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getLocalizedMessage());
		errorMessage.setMessage("Card does not exists!");
		log.error("NoSuchElementException - Message: {}", errorMessage);
		
		return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(value = { HibernateException.class })
	public ResponseEntity<Object> handleHibernateException(HibernateException ex,
			WebRequest request) {
		ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getLocalizedMessage());
		errorMessage.setMessage("You can not change card number!");
		log.error("HibernateException - Message: {}", errorMessage);
		
		return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(value = { HttpClientErrorException.class })
	public ResponseEntity<Object> handleHttpClientErrorException(HttpClientErrorException ex,
			WebRequest request) {
		
		log.error("HttpClientErrorException - Message: {}", ex.getLocalizedMessage());
		
		return new ResponseEntity<>(ex.getLocalizedMessage(), new HttpHeaders(), HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(value = {ResourceAlreadyExistsException.class})
	public ResponseEntity<Object> handleResourceAlreadyExistsException(ResourceAlreadyExistsException ex){
		String message = ex.getMessage();
		log.error("ResourceAlreadyExistsException - Message: {}", message);
		
		return new ResponseEntity<Object>(message, HttpStatus.CONFLICT);
	}
	
	
}
