package com.sumpositive.ticketing.user.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sumpositive.ticketing.auth.dto.RegisterRequest;
import com.sumpositive.ticketing.common.exception.DuplicateResourceException;
import com.sumpositive.ticketing.common.exception.ResourceNotFoundException;
import com.sumpositive.ticketing.user.dto.AdminCreateUserRequest;
import com.sumpositive.ticketing.user.dto.UserRequest;
import com.sumpositive.ticketing.user.dto.UserResponse;
import com.sumpositive.ticketing.user.entity.User;
import com.sumpositive.ticketing.user.enums.AccountStatus;
import com.sumpositive.ticketing.user.enums.UserRole;
import com.sumpositive.ticketing.user.mapper.UserMapper;
import com.sumpositive.ticketing.user.repository.UserRepository;

import jakarta.transaction.Transactional;


@Service
public class UserService implements UserServiceIMPL{

	private final UserRepository userRepository;
	private final UserMapper userMapper;
	private final PasswordEncoder passwordEncoder;
	
	@Value("${app.admin-setup-key}")
	private String adminSetupKey;
	
	
	public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.userMapper = userMapper;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	@Transactional
	public UserResponse createUser(RegisterRequest request) {
		if(userRepository.existsByEmail(request.getEmail())) {
			throw new DuplicateResourceException("Email already registered");
		}
		
		User user = new User();
		user.setFullName(request.getFullName());
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setUserRole(UserRole.EMPLOYEE);
		user.setAccountStatus(AccountStatus.PENDING);
		user.setActive(true);
		
		User saveUser = userRepository.save(user);
		return userMapper.toResponse(saveUser);
	}

	@Override
	public List<UserResponse> getAllUsers() {
		return userRepository.findAll()
													.stream()
													.map(userMapper :: toResponse)
													.toList();
	}

	@Override
	public UserResponse getUserById(Long id) {
		User user = findUserById(id);
		return userMapper.toResponse(user);
	}

	@Override
	public UserResponse updateUser(Long id, UserRequest request) {
		User user = findUserById(id);
		
		userRepository.findByEmail(request.getEmail())
        .ifPresent(existingUser -> {
            if (!existingUser.getId().equals(id)) {
                throw new DuplicateResourceException(
                        "Email already registered"
                );
            }
        });
		
		userMapper.updateEntity(user,request);
		User updateUser = userRepository.save(user);
		
		return userMapper.toResponse(updateUser);
	}

	@Override
	public void deleteUser(Long id) {
		User user = findUserById(id);

		user.setActive(false);
		userRepository.save(user);
	}

	@Override
	public UserResponse activateUser(Long id) {
		User user = findUserById(id);
		
		user.setActive(true);
		User updateUser = userRepository.save(user);
		return userMapper.toResponse(updateUser);
	}

	@Override
	public UserResponse deactivateUser(Long id) {
		User user = findUserById(id);
		
		user.setActive(false);
		User updatedUser = userRepository.save(user);
		return userMapper.toResponse(updatedUser);
	}

	@Override
	public User findUserById(Long id) {
		return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("user not found with ID: "+id));
	}
	
	@Override
	@Transactional
	public UserResponse createUserByAdmin(AdminCreateUserRequest request) {

	    if (userRepository.existsByEmail(request.getEmail())) {
	        throw new DuplicateResourceException("Email already registered");
	    }

	    User user = new User();

	    user.setFullName(request.getFullName());
	    user.setEmail(request.getEmail());
	    user.setPassword(passwordEncoder.encode(request.getPassword()));
	    user.setUserRole(request.getRole());
	    user.setAccountStatus(AccountStatus.APPROVED);
	    user.setActive(true);

	    User savedUser = userRepository.save(user);

	    return userMapper.toResponse(savedUser);
	}

	@Override
	@Transactional
	public UserResponse createFirstAdmin(RegisterRequest request, String setupKey) {

	    if (setupKey == null ||
	        !MessageDigest.isEqual(
	            adminSetupKey.getBytes(StandardCharsets.UTF_8),
	            setupKey.getBytes(StandardCharsets.UTF_8))) {

	        throw new IllegalArgumentException("Invalid setup key");
	    }

	    boolean adminExists = userRepository.findAll()
	            .stream()
	            .anyMatch(user -> user.getUserRole() == UserRole.ADMIN);

	    if (adminExists) {
	        throw new IllegalStateException("ADMIN account already exists");
	    }

	    if (userRepository.existsByEmail(request.getEmail())) {
	        throw new DuplicateResourceException("Email already registered");
	    }

	    User user = new User();
	    user.setFullName(request.getFullName());
	    user.setEmail(request.getEmail());
	    user.setPassword(passwordEncoder.encode(request.getPassword()));
	    user.setUserRole(UserRole.ADMIN);
	    user.setAccountStatus(AccountStatus.APPROVED);
	    user.setActive(true);

	    User savedUser = userRepository.save(user);

	    return userMapper.toResponse(savedUser);
	}

}
