package com.student.studentmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.student.studentmanagement.entity.Attendance;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    List<Attendance> findByStudent_Id(Long studentId);

    boolean existsByStudent_IdAndSubject_IdAndDate(
            Long studentId,
            Long subjectId,
            LocalDate date
    );
}