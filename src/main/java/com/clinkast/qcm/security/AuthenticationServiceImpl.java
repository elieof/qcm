package com.clinkast.qcm.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;


public class AuthenticationServiceImpl implements AuthenticationService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(TokenManager.class);


    @Autowired
    private ApplicationContext applicationContext;

    private final AuthenticationManager authenticationManager;
    private final TokenManager tokenManager;

    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, TokenManager tokenManager) {
	this.authenticationManager = authenticationManager;
	this.tokenManager = tokenManager;
    }

    @Override
    public String authenticate(String login, String password) {
	// Here principal=username, credentials=password
	Authentication authentication = new UsernamePasswordAuthenticationToken(login, password);
	try {
	    authentication = authenticationManager.authenticate(authentication);
	    // Here principal=UserDetails (UserContext in our case), credentials=null (security reasons)
	    SecurityContextHolder.getContext().setAuthentication(authentication);

	    if (authentication.getPrincipal() != null) {
		AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
		String newToken = tokenManager.generateToken(userDetails);
		if (newToken == null) {
		    return null;
		}
		return newToken;
	    }
	} catch (AuthenticationException e) {
	    LOGGER.debug("Authentication error ", e);
	}
	return null;
    }

    @Override
    public boolean checkToken(String token) {
	AppUserDetails userDetails = tokenManager.parseToken(token);
	if (userDetails == null) {
		return false;
	}

	Authentication securityToken = new PreAuthenticatedAuthenticationToken(
		userDetails, null, userDetails.getAuthorities());
	SecurityContextHolder.getContext().setAuthentication(securityToken);

	return true;
    }

    @Override
    public void logout(String token) {
	UserDetails logoutUser = tokenManager.removeToken(token);
	System.out.println(" *** AuthenticationServiceImpl.logout: " + logoutUser);
	SecurityContextHolder.clearContext();
    }

    @Override
    public UserDetails currentUser() {
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	if (authentication == null) {
		return null;
	}
	return (UserDetails) authentication.getPrincipal();
    }

    @Override
    public String refreshToken(String token) {
	AppUserDetails userDetails = tokenManager.removeToken(token);
	return tokenManager.generateToken(userDetails);
    }

    @Override
    public String getTokenValidity() {
	// TODO Auto-generated method stub
	return tokenManager.getTokenValidity().toString();
    }

}
