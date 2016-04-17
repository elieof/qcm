package com.clinkast.qcm.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.clinkast.qcm.exception.ApiErrorMessage;
import com.clinkast.qcm.exception.ApiAuthenticationException;

/**
 * {@link AuthenticationEntryPoint} that rejects all requests. Login-like function is featured
 * in {@link TokenAuthenticationFilter} and this does not perform or suggests any redirection.
 * This object is hit whenever user is not authorized (anonymous) and secured resource is requested.
 */
public final class UnauthorizedEntryPoint implements AuthenticationEntryPoint {
    
    //private static final Logger LOGGER = LoggerFactory.getLogger(UnauthorizedEntryPoint.class);

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
	
	   	
	    String unauthorizedError = new ApiErrorMessage(new ApiAuthenticationException()).jsonValue();
	    response.getWriter().write(unauthorizedError);
	    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	}
}