package com.student.studentmanagement.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import com.student.studentmanagement.repository.*;
import com.student.studentmanagement.entity.*;

@RestController
@RequestMapping("/teacher")
@RequiredArgsConstructor
public class TeacherController {

    private final AttendanceRepository attendanceRepository;
    private final NotesRepository notesRepository;
    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;

    @PostMapping("/mark-attendance")
    public String markAttendance(@RequestBody Attendance attendance) {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User teacher = userRepository.findByUsername(username);

        Subject subject = subjectRepository.findById(
                attendance.getSubject().getId()
        ).orElseThrow(() -> new RuntimeException("Subject not found"));

        if (!subject.getTeacher().getId().equals(teacher.getId())) {
            throw new RuntimeException("Not allowed to mark attendance for this subject");
        }

        attendance.setDate(LocalDate.now());

        boolean exists = attendanceRepository
                .existsByStudent_IdAndSubject_IdAndDate(
                        attendance.getStudent().getId(),
                        attendance.getSubject().getId(),
                        attendance.getDate()
                );

        if (exists) {
            return "Attendance already marked today!";
        }

        attendanceRepository.save(attendance);

        return "Attendance marked successfully!";
    }

    @PostMapping("/upload-notes")
    public Notes uploadNotes(@RequestBody Notes notes) {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User teacher = userRepository.findByUsername(username);

        Subject subject = subjectRepository.findById(
                notes.getSubjectId()
        ).orElseThrow(() -> new RuntimeException("Subject not found"));

        if (!subject.getTeacher().getId().equals(teacher.getId())) {
            throw new RuntimeException("Not allowed to upload notes for this subject");
        }

        return notesRepository.save(notes);
    }
}