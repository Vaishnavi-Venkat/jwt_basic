package com.spring.security.jwtbasic.controller;

import com.spring.security.jwtbasic.jwtutil.JwtRequestModel;
import com.spring.security.jwtbasic.jwtutil.JwtResponseModel;
import com.spring.security.jwtbasic.jwtutil.JwtUserDetailsService;
import com.spring.security.jwtbasic.jwtutil.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@CrossOrigin
public class JwtController1 {

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenManager tokenManager;

    @PostMapping("/login")
    public ResponseEntity<?> createToken(@RequestBody JwtRequestModel requestModel) throws Exception {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestModel.getUsername(), requestModel.getPassword()));

        } catch (DisabledException e) {
            throw new DisabledException("User Disabled",e);
        }
        catch (BadCredentialsException e){
            throw new Exception("Invalid Credentials!!!",e);
        }
        final UserDetails userDetails=userDetailsService.loadUserByUsername(requestModel.getUsername());
        final String jwtToken=tokenManager.generateJwtToken(userDetails);
        return ResponseEntity.ok(new JwtResponseModel(jwtToken));

    }
}
