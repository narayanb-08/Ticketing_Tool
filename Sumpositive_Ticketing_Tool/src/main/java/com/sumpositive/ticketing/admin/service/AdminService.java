package com.sumpositive.ticketing.admin.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sumpositive.ticketing.admin.dto.CreateAdminRequest;
import com.sumpositive.ticketing.common.exception.DuplicateResourceException;
import com.sumpositive.ticketing.user.dto.UserResponse;
import com.sumpositive.ticketing.user.entity.User;
import com.sumpositive.ticketing.user.enums.AccountStatus;
import com.sumpositive.ticketing.user.enums.UserRole;
import com.sumpositive.ticketing.user.mapper.UserMapper;
import com.sumpositive.ticketing.user.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class AdminService implements AdminServiceImpl {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Value("${app.admin-setup-key}")
    private String adminSetupKey;

    public AdminService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            UserMapper userMapper) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Override
    @Transactional
    public UserResponse createFirstAdmin(
            CreateAdminRequest request, String setupKey) {

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
            throw new IllegalStateException(
                    "First ADMIN account already exists");
        }

        return saveAdmin(request);
    }

    @Override
    @Transactional
    public UserResponse createAdmin(CreateAdminRequest request) {

        return saveAdmin(request);
    }

    private UserResponse saveAdmin(CreateAdminRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException(
                    "Email already registered");
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

    @Override
    public List<UserResponse> getPendingEmployees() {

        return userRepository.findByAccountStatus(AccountStatus.PENDING)
                .stream()
                .filter(user -> user.getUserRole() == UserRole.EMPLOYEE)
                .map(userMapper::toResponse)
                .toList();
    }
    
    @Override
    @Transactional
    public UserResponse approveEmployee(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("Employee not found"));

        if (user.getUserRole() != UserRole.EMPLOYEE) {
            throw new IllegalArgumentException(
                    "Only employees can be approved");
        }

        if (user.getAccountStatus() != AccountStatus.PENDING) {
            throw new IllegalStateException(
                    "Employee is not in pending status");
        }

        user.setAccountStatus(AccountStatus.APPROVED);

        User updatedUser = userRepository.save(user);

        return userMapper.toResponse(updatedUser);
    }
    
    @Override
    @Transactional
    public UserResponse rejectEmployee(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("Employee not found"));

        if (user.getUserRole() != UserRole.EMPLOYEE) {
            throw new IllegalArgumentException(
                    "Only employees can be rejected");
        }

        if (user.getAccountStatus() != AccountStatus.PENDING) {
            throw new IllegalStateException(
                    "Employee is not in pending status");
        }

        user.setAccountStatus(AccountStatus.REJECTED);

        User updatedUser = userRepository.save(user);

        return userMapper.toResponse(updatedUser);
    }

}
