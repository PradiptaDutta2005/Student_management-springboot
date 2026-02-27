package com.student.studentmanagement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.student.studentmanagement.repository.UserRepository;
import com.student.studentmanagement.entity.User;
import com.student.studentmanagement.dto.UserResponse;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getUsername(),
                        user.getRole().name()
                ))
                .toList();
    }
}