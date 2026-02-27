package com.student.studentmanagement.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.student.studentmanagement.repository.*;
import com.student.studentmanagement.entity.*;

@RestController
@RequestMapping("/teacher")
@RequiredArgsConstructor
public class TeacherController {

    private final AttendanceRepository attendanceRepository;
    private final NotesRepository notesRepository;

    @PostMapping("/mark-attendance")
    public Attendance markAttendance(@RequestBody Attendance attendance) {
        return attendanceRepository.save(attendance);
    }

    @PostMapping("/upload-notes")
    public Notes uploadNotes(@RequestBody Notes notes) {
        return notesRepository.save(notes);
    }
}