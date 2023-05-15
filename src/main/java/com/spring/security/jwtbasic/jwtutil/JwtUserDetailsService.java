package com.spring.security.jwtbasic.jwtutil;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if("yugendran".equals(username)) {
            return new User("yugendran", "$2a$10$zgVi2fN.7FZBk0QwUIMrIO9aJUvQUCmvw9MseT75E4VBPVJotIize", new ArrayList<>());
        }else{
            throw new UsernameNotFoundException("User not found with username:"+username);
        }
    }
}
