package com.student.studentmanagement.repository;

import com.student.studentmanagement.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import com.student.studentmanagement.entity.User;


import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    List<User> findByRole(Role role);

}