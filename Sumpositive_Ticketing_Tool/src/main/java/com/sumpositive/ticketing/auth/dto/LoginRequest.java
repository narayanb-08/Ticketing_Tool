package com.sumpositive.ticketing.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
	
	@NotBlank(message = "Email is required")
	@Email(message = "Please provide a valid email address")
	private String email;
	
	@NotBlank(message = "password is required")
	private String password;

	public LoginRequest() {
		super();
	}

	public LoginRequest(
			@NotBlank(message = "Email is required") @Email(message = "Please provide a valid email address") String email,
			@NotBlank(message = "password is required") String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
