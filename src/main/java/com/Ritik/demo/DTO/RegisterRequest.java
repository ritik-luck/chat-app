package com.Ritik.demo.DTO;

import lombok.Getter;
import lombok.Setter;
import com.Ritik.demo.User.Role;

@Getter
@Setter
public class RegisterRequest {
    private String username;
    private String email;
    private String password;
    private Role role;

    public String getUsername() {
        return username;
    }

//    public void setUsername(String username) {
//        this.username = username;
//    }

    public String getPassword() {
        return password;
    }

//    public void setPassword(String password) {
//        this.password = password;
//    }

    public Role getRole() {
        return role;
    }

//    public void setRole(Role role) {
//        this.role = role;
//    }

    public String getEmail() {
        return email;
    }

//    public void setEmail(String email) {
//        this.email = email;
//    }
}