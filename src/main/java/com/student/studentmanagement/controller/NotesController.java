package com.student.studentmanagement.controller;

import com.student.studentmanagement.entity.Notes;
import com.student.studentmanagement.entity.Subject;
import com.student.studentmanagement.repository.NotesRepository;
import com.student.studentmanagement.repository.SubjectRepository;
import com.student.studentmanagement.service.NoteStorageService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/notes")
public class NotesController {

    private final NotesRepository notesRepository;
    private final SubjectRepository subjectRepository;
    private final NoteStorageService storageService;

    public NotesController(
            NotesRepository notesRepository,
            SubjectRepository subjectRepository,
            NoteStorageService storageService) {
        this.notesRepository = notesRepository;
        this.subjectRepository = subjectRepository;
        this.storageService = storageService;
    }

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public String uploadNotes(
            @RequestParam String title,
            @RequestParam Long subjectId,
            @RequestParam MultipartFile file
    ) throws Exception {

        // 1️⃣ Find subject
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        // 2️⃣ Save PDF file
        String filePath = storageService.savePdf(file);

        // 3️⃣ Create Notes entity
        Notes notes = new Notes();
        notes.setTitle(title);
        notes.setFileUrl(filePath);
        notes.setSubject(subject);

        // 4️⃣ Save to DB
        notesRepository.save(notes);

        return "PDF uploaded successfully";
    }
}