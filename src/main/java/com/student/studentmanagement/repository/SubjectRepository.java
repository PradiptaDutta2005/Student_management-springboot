package com.student.studentmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.student.studentmanagement.entity.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
}