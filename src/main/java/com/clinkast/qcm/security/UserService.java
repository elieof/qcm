package com.clinkast.qcm.security;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import com.clinkast.qcm.entities.User;
import com.clinkast.qcm.exception.ServiceException;
import com.clinkast.qcm.services.dto.ChangePassword;
import com.clinkast.qcm.services.dto.ResetPassword;
import com.clinkast.qcm.services.dto.UserProfile;

public interface UserService {
    
    User createUser(User user) throws ServiceException;
    

    @PreAuthorize("hasRole('ROLE_ADMIN') or #userId == principal.user.id")
    User updateUser(int userId, User user) throws ServiceException;
    

    @PreAuthorize("hasRole('ROLE_ADMIN') or #userId == principal.user.id")
    User updateProfile(int userId, UserProfile profile) throws ServiceException;
    

    @PreAuthorize("hasRole('ROLE_ADMIN') or #userId == principal.user.id")
    void changePassword(int userId, ChangePassword userPassword) throws ServiceException;
    
    void resetPassword(ResetPassword userPassword) throws ServiceException;
    
    void resetPasswordLink(String login) throws ServiceException;
        
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    void deleteUser(int userId);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    List<User> getUsers();

    @PreAuthorize("hasRole('ROLE_ADMIN') or #userId == principal.user.id")
    User findById(int userId);

}
