package com.student.studentmanagement.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.student.studentmanagement.repository.*;
import com.student.studentmanagement.entity.*;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final AttendanceRepository attendanceRepository;
    private final NotesRepository notesRepository;

    @GetMapping("/attendance/{studentId}")
    public List<Attendance> getAttendance(@PathVariable Long studentId) {
        return attendanceRepository.findByStudent_Id(studentId);
    }

    @GetMapping("/notes/{subjectId}")
    public List<Notes> getNotes(@PathVariable Long subjectId) {
        return notesRepository.findBySubjectId(subjectId);
    }

}