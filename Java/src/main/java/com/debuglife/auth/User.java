package com.debuglife.auth;

import javax.persistence.*;

@Entity
public class User {
	  @Id
	  @GeneratedValue(strategy = GenerationType.AUTO)
	  private Long id;
	  private String firstName;
	  private String lastName;
	  
	  @Column(nullable = false, unique = true)
	  private String username;
	  
	  private String password;
	  
	  public Long getId() {
	    return id;
	  }
	  public void setId(Long id) {
	    this.id = id;
	  }
	  public String getUsername() {
	    return username;
	  }
	  public void setUsername(String username) {
	    this.username = username;
	  }
	  public String getPassword() {
	    return password;
	  }
	  public void setPassword(String password) {
	    this.password = password;
	  }
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
}