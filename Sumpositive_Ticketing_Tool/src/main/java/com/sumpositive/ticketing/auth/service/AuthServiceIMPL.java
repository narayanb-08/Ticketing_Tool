package com.sumpositive.ticketing.auth.service;

import com.sumpositive.ticketing.auth.dto.LoginRequest;
import com.sumpositive.ticketing.auth.dto.LoginResponse;
import com.sumpositive.ticketing.auth.dto.RegisterRequest;
import com.sumpositive.ticketing.user.entity.User;

public interface AuthServiceIMPL {
	
	public User registerUser(RegisterRequest request);
	public LoginResponse loginUser(LoginRequest request);
	
}
