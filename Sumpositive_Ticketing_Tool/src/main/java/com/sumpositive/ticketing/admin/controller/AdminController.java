package com.sumpositive.ticketing.admin.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sumpositive.ticketing.admin.dto.CreateAdminRequest;
import com.sumpositive.ticketing.admin.service.AdminServiceImpl;
import com.sumpositive.ticketing.user.dto.UserResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminServiceImpl adminService;

    public AdminController(AdminServiceImpl adminService) {
        this.adminService = adminService;
    }

    // Create the first ADMIN account
    @PostMapping("/setup")
    public ResponseEntity<UserResponse> createFirstAdmin(
            @Valid @RequestBody CreateAdminRequest request,
            @RequestHeader("X-ADMIN-SETUP-KEY") String setupKey) {

        UserResponse response =
                adminService.createFirstAdmin(request, setupKey);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Create additional ADMIN accounts
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<UserResponse> createAdmin(
            @Valid @RequestBody CreateAdminRequest request) {

        UserResponse response = adminService.createAdmin(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/pending-employees")
    public ResponseEntity<List<UserResponse>> getPendingEmployees() {

        return ResponseEntity.ok(
                adminService.getPendingEmployees()
        );
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/approve-employee/{userId}")
    public ResponseEntity<UserResponse> approveEmployee(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                adminService.approveEmployee(userId));
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/reject-employee/{userId}")
    public ResponseEntity<UserResponse> rejectEmployee(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                adminService.rejectEmployee(userId));
    }
}
