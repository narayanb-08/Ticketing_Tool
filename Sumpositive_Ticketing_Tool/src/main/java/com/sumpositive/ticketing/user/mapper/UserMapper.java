package com.sumpositive.ticketing.user.mapper;

import org.springframework.stereotype.Component;

import com.sumpositive.ticketing.user.dto.UserRequest;
import com.sumpositive.ticketing.user.dto.UserResponse;
import com.sumpositive.ticketing.user.entity.User;

@Component
public class UserMapper {

	public UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.isActive(),
                user.getUserRole()
        );
    }
	
	public User toEntity(UserRequest request) {
		User user = new User();
		user.setFullName(request.getFullName());
		user.setEmail(request.getEmail());
		user.setUserRole(request.getRole());
		
		return user;
	}
	
	
		public void updateEntity(User user, UserRequest request) {
			user.setFullName(request.getFullName());
			user.setEmail(request.getEmail());
			user.setUserRole(request.getRole());
		
	}
}
