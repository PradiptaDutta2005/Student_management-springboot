package com.student.studentmanagement.controller;

import com.student.studentmanagement.dto.AssignSubjectRequest;
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
//    @PostMapping("/assign-subject")
//    public Subject assignSubjectToTeacher(@RequestParam Long subjectId,
//                                          @RequestParam Long teacherId) {
//
//        Subject subject = subjectRepository.findById(subjectId)
//                .orElseThrow(() -> new RuntimeException("Subject not found"));
//
//        User teacher = userRepository.findById(teacherId)
//                .orElseThrow(() -> new RuntimeException("Teacher not found"));
//
//        if (teacher.getRole().name().equals("TEACHER")) {
//            subject.setTeacher(teacher);
//            return subjectRepository.save(subject);
//        } else {
//            throw new RuntimeException("User is not a teacher!");
//        }
//    }
    @PostMapping("/assign-subject")
    public Subject assignSubject(@RequestBody AssignSubjectRequest request) {

        Subject subject = subjectRepository.findById(request.getSubjectId())
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        User teacher = userRepository.findById(request.getTeacherId())
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        subject.setTeacher(teacher);
        return subjectRepository.save(subject);
    }
    @GetMapping("/teachers")
    public List<UserResponse> getAllTeachers() {
        return userRepository.findAll()
                .stream()
                .filter(user -> user.getRole().name().equals("TEACHER"))
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getUsername(),
                        user.getRole().name()
                ))
                .toList();
    }
}