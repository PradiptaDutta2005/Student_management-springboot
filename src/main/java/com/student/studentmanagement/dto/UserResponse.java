package com.student.studentmanagement.dto;

public record UserResponse(
        Long id,
        String username,
        String role
) {}