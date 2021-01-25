package com.example.Wallet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.Wallet.configuration.JwtTokenUtil;
import com.example.Wallet.entity.JwtRequest;
import com.example.Wallet.entity.JwtResponse;
import com.example.Wallet.service.JwtUserDetailsService;
// Using Spring Authentication Manager we authenticate the username and password.
@RestController
@CrossOrigin
public class JwtAuthController {

	//If the credentials are valid, a JWT token is created using the JWTTokenUtil and provided to the client. 
	 @Autowired
	    private AuthenticationManager authenticationManager;

	    @Autowired
	    private JwtTokenUtil jwtTokenUtil;

	    @Autowired
	    private JwtUserDetailsService userDetailsService;

	    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

	        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

	        final UserDetails userDetails = userDetailsService
	                .loadUserByUsername(authenticationRequest.getUsername());

	        final String token = jwtTokenUtil.generateToken(userDetails);

	        return ResponseEntity.ok(new JwtResponse(token));
	    }

	    private void authenticate(String username, String password) throws Exception {
	        try {
	            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
	        } catch (DisabledException e) {
	            throw new Exception("USER_DISABLED", e);
	        } catch (BadCredentialsException e) {
	            throw new Exception("INVALID_CREDENTIALS", e);
	        }
	    }

}
