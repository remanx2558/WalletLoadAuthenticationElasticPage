package com.example.Wallet.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//This annotation indicates that this class will handle HTTP requests
// and return the response as JSON. It combines the @Controller and @ResponseBody annotations.
@RestController
public class AuthenticationController {
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@Autowired
	private JwtUtil jwtTokenUtil;


	//This method handles the HTTP POST request at the URL "/authenticate". It expects a request body in the form of an AuthenticationRequest object.
	// It also throws an exception if there is an error during authentication.
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);

		//The method returns a ResponseEntity object with the JWT token wrapped in an AuthenticationResponse object.
		// The ResponseEntity.ok method indicates a successful response with the provided token.
		return ResponseEntity.ok(new AuthenticationResponse(token));
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> saveUser(@RequestBody UserDTO user) throws Exception {
		//It also throws an exception if there is an error while saving the user.,,,
		return ResponseEntity.ok(userDetailsService.save(user));
	}

}
/**
	The sequence of request handling would typically be as follows:

		The client sends an authentication request to the /authenticate endpoint, providing the username and password in the request body.

		The request is intercepted by CustomJwtAuthenticationFilter, which validates the JWT token.

		If the token is valid, the request proceeds to the AuthenticationController's createAuthenticationToken method.

		In the createAuthenticationToken method, the provided username and password are authenticated using the AuthenticationManager.

		If the authentication is successful, the UserDetailsService is called to load the user details.

		The JwtUtil generates a new JWT token based on the user details.

		The generated token is returned as an AuthenticationResponse in the response body.

		For registration, the client sends a request to the /register endpoint with the user details in the request body.

		The AuthenticationController's saveUser method is called, which delegates the user saving to CustomUserDetailsService.

		The saved user is returned in the response body.

 */