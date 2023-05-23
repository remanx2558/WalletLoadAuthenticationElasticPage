package com.example.Wallet.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import com.example.Wallet.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;


/**
 * This custom JWT authentication filter intercepts requests, extracts and validates the JWT token,
 * and sets the authenticated user in the Spring Security context.
 * By applying this filter, you can ensure that incoming requests with valid JWT tokens are properly authenticated and authorized.
 *
 *  Explanation:
 *
 *     The doFilterInternal method intercepts incoming requests and processes them.
 *     The extractJwtFromRequest method is called to extract the JWT token from the request header.
 *     The extracted token is checked for validity using the jwtTokenUtil.validateToken method.
 *     If the token is valid, the user details are extracted from the token using the jwtTokenUtil.getUsernameFromToken and jwtTokenUtil.getRolesFromToken methods.
 *     A UserDetails object is created with the extracted user details.
 *     An UsernamePasswordAuthenticationToken object is created with the UserDetails, null credentials, and the authorities.
 *     The authentication token is set in the security context using SecurityContextHolder.getContext().setAuthentication.
 *     If the token is invalid or validation fails, a message is printed.
 */
@Component
public class CustomJwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtTokenUtil;


	/**
	 * Intercepts incoming requests and processes them.
	 *
	 * @param request  the HTTP request
	 * @param response the HTTP response
	 * @param chain    the filter chain
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		try {

			// JWT Token is in the form "Bearer token". Remove Bearer word and get only the Token
			String jwtToken = extractJwtFromRequest(request);

			if (StringUtils.hasText(jwtToken) && jwtTokenUtil.validateToken(jwtToken)) {
				// Token is valid, extract user details and create an authentication token
				UserDetails userDetails = new User(jwtTokenUtil.getUsernameFromToken(jwtToken), "",
						jwtTokenUtil.getRolesFromToken(jwtToken));

				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				// After setting the Authentication in the context, we specify
				// that the current user is authenticated. So it passes the
				// Spring Security Configurations successfully.

				// Set the authentication token in the security context
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			} else {
				// Token is invalid, unable to set the security context
				logger.warn("Cannot set the Security Context");
			}

		} catch (ExpiredJwtException ex) {
			request.setAttribute("exception", ex);
			throw ex;
		} catch (BadCredentialsException ex) {
			request.setAttribute("exception", ex);
			throw ex;
		}
		//Finally, call the doFilter method of the FilterChain to continue processing the request:
		chain.doFilter(request, response);
	}

	/**
	 * Extracts the JWT token from the request header.
	 *
	 * @param request the HTTP request
	 * @return the extracted JWT token
	 * @throws InvalidTokenException if the token format is invalid
	 */
	private String extractJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7, bearerToken.length());
		}
		throw new InvalidTokenException("Token Does not Match the format");
	}

}
