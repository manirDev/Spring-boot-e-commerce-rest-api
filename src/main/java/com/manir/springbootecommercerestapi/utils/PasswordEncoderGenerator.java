package com.manir.springbootecommercerestapi.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderGenerator {
    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println("ADMIN: " + passwordEncoder.encode("admin"));
        System.out.println("USER: " + passwordEncoder.encode("user"));
    }
}
