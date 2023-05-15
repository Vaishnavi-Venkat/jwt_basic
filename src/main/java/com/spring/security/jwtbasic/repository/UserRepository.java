package com.spring.security.jwtbasic.repository;

import com.spring.security.jwtbasic.jwtutil.JwtRequestModel;
import com.spring.security.jwtbasic.model.UserRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<UserRequest, Long>{
}
