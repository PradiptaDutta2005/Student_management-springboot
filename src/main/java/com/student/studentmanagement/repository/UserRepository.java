package com.student.studentmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.student.studentmanagement.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}