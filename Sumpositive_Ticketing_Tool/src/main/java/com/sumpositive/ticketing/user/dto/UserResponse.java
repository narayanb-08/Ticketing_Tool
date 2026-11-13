package com.sumpositive.ticketing.user.dto;

public class UserResponse {

	private Long id;
	private String fullName;
	private String email;
	private boolean isActive;
	public UserResponse() {
		super();
	}
	public UserResponse(Long id, String fullName, String email, boolean isActive) {
		super();
		this.id = id;
		this.fullName = fullName;
		this.email = email;
		this.isActive = isActive;
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
	
	
}
