package com.Ritik.demo.Model;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHasher {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        System.out.println("Hashed password for 'password123': " + encoder.encode("password123"));
        System.out.println("Hashed password for 'adminpass': " + encoder.encode("adminpass"));
        System.out.println("Hashed password for 'password456': " + encoder.encode("password456"));
    }
}