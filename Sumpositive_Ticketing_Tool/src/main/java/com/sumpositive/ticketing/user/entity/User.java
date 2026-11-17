package com.sumpositive.ticketing.user.entity;

import com.sumpositive.ticketing.user.enums.UserRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "users_info")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;
	
	@Column(name = "full_name", nullable = false, length = 100)
		private String fullName;
	
	@Column(nullable = false, unique = true, length = 150)
		private String email;
	
	@Column(name = "password_hash", nullable = false)
		private String password;
	
	@Column(nullable = false)
		private boolean active;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "user_role",length = 20)
	private UserRole userRole;
}
