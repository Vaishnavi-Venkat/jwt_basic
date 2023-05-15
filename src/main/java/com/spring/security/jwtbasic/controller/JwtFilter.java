package com.spring.security.jwtbasic.controller;

import com.spring.security.jwtbasic.jwtutil.JwtUserDetailsService;
import com.spring.security.jwtbasic.jwtutil.TokenManager;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private TokenManager tokenManager;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenheader=request.getHeader("Authorization");
        String username=null;
        String token=null;
        if(tokenheader!=null&&tokenheader.startsWith("Bearer ")){
             token=tokenheader.replace("Bearer ","");
             try{
                 username=tokenManager.getUsernameFromToken(token);
             }catch (IllegalArgumentException e){
                 System.out.println("Unable to get Jwt token");
                e.printStackTrace();
             }
             catch (ExpiredJwtException e){
                 System.out.println("Jwt token has expired");
                e.printStackTrace();
             }
        }else{
              System.out.println("Bearer String not found in token");
        }
        if(null!=username&& SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails= jwtUserDetailsService.loadUserByUsername(username);
            if(tokenManager.validateToken(token,userDetails)){
                UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            }
        }

        filterChain.doFilter(request,response);
    }
}
