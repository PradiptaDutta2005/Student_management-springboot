package com.student.studentmanagement.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.student.studentmanagement.repository.*;
import com.student.studentmanagement.entity.*;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final AttendanceRepository attendanceRepository;
    private final NotesRepository notesRepository;
    private final UserRepository userRepository;

    // -----------------------------
    // GET STUDENT ATTENDANCE
    // -----------------------------
    @GetMapping("/attendance")
    public List<Attendance> getAttendance() {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User student = userRepository.findByUsername(username);

        return attendanceRepository.findByStudent_Id(student.getId());
    }

    // -----------------------------
    // GET ALL NOTES
    // -----------------------------
    @GetMapping("/notes")
    public List<Notes> getNotes() {
        return notesRepository.findAll();
    }

    // -----------------------------
    // DOWNLOAD NOTES
    // -----------------------------
    @GetMapping("/download/{filename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) throws Exception {

        Path filePath = Paths.get("uploads").resolve(filename);

        Resource resource = new UrlResource(filePath.toUri());

        if (!resource.exists()) {
            throw new RuntimeException("File not found");
        }

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}