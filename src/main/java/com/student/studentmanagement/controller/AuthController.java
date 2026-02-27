package com.student.studentmanagement.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.student.studentmanagement.repository.UserRepository;
import com.student.studentmanagement.entity.User;
import com.student.studentmanagement.security.JwtUtil;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public String register(@RequestBody User user) {

        if (userRepository.findByUsername(user.getUsername()) != null) {
            return "Username already exists!";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return "User registered successfully!";
    }

    @PostMapping("/login")
    public String login(@RequestBody User request) {

        User user = userRepository.findByUsername(request.getUsername());

        if (user == null) {
            return "User not found!";
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return "Invalid password!";
        }

        return jwtUtil.generateToken(user.getUsername(), user.getRole().name());
    }
}