package com.example.Wallet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//when the ResourceNotFoundException is thrown, the Spring MVC framework will handle
// the exception based on its default exception handling mechanism.
// It will look for an appropriate exception handler method to handle the exception and
// determine the response status code. If no custom exception handler is found,
// the response will have an HTTP status code of 500 (Internal Server Error) by default.

//the response sent back to the client will have an HTTP status code of 404 (Not Found).
// The error message will be included in the response body.
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	public ResourceNotFoundException(String message){
		super(message);
		
	}
	

}
