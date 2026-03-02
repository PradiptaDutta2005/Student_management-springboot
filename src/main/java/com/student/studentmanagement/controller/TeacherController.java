package com.student.studentmanagement.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.*;
import java.time.LocalDate;
import java.util.UUID;

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

    // ✅ MARK ATTENDANCE
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
            throw new RuntimeException("Not allowed to mark attendance");
        }

        attendance.setDate(LocalDate.now());

        boolean exists = attendanceRepository
                .existsByStudent_IdAndSubject_IdAndDate(
                        attendance.getStudent().getId(),
                        subject.getId(),
                        attendance.getDate()
                );

        if (exists) {
            return "Attendance already marked today!";
        }

        attendanceRepository.save(attendance);

        return "Attendance marked successfully!";
    }

    // ✅ UPLOAD NOTES WITH HASHED FILE NAME
    @PostMapping("/upload-notes")
    public String uploadNotes(
            @RequestParam("file") MultipartFile file,
            @RequestParam("subjectId") Long subjectId,
            @RequestParam("title") String title
    ) throws Exception {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User teacher = userRepository.findByUsername(username);

        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        if (!subject.getTeacher().getId().equals(teacher.getId())) {
            throw new RuntimeException("Not allowed");
        }

        // 🔥 Extract extension safely
        String originalName = file.getOriginalFilename();
        String extension = "";

        if (originalName != null && originalName.contains(".")) {
            extension = originalName.substring(originalName.lastIndexOf("."));
        }

        String hashedName = UUID.randomUUID().toString().replace("-", "") + extension;

        Path uploadPath = Paths.get("uploads");

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Files.copy(file.getInputStream(),
                uploadPath.resolve(hashedName),
                StandardCopyOption.REPLACE_EXISTING);

        Notes notes = Notes.builder()
                .title(title)
                .originalFileName(originalName)
                .storedFileName(hashedName)
                .uploadDate(LocalDate.now())
                .subject(subject)
                .build();

        notesRepository.save(notes);

        return "File uploaded successfully!";
    }
}