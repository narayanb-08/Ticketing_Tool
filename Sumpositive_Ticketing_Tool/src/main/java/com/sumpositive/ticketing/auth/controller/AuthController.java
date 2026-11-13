package com.sumpositive.ticketing.auth.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sumpositive.ticketing.auth.dto.LoginRequest;
import com.sumpositive.ticketing.auth.dto.LoginResponse;
import com.sumpositive.ticketing.auth.dto.RegisterRequest;
import com.sumpositive.ticketing.auth.service.AuthService;
import com.sumpositive.ticketing.user.entity.User;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}
	
	
	@PostMapping("/register")
	public ResponseEntity<Map<String, Object>> registerUser(@Valid @RequestBody RegisterRequest request){
		
		User user = authService.registerUser(request);
		
		Map<String, Object> response = Map.of(
												"id", user.getId(),
												"fullName", user.getFullName(),
												"email", user.getEmail(),
												"message", "Registration successful");
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@PostMapping("/login")
		public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginRequest request){
						LoginResponse response = authService.loginUser(request);
						return ResponseEntity.ok(response);
	}
}
