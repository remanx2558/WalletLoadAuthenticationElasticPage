package com.example.Wallet.jwt;
import java.io.IOException;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This class handles unauthorized requests by providing an appropriate response.
 */@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	//

	/**
	 * Called when an unauthorized request is received. It handles the response to the unauthorized request.
	 * This method is overridden from the AuthenticationEntryPoint interface.
	 *
	 * @param request        The HTTP servlet request.
	 * @param response       The HTTP servlet response.
	 * @param authException  The authentication exception.
	 * @throws IOException      If an I/O error occurs while writing the response.
	 * @throws ServletException If any servlet-specific error occurs.
	 */
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {

		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		String message;
		// Check if the request as any exception that we have stored in Request
		final Exception exception = (Exception) request.getAttribute("exception");
		
		// If yes then use it to create the response message else use the authException
		if (exception != null) {
			/// Converts the exception to a JSON response message using the ObjectMapper and writes it to the response output stream.
// Creates a map with the key "cause" and the exception's string representation as the value.
// Serializes the map into a JSON byte array using the ObjectMapper.
// The resulting JSON byte array is written to the response output stream, providing standardized error details to the client.
// Collections.singletonMap is a utility method that creates an immutable map with a single key-value pair.
// The ObjectMapper class from the Jackson library is used for JSON data mapping between Java objects and JSON. It offers various JSON handling methods.
			byte[] body = new ObjectMapper().writeValueAsBytes(Collections.singletonMap("cause", exception.toString()));
			response.getOutputStream().write(body);
		} else {
// If no exception is stored, it retrieves the cause and message from the authException.
// Constructs a JSON response message and writes it to the response output stream
			if (authException.getCause() != null) {
				message = authException.getCause().toString() + " " + authException.getMessage();
			} else {
				message = authException.getMessage();
			}

// The byte[] type represents serialized JSON data as binary for HTTP responses.
// It is suitable when working with APIs or frameworks that expect binary data.
// Alternative representations like strings or deserialized Java objects may be more appropriate for manipulating JSON data.
			byte[] body = new ObjectMapper().writeValueAsBytes(Collections.singletonMap("error", message));

			response.getOutputStream().write(body);
		}
	}

}
