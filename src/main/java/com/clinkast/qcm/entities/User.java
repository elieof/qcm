package com.clinkast.qcm.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Pattern;





import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "USERS")
public class User extends AbstractEntity {
    private static final long serialVersionUID = 1L;

    // propriétés
    @Size(min=2, max=40)
    //@AlphaNumeric
    private String firstname;

    @Size(min=2, max=40)
    //@Alphabetic
    private String lastname;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm a z", timezone="Europe/Paris")
    private Date createdAt;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm", timezone="Europe/Paris")
    private Date updatedAt;

    @JsonIgnore
    private boolean enabled;

    @NotNull
    @Email
    private String login;


    @Pattern.List({
	@Pattern(regexp = "(?=.*\\d).+", message = "{password.digit}"),
	@Pattern(regexp = "(?=.*[a-z]).+", message = "{password.lowercase}"),
	@Pattern(regexp = "(?=.*[A-Z]).+", message = "{password.uppercasecase}"),
	@Pattern(regexp = "(?=\\S+$).+", message = "{password.whitespace}")
    })
    @Length(min=6, max=16)
    @NotNull
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinTable(name="users_roles",  
    joinColumns={@JoinColumn(name="user_id", referencedColumnName="id")},  
    inverseJoinColumns={@JoinColumn(name="role_id", referencedColumnName="id")}) 
    private Set<Role> roles = new HashSet<Role>();

    @Transient
    private Set<String> rolesString = new HashSet<String>();
    // constructeur
    public User() {
    }

    

    public User(String login, String password) {
   	this.login = login;
   	this.password = password;
       }
    
    public String getLastname() {
	return lastname;
    }

    public void setLastname(String lastname) {
	this.lastname = lastname;
    }

    public Date getCreatedAt() {
	return createdAt;
    }

    public void setCreatedAt(Date creationAt) {
	this.createdAt = creationAt;
    }

    public Date getUpdatedAt() {
	return updatedAt;
    }

    public void setUpdatedAt(Date updateAt) {
	this.updatedAt = updateAt;
    }

    public boolean isEnabled() {
	return enabled;
    }

    public void setEnabled(boolean enabled) {
	this.enabled = enabled;
    }


    @JsonIgnore
    public Set<Role> getRoles() {
	return roles;
    }


    @JsonIgnore
    public void setRoles(Set<Role> roles) {
	this.roles = roles;
    }  

    @JsonIgnore
    public void setRoles(ArrayList<Role> roles) {
	for(Role role : roles){
	    this.roles.add(role);
	}
    }

    // identité
    @Override
    public String toString() {
	return String.format("User[%s,%s,%s]", firstname, login, password);
    }

    // getters et setters
    public String getFirstname() {
	return firstname;
    }

    public void setFirstname(String identity) {
	this.firstname = identity;
    }

    public String getLogin() {
	return login;
    }

    public void setLogin(String login) {
	this.login = login;
    }


    @JsonIgnore
    public String getPassword() {
	return password;
    }

    @JsonProperty
    public void setPassword(String password) {
	this.password = password;
    }

    public void setRolesString(Set<String> roles) {
	this.rolesString = roles;
    }

    public Set<String> getRolesString(){
	if(rolesString.isEmpty()){
	    for(Role role: this.roles){
		rolesString.add(role.getName());
	    }
	}
	return rolesString;
    }
}
