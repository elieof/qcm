package com.clinkast.qcm.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.clinkast.qcm.entities.User;
import com.clinkast.qcm.security.UserService;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-spring-config-dao.xml", 
	"classpath:test-spring-config-services.xml"})
public class UserServiceTest {

    @Autowired
    private UserService userService;
    
    @Test
    public void testCreateUser() throws Exception {
	String[] roleNames = {"admin", "rh", "user"};
	User user = new User("olivercrock@yahoo.fr", "olivercrock");
	for(String roleName : roleNames){
	    user.getRolesString().add("ROLE_"+roleName.toUpperCase());
	}
	User newUser = userService.createUser(user);
	assertTrue(newUser!=null);
	assertTrue(newUser.getId()!=null);
    }
}
