package com.student.studentmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.student.studentmanagement.entity.Attendance;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByStudentId(Long studentId);
}