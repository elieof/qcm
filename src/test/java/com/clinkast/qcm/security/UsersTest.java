package com.clinkast.qcm.security;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.clinkast.qcm.entities.Role;
import com.clinkast.qcm.entities.User;
import com.clinkast.qcm.repositories.UserRepository;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:test-spring-config-dao.xml","classpath:test-spring-config-security.xml"})
public class UsersTest {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AppUserDetailsService appUserDetailsService;

	@Test
	public void findAllUsersWithTheirRoles() {
	    List<User> users = userRepository.findAll();
		for (User user : users) {
			System.out.println(user);
			display("Roles :", userRepository.getRoles(user.getId()));	
		}
	}

	@Test
	public void findUserByLogin() {
		// on récupère l'utilisateur [admin]
		User user = userRepository.findByLogin("admin");
		// on vérifie que son mot de passe est [admin]
		Assert.assertTrue(BCrypt.checkpw("admin", user.getPassword()));
		// on vérifie le rôle de admin / admin
		List<Role> roles = userRepository.getRoles("admin", user.getPassword());
		Assert.assertEquals(1, roles.size());
		Assert.assertEquals("ROLE_ADMIN", roles.get(0).getName());
	}

	@Test
	public void loadUserByUsername() {
		// on récupère l'utilisateur [admin]
		AppUserDetails userDetails = (AppUserDetails) appUserDetailsService.loadUserByUsername("admin");
		// on vérifie que son mot de passe est [admin]
		Assert.assertTrue(BCrypt.checkpw("admin", userDetails.getPassword()));
		// on vérifie le rôle de admin / admin
		@SuppressWarnings("unchecked")
		List<SimpleGrantedAuthority> authorities = (List<SimpleGrantedAuthority>) userDetails.getAuthorities();
		Assert.assertEquals(1, authorities.size());
		Assert.assertEquals("ROLE_ADMIN", authorities.get(0).getAuthority());
	}

	// méthode utilitaire - affiche les éléments d'une collection
	private void display(String message, Iterable<?> elements) {
		System.out.print(message);
		for (Object element : elements) {
			System.out.print(" "+element);
		}
		System.out.println("");
	}
}
