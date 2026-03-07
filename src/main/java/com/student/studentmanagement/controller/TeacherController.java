package com.student.studentmanagement.controller;

import org.springframework.core.io.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.*;
import java.time.LocalDate;
import java.util.List;
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

    // -------------------------------
    // GET SUBJECTS FOR TEACHER
    // -------------------------------
    @GetMapping("/subjects")
    public List<Subject> getTeacherSubjects() {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User teacher = userRepository.findByUsername(username);

        return subjectRepository.findAll()
                .stream()
                .filter(s -> s.getTeacher() != null &&
                        s.getTeacher().getId().equals(teacher.getId()))
                .toList();
    }

    // -------------------------------
    // GET ALL STUDENTS
    // -------------------------------
    @GetMapping("/students")
    public List<User> getStudents() {
        return userRepository.findByRole(Role.STUDENT);
    }

    // -------------------------------
    // MARK ATTENDANCE
    // -------------------------------
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

    // -------------------------------
    // UPLOAD NOTES
    // -------------------------------
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

        Files.copy(
                file.getInputStream(),
                uploadPath.resolve(hashedName),
                StandardCopyOption.REPLACE_EXISTING
        );

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

    // -------------------------------
    // DOWNLOAD NOTES
    // -------------------------------
    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> download(@PathVariable String fileName) throws Exception {

        Path path = Paths.get("uploads").resolve(fileName);

        Resource resource = new UrlResource(path.toUri());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }

    // -------------------------------
    // GET NOTES FOR TEACHER
    // -------------------------------
    @GetMapping("/notes")
    public List<Notes> getNotes() {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User teacher = userRepository.findByUsername(username);

        return notesRepository.findAll()
                .stream()
                .filter(n -> n.getSubject().getTeacher().getId().equals(teacher.getId()))
                .toList();
    }
}