package com.clinkast.qcm.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.clinkast.qcm.entities.Role;
import com.clinkast.qcm.entities.User;

public class AppUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;

	// propriétés
	private User user;

	// constructeurs
	public AppUserDetails() {
	}

	public AppUserDetails(User user) {
		this.user = user;
	}

	// -------------------------interface
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		for (String role : user.getRolesString()) {
			authorities.add(new SimpleGrantedAuthority(role));
		}
		return authorities;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getLogin();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	// getters et setters
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
