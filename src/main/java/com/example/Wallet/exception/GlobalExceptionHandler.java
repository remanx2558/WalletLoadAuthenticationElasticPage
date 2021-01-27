package com.example.Wallet.exception;

import java.sql.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest request){
		
		ErrorDetails errorDetails = 
				new ErrorDetails(new Date(0, 0, 0), exception.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}
	
	
	@ExceptionHandler(APIException.class)
	public ResponseEntity<?> handleAPIException(ResourceNotFoundException exception, WebRequest request){
		
		ErrorDetails errorDetails = 
				new ErrorDetails(new Date(0, 0, 0), exception.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}

	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleGlobalException(Exception exception, WebRequest request){
		
		ErrorDetails errorDetails = 
				new ErrorDetails(new Date(0, 0, 0), exception.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
		@ExceptionHandler(MethodArgumentNotValidException.class)
		public ResponseEntity<?> customValidationErrorHandling(MethodArgumentNotValidException exception){
			ErrorDetails errorDetails = new ErrorDetails(new Date(0, 0, 0),"validation error",exception.getBindingResult().getFieldError().getDefaultMessage());
			return new ResponseEntity<>(errorDetails,HttpStatus.BAD_REQUEST);
		}

	
	
	
}