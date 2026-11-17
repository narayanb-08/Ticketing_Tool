package com.sumpositive.ticketing.user.service;

import java.util.List;

import com.sumpositive.ticketing.auth.dto.RegisterRequest;
import com.sumpositive.ticketing.user.dto.AdminCreateUserRequest;
import com.sumpositive.ticketing.user.dto.UserRequest;
import com.sumpositive.ticketing.user.dto.UserResponse;
import com.sumpositive.ticketing.user.entity.User;

public interface UserServiceIMPL {

	public UserResponse createUser(RegisterRequest request);
	public List<UserResponse> getAllUsers();
	public UserResponse getUserById(Long id);
	public UserResponse updateUser(Long id, UserRequest request);
	public void deleteUser(Long id);
	public UserResponse activateUser(Long id);
	public UserResponse deactivateUser(Long id);
	public User findUserById(Long id);
	public UserResponse createUserByAdmin(AdminCreateUserRequest request);
	public UserResponse createFirstAdmin(RegisterRequest request, String setupkey);
}
