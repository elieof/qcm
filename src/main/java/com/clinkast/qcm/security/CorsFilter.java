package com.clinkast.qcm.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;


class CorsFilter extends OncePerRequestFilter  {

    public static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
    public static final String ACCESS_CONTROL_ALLOW_CREDENTIALS = "Access-Control-Allow-Credentials";
    public static final String ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";
    public static final String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";
    public static final String ACCESS_CONTROL_MAX_AGE = "Access-Control-Max-Age";
    public static final String ORIGIN = "Origin";
    public static final String ACCESS_CONTROL_REQUEST_METHOD = "Access-Control-Request-Method";
    public static final String ACCESS_CONTROL_EXPOSE_HEADERS = "Access-Control-Expose-Headers";
    public static final String ACCESS_CONTROL_REQUEST_HEADERS = "Access-Control-Request-Headers";

    public static final boolean ALLOWED_CREDENTIALS = true;
    public static final String ALLOWED_METHODS = "GET, POST, PUT, DELETE, OPTIONS, PATCH";
    public static final String ALLOWED_HEADERS = "Origin, Content-type, Accept, Authorization, X-Auth-Token";
    public static final String ALLOWED_ORIGIN = "*"; // allow all origins

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException,
    IOException {
	String origin = request.getHeader(ORIGIN);
	if (origin != null)
	{	    
	    response.setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, origin);
	    
	    if (request.getMethod().equalsIgnoreCase("OPTIONS"))
		{
		    preflight(origin, request, response);
		}
	}
	
	if (ALLOWED_CREDENTIALS) response.setHeader(ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
	response.setHeader(ACCESS_CONTROL_MAX_AGE, "3600");

	filterChain.doFilter(request, response);
    }
    protected void preflight(String origin, HttpServletRequest request, HttpServletResponse response) {
	response.setStatus(HttpServletResponse.SC_OK);
	response.setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, origin);
	response.setHeader(ACCESS_CONTROL_MAX_AGE, "3600");
	if (ALLOWED_CREDENTIALS) response.setHeader(ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
	String requestMethods = request.getHeader(ACCESS_CONTROL_REQUEST_METHOD);
	if (requestMethods != null)
	{
	    if (ALLOWED_METHODS != null)
	    {
		requestMethods = ALLOWED_METHODS;
	    }
	    response.setHeader(ACCESS_CONTROL_ALLOW_METHODS, requestMethods);
	}
	String allowHeaders = request.getHeader(ACCESS_CONTROL_REQUEST_HEADERS);
	if (allowHeaders != null)
	{
	    if (ALLOWED_HEADERS != null)
	    {
		allowHeaders = ALLOWED_HEADERS;
	    }
	    response.setHeader(ACCESS_CONTROL_ALLOW_HEADERS, allowHeaders);
	}
	
	try {
	    response.flushBuffer();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }

}