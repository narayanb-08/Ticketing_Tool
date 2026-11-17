package com.sumpositive.ticketing.user.dto;

import com.sumpositive.ticketing.user.enums.UserRole;

public class UserResponse {

	private Long id;
	private String fullName;
	private String email;
	private boolean isActive;
	private UserRole userRole;
	
	
	public UserResponse() {
	
	}
	
	
	public UserResponse(Long id, String fullName, String email, boolean isActive, UserRole userRole) {
		this.id = id;
		this.fullName = fullName;
		this.email = email;
		this.isActive = isActive;
		this.userRole = userRole;
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
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
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public UserRole getUserRole() {
		return userRole;
	}
	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}
	
}
