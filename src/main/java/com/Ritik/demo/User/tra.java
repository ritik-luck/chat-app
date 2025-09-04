package com.Ritik.demo.User;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class tra {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        // 1. Hash a raw password
        String rawPassword = "123";
        String manualHash = encoder.encode(rawPassword);
        //System.out.println("Generated hash: " + manualHash);

        // 2. Compare with an existing hash (use matches(), not equals())
        String existingHash = "$2a$10$mHGjVpGF5ycsYRHWz.RuD.q60pYDRq.ItdC7g6VbfKeg/Z9lGHWEq";
        boolean isMatch = encoder.matches(rawPassword, existingHash);
        System.out.println("Password matches? " + isMatch); // true if "password123" was the original password
    }
}