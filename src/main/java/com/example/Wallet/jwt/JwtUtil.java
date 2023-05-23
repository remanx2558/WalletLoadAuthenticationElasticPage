package com.example.Wallet.jwt;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

/**
 * This class handles token generation, validation, and extraction of user details from the token.
 */
@Service
public class JwtUtil {


	private String secret;// Represents the secret key used for signing and verifying the JWT.
	private int jwtExpirationInMs=5 * 60 * 60;//// Represents the expiration time of the JWT in milliseconds.

	//Use @Value annotation to inject the value of the jwt.secret property from external configuration into the secret variable.Two ways of using are mentioned here.
	@Value("${jwt.secret}") //its value is stored in application.properties file
	public void setSecret(String secret) {
		this.secret = secret;
	}
	

	public void setJwtExpirationInMs(@Value("${jwt.expirationDateInMs}") int jwtExpirationInMs) {
		this.jwtExpirationInMs = jwtExpirationInMs;
	}

	// generate token for user

	//he UserDetails interface is part of the Spring Security framework and represents the user details
	// required for authentication and authorization. It contains information such as the user's username, password, and authorities (roles).
	/**
	 * Generates a token for the given user details.
	 * It creates a claims map to store additional information in the token, such as user roles.
	 *
	 * @param userDetails The user details.
	 * @return The generated JWT token.
	 */
	public String generateToken(UserDetails userDetails) {
		//It creates a claims map to store additional information in the token, such as user roles.
		Map<String, Object> claims = new HashMap<>();
		//The getAuthorities() method returns a collection of GrantedAuthority objects, which represent the authorities/roles assigned to the user.
		// The returned collection typically contains instances of the SimpleGrantedAuthority class,
		// which is a basic implementation of the GrantedAuthority interface.

		//By using the wildcard ? extends GrantedAuthority, the code allows for flexibility in the type of authorities returned by the getAuthorities() method.
		// It allows the collection to contain any class that extends the GrantedAuthority interface, providing compatibility
		// with different implementations or custom authority classes.
		Collection<? extends GrantedAuthority> roles = userDetails.getAuthorities();
		if (roles.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
			claims.put("isAdmin", true);
		}
		if (roles.contains(new SimpleGrantedAuthority("ROLE_USER"))) {
			claims.put("isUser", true);
		}
		return doGenerateToken(claims, userDetails.getUsername());
	}

	/**
	 * Generates a JWT token using the provided claims and subject (user identifier).
	 * It sets the issued and expiration dates for the token.
	 * The token is signed using the HMAC-SHA512 algorithm with the secret key.
	 * The compact token string is returned.
	 *
	 * @param claims  The claims to include in the token.
	 * @param subject The subject (user identifier) for the token.
	 * @return The generated JWT token.
	 */
	private String doGenerateToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs)).signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	/**
	 * Validates the authenticity and integrity of the given JWT token.
	 * It attempts to parse and verify the token using the secret key.
	 * If the token is valid, it returns true; otherwise, it throws a BadCredentialsException with an appropriate message.
	 *
	 * @param authToken The JWT token to validate.
	 * @return True if the token is valid, false otherwise.
	 * @throws BadCredentialsException If the token is invalid or expired.
	 */
	public boolean validateToken(String authToken) {
		 //final String username = getUsernameFromToken(authToken);
	      // return (username.equals(userDetails.getUsername()) && !isTokenExpired(authToken));
	   
		try {
			// Jwt token has not been tampered with
			Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
			throw new BadCredentialsException("INVALID_CREDENTIALS", ex);
		} catch (ExpiredJwtException ex) {
			throw new BadCredentialsException("INVALID_CREDENTIALS", ex);
		}
	}

	/**
	 * Extracts the username (subject) from the given JWT token.
	 * It parses the token, retrieves the claims, and returns the subject.
	 *
	 * @param token The JWT token.
	 * @return The username extracted from the token.
	 */
	public String getUsernameFromToken(String token) {
		Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		return claims.getSubject();
	}

	/**
	 * Extracts the user roles from the given JWT token.
	 * It parses the token, retrieves the claims, and checks if the "isAdmin" or "isUser" flag is present.
	 * Based on the flags, it creates and returns a list of SimpleGrantedAuthority objects representing the user roles.
	 *
	 * @param authToken The JWT token.
	 * @return The list of user roles extracted from the token.
	 */
	public List<SimpleGrantedAuthority> getRolesFromToken(String authToken) {
		List<SimpleGrantedAuthority> roles = null;
		Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken).getBody();
		Boolean isAdmin = claims.get("isAdmin", Boolean.class);
		Boolean isUser = claims.get("isUser", Boolean.class);
		if (isAdmin != null && isAdmin == true) {
			roles = Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}
		if (isUser != null && isUser == true) {
			roles = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
		}
		return roles;
	}

	

}