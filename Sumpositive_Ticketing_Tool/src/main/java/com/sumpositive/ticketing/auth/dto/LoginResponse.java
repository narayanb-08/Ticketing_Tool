package com.sumpositive.ticketing.auth.dto;

import com.sumpositive.ticketing.user.enums.UserRole;

public class LoginResponse {
	private long id;
	private String fullName;
	private String email;
	private String token;
	private String message;
	private UserRole role;
	
	
	public LoginResponse() {
		
	}
	
	public LoginResponse(long id, String fullName, String email, String token, String message, UserRole role) {
		super();
		this.id = id;
		this.fullName = fullName;
		this.email = email;
		this.token = token;
		this.message = message;
		this.role = role;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}
	
	
}
