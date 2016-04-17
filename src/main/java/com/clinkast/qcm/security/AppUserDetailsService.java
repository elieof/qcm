package com.clinkast.qcm.security;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.clinkast.qcm.entities.Role;
import com.clinkast.qcm.entities.User;
import com.clinkast.qcm.exception.ApiException;
import com.clinkast.qcm.exception.ServiceException;
import com.clinkast.qcm.repositories.RoleRepository;
import com.clinkast.qcm.repositories.UserRepository;
import com.clinkast.qcm.services.api.MailService;
import com.clinkast.qcm.services.dto.ChangePassword;
import com.clinkast.qcm.services.dto.ResetPassword;
import com.clinkast.qcm.services.dto.UserProfile;
import com.clinkast.qcm.utils.ObjectUtils;


@Service
public class AppUserDetailsService implements UserDetailsService, UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppUserDetailsService.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private MailService mailService;

    @Value("${appli.url}")
    private String appliUrl;
        
    @Value("${reset.password.url}")
    private String resetPasswordLink;
    
    @Value("${jwt.reset.token.validity:1}")
    private Integer tokenValidity;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
	// on cherche l'utilisateur via son login
	User user = userRepository.findByLogin(login);
	// trouvé ?
	if (user == null) {
	    LOGGER.debug(String.format("login [%s] inexistant", login));
	    throw new UsernameNotFoundException(String.format("login [%s] inexistant", login));
	}
	// on rend les détails de l'utilsateur
	return new AppUserDetails(user);
    }

    public User createUser(User user) throws ServiceException{
	// l'utilisateur existe-t-il déjà ?
	if(userRepository.findByLogin(user.getLogin())==null){
	    //user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
	    user.setPassword(passwordEncoder.encode(user.getPassword()));
	    //send an email
	    //mailService.sendSubscriptionMail(user.getLogin(), user.getFirstname(), appliUrl);
	    //set Date
	    user.setCreatedAt(new Date());
	    user.setUpdatedAt(new Date());
	    user.setEnabled(true);
	    Set<Role> roles = new HashSet<Role>();
	    LOGGER.debug("User roles :");
	    for(String name : user.getRolesString()){
		LOGGER.debug(name);
		Role role = roleRepository.findRoleByName(name);
		if(role!=null) roles.add(role);
	    }
	    if(!roles.isEmpty()){
		user.getRoles().clear();
		user.getRoles().addAll(roles);
		return user= userRepository.save(user);
	    }
	    throw new ServiceException("Error while creating user : invalid roles values");	
	}
	throw new ServiceException("Error while creating user : a user with that login already exist");
    }

    public List<User> findAll(){
	return userRepository.findAll();
    }


    @Override
    public void deleteUser(int userId) {
	User user = findById(userId);
	if(user!=null && user.isEnabled())
	    user.setEnabled(false);

    }

    @Override
    public List<User> getUsers() {
	return userRepository.findAll();
    }

    @Override
    public User updateUser(int userId, User user) {
	User userOld = findById(userId);
	if(userOld!=null){
	    ObjectUtils.myCopyProperties(user, userOld, "createAt", "updateAt", "roles");	
	    userOld.setUpdatedAt(new Date());
	}
	return userRepository.save(userOld);
    }


    @Override
    public User findById(int userId) {	
	return userRepository.findOne(userId);
    }

    @Override
    public User updateProfile(int userId, UserProfile profile) throws ServiceException{
	if(userRepository.updateProfile(userId, profile)>0)
	    return findById(userId);
	throw new ServiceException("Error while updating user : User not found");
    }

    @Override
    public void changePassword(int userId, ChangePassword userPassword) throws ServiceException{

	String oldPassword = userRepository.getPassword(userId);
	if(!StringUtils.isEmpty(oldPassword) && oldPassword.equals(userPassword.getOldPassword()))
	    userRepository.updatePassword(userId, passwordEncoder.encode(userPassword.getNewPassword()));
	else 
	    throw new ServiceException(ApiException.getString("password.old"));

    }

    @Override
    public void resetPassword(ResetPassword userPassword) throws ServiceException {
	
	userRepository.updatePasswordByLogin(userPassword.getNewPassword());

    }

    @Override
    public void resetPasswordLink(String login) throws ServiceException {
	// TODO send email link -- generate token 
	User user = userRepository.findByLogin(login);
	if(user!=null){
	    TokenManager tokenManager = new TokenManager();
	    String token = tokenManager.generateTokenForExpiration(new AppUserDetails(user), tokenValidity);
	    String username = user.getFirstname()!=null?user.getFirstname():user.getLogin();
	    mailService.sendResetPasswordMail(user.getLogin(), username, resetPasswordLink+token);
	}
    }


}
