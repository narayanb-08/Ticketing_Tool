package com.sumpositive.ticketing.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.sumpositive.ticketing.user.entity.User;
import com.sumpositive.ticketing.user.enums.AccountStatus;
import com.sumpositive.ticketing.user.repository.UserRepository;

@Component("accountStatusSecurity")
public class AccountStatusSecurity {

    private final UserRepository userRepository;

    public AccountStatusSecurity(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isApproved() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        return userRepository.findByEmail(authentication.getName())
                .map(User::getAccountStatus)
                .map(status -> status == AccountStatus.APPROVED)
                .orElse(false);
    }
}
