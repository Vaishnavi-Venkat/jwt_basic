package com.spring.security.jwtbasic.jwtutil;

import java.io.Serializable;

public class JwtResponseModel implements Serializable {

    private static final long SERIAL_ID=1L;
    private final String token;


    public JwtResponseModel(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
