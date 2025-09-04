package com.Ritik.demo.Services;

import com.Ritik.demo.DTO.LoginRequest;
import com.Ritik.demo.DTO.RegisterRequest;
import com.Ritik.demo.User.user;
import com.Ritik.demo.Repository.UserRepository;
import com.Ritik.demo.Utility.JWT_Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  JWT_Util jwtUtil;
    @Autowired
    private  PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public user register(RegisterRequest request) {
        user user = new user(
                request.getUsername(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()), // Hash password
                request.getRole()
        );
        userRepository.save(user);

        return user;  // Return the registered user
    }
    public String login(LoginRequest request) {
        System.out.println("Authenticating user: " + request.getUsername());
        System.out.println("Entered Password: " + request.getPassword());
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            System.out.println("✅ Authentication successful for: " + userDetails.getUsername());
            return jwtUtil.generateToken(userDetails.getUsername());

        } catch (Exception e) {
            System.out.println("❌ Authentication failed: " + e.getMessage());
            throw new RuntimeException("Invalid username or password");
        }
    }
}