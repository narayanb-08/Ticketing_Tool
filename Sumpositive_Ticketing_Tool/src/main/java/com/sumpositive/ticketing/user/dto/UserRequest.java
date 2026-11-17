package com.sumpositive.ticketing.user.dto;

import com.sumpositive.ticketing.user.enums.UserRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserRequest {

		@NotBlank(message = "Full name is required")
		@Size(max = 100, message = "Full name cannot exceed 100 characters")
		private String fullName;
		
		
		@NotBlank(message = "Email is required")
		@Email(message = "Please provide a valid email address")
		@Size(max = 150, message = "Email cannot exceed 150 character")
		private String email;
		
		@NotNull(message = "User role is required")
		private UserRole userRole;


		public UserRequest() {
			super();
		}


		public UserRequest(String fullName, String email, UserRole userRole) {
			this.fullName = fullName;
			this.email = email;
			this.userRole = userRole;
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
		
		public UserRole getRole() {
	        return userRole;
	    }

	    public void setRole(UserRole userRole) {
	        this.userRole = userRole;
	    }
}
