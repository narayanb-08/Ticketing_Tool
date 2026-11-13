package com.sumpositive.ticketing.auth.dto;

public class LoginResponse {
	private long id;
	private String fullName;
	private String email;
	private String token;
	private String message;
	public LoginResponse() {
		super();
	}
	public LoginResponse(long id, String fullName, String email, String token, String message) {
		super();
		this.id = id;
		this.fullName = fullName;
		this.email = email;
		this.token = token;
		this.message = message;
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
	
	
}
