package com.sumpositive.ticketing.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sumpositive.ticketing.user.entity.User;
import com.sumpositive.ticketing.user.enums.AccountStatus;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	Optional <User> findByEmail(String email);
	boolean existsByEmail(String email);
	
	List<User> findByAccountStatus(AccountStatus accountStatus);
}
