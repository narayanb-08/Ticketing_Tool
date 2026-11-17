package com.sumpositive.ticketing.auth.service;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sumpositive.ticketing.auth.dto.LoginRequest;
import com.sumpositive.ticketing.auth.dto.LoginResponse;
import com.sumpositive.ticketing.auth.dto.RegisterRequest;
import com.sumpositive.ticketing.common.exception.DuplicateResourceException;
import com.sumpositive.ticketing.common.exception.InvalidCredentialsException;
import com.sumpositive.ticketing.security.JwtSecurity;
import com.sumpositive.ticketing.user.entity.User;
import com.sumpositive.ticketing.user.enums.UserRole;
import com.sumpositive.ticketing.user.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class AuthService implements AuthServiceIMPL{
		
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtSecurity jwtSecurity;
	
	public AuthService(UserRepository userRepository,PasswordEncoder passwordEncoder, JwtSecurity jwtSecurity) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtSecurity = jwtSecurity;
	}
	
	@Override
	@Transactional
	public User registerUser(RegisterRequest request) {
		if(userRepository.existsByEmail(request.getEmail())) {
			throw new DuplicateResourceException("Email already registered");
		}
		
		User user = new User();
		
		user.setFullName(request.getFullName());
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		
		user.setActive(true);
		
		user.setUserRole(UserRole.EMPLOYEE);
		return userRepository.save(user);
	}

	@Override
	public LoginResponse loginUser(LoginRequest request) {

	    User user = userRepository.findByEmail(request.getEmail())
	            .orElseThrow(() -> new InvalidCredentialsException(
	                    "Email not registered"));

	    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
	        throw new InvalidCredentialsException("Incorrect password");
	    }

	    if (!user.isActive()) {
	        throw new InvalidCredentialsException("Your account is inactive");
	    }

	    if (request.getRole() == null ||
	            !request.getRole().equals(user.getUserRole())) {

	        throw new InvalidCredentialsException(
	                "Role mismatch. Your registered role is: " + user.getUserRole());
	    }

	    String token = jwtSecurity.generateToken(user.getEmail());

	    return new LoginResponse(
	            user.getId(),
	            user.getFullName(),
	            user.getEmail(),
	            token,
	            "Login successful",
	            user.getUserRole()
	    );
	}
}
