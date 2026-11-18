package com.sumpositive.ticketing.auth.dto;

import com.sumpositive.ticketing.user.enums.UserRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class RegisterRequest {

	@NotBlank(message = "Full name is required")
	@Size(max = 100,message = "Full name cannot exceed 100 characters")
	private String fullName;
	
	@NotBlank(message = "Email is required")
	@Email(message = "Please provide a valid email address")
	private String email;
	
	@NotBlank(message = "password is required")
	@Size(min = 8, max = 100,message = "Password must contain between 8 and 100 characters")
	private String password;
	
	@NotNull(message = "Please select a role")
    private UserRole role;
	
	public RegisterRequest() {
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}