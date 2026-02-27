package com.student.studentmanagement.controller;

import com.student.studentmanagement.dto.UserResponse;
import com.student.studentmanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.student.studentmanagement.repository.*;
import com.student.studentmanagement.entity.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;
    private final UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @PostMapping("/create-user")
    public User createUser(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @PostMapping("/create-subject")
    public Subject createSubject(@RequestBody Subject subject) {
        return subjectRepository.save(subject);
    }

//    @GetMapping("/all-users")
//    public List<User> getAllUsers() {
////        return userRepository.findAll();
//        return userService.getAllUsers();
//    }
    @GetMapping("/all-users")
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
        }
}