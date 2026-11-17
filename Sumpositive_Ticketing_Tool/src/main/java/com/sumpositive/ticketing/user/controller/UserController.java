package com.sumpositive.ticketing.user.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sumpositive.ticketing.auth.dto.RegisterRequest;
import com.sumpositive.ticketing.user.dto.AdminCreateUserRequest;
import com.sumpositive.ticketing.user.dto.UserRequest;
import com.sumpositive.ticketing.user.dto.UserResponse;
import com.sumpositive.ticketing.user.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
			private final UserService userService;

			public UserController(UserService userService) {
				super();
				this.userService = userService;
			}
			
			@PostMapping("/create")
			public ResponseEntity<UserResponse> createUser(@RequestBody RegisterRequest request){
				UserResponse response = userService.createUser(request);
				
				return ResponseEntity.status(HttpStatus.CREATED).body(response);
			}
			
			@GetMapping("/getAllUsers")
			public ResponseEntity<List<UserResponse>> getAllUsers(){
				List<UserResponse> users = userService.getAllUsers();
				
				return ResponseEntity.ok(users);
			}
			
			@GetMapping("/getById/{id}")
			public ResponseEntity<UserResponse> getUserById(@PathVariable Long id){
				UserResponse response = userService.getUserById(id);
				
				return ResponseEntity.ok(response);
			}
			
			@PutMapping("/updateUser/{id}")
			public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody UserRequest request){
				UserResponse response = userService.updateUser(id, request);
				
				return ResponseEntity.ok(response);
				
			}
			
			@DeleteMapping("/deleteUser/{id}")
			public ResponseEntity<UserResponse> deleteUser(@PathVariable Long id){
				userService.deleteUser(id);
				
				return ResponseEntity.noContent().build();
			}
			
			@PatchMapping("/activate/{id}")
			public ResponseEntity<UserResponse> activateUser(@PathVariable Long id){
				UserResponse response = userService.activateUser(id);
				
				return ResponseEntity.ok(response);
			}
			
			@PatchMapping("/deactivate/{id}")
			public ResponseEntity<UserResponse> deactivateUser(@PathVariable Long id){
				UserResponse response = userService.deactivateUser(id);
				
				return ResponseEntity.ok(response);
			}
			
			@PostMapping("/admin/create")
			public ResponseEntity<UserResponse> createUserByAdmin(@Valid @RequestBody AdminCreateUserRequest request) {
			    UserResponse response = userService.createUserByAdmin(request);

			    return ResponseEntity.status(HttpStatus.CREATED).body(response);
			}
			
			@PostMapping("/first-admin")
			public ResponseEntity<UserResponse> createFirstAdmin(
			        @RequestBody RegisterRequest request,
			        @RequestHeader("X-Admin-Setup-Key") String setupKey) {

			    UserResponse response = userService.createFirstAdmin(request, setupKey);

			    return ResponseEntity.status(HttpStatus.CREATED).body(response);
			}
}
