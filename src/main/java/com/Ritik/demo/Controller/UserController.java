package com.Ritik.demo.Controller;

import com.Ritik.demo.DTO.LoginRequest;
import com.Ritik.demo.DTO.RegisterRequest;
import com.Ritik.demo.Services.UserService;
import com.Ritik.demo.User.user;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private  UserService authService;
    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        user registeredUser = authService.register(request);
        return new ResponseEntity<>(registeredUser, HttpStatus.OK);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
       // System.out.println("hello login page");
        String token = authService.login(request);
        //System.out.println("hello login pagewwww");
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}
