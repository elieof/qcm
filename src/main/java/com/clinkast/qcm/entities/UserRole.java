package com.clinkast.qcm.entities;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


//@Entity
//@Table(name = "USERS_ROLES")
public class UserRole extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	// un UserRole référence un User
	@ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
	@JoinColumn(name = "USER_ID")
	private User user;
	// un UserRole référence un Role
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ROLE_ID")
	private Set<Role> roles;

	// constructeurs
	public UserRole() {

	}

	public UserRole(User user, Set<Role> roles) {
		this.user = user;
		this.roles = roles;
	}

	// getters et setters

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRole(Set<Role> roles) {
		this.roles = roles;
	}
}
