package com.clinkast.qcm.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.clinkast.qcm.entities.User;
import com.clinkast.qcm.exception.ApiException;
import com.clinkast.qcm.exception.ApiInvalidParameterException;
import com.clinkast.qcm.exception.ServiceException;
import com.clinkast.qcm.security.UserService;
import com.clinkast.qcm.services.dto.ChangePassword;
import com.clinkast.qcm.services.dto.ResetPassword;
import com.clinkast.qcm.services.dto.UserProfile;

@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    //private static final Logger LOGGER = LoggerFactory.getLogger(AppUserDetailsService.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value="/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public User createUser(@RequestBody @Validated User user,HttpServletRequest request) throws ApiException {

	try {
	    //String baseUrl = String.format("%s://%s:%d/tasks/",request.getScheme(),  request.getServerName(), request.getServerPort());

	    return userService.createUser(user);
	} catch (ServiceException e) {
	    throw new ApiInvalidParameterException(e.getMessage());
	}
    }

    @RequestMapping(value="/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAllUsers() {
	return userService.getUsers();
    }


    @RequestMapping(value="/{id:[\\d]+}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public User updateUser(@PathVariable("id") int userId,
	    @RequestBody @Validated UserProfile profile) throws ApiException {
	try {
	    return userService.updateProfile(userId, profile);
	} catch (ServiceException e) {
	    throw new ApiInvalidParameterException(e.getMessage());
	}
    }
    
    @RequestMapping(value="/{id}/password", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void changePassword(@PathVariable("id") int userId,
	    @RequestBody @Validated ChangePassword userPassword) throws ApiException {
	try {
	    userService.changePassword(userId, userPassword);
	} catch (ServiceException e) {
	    throw new ApiInvalidParameterException(e.getMessage());
	}
    }
    
    @RequestMapping(value="/password-link", method = RequestMethod.PUT)
    public void resetPasswordLink(@RequestParam("login") String login) throws ApiException {
	try {
	    userService.resetPasswordLink(login);
	} catch (ServiceException e) {
	    throw new ApiInvalidParameterException(e.getMessage());
	}
    }
    
    @RequestMapping(value="/password", method = RequestMethod.PUT)
    public void resetPassword(@RequestBody @Validated ResetPassword userPassword) throws ApiException {
	try {
	    userService.resetPassword(userPassword);
	} catch (ServiceException e) {
	    throw new ApiInvalidParameterException(e.getMessage());
	}
    }



    @RequestMapping(value="/delete/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable("id") int userRoleId) {
	userService.deleteUser(userRoleId);
    }


    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public User getUserById(@PathVariable("id") int userId) {
	return userService.findById(userId);
    }

}
