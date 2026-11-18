package com.sumpositive.ticketing.admin.service;

import java.util.List;

import com.sumpositive.ticketing.admin.dto.CreateAdminRequest;
import com.sumpositive.ticketing.user.dto.UserResponse;

public interface AdminServiceImpl {
	UserResponse createFirstAdmin(CreateAdminRequest request, String setupkey);
	
	UserResponse createAdmin(CreateAdminRequest request);
	
	List<UserResponse> getPendingEmployees();
	
	UserResponse approveEmployee(Long userId);
	
	UserResponse rejectEmployee(Long userId);
}
