package com.student.studentmanagement.service;

import com.student.studentmanagement.entity.Notes;
import com.student.studentmanagement.entity.Subject;
import com.student.studentmanagement.repository.NotesRepository;
import com.student.studentmanagement.repository.SubjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class NotesService {

    private final NotesRepository notesRepository;
    private final SubjectRepository subjectRepository;
    private final NoteStorageService storageService;

    public NotesService(NotesRepository notesRepository,
                        SubjectRepository subjectRepository,
                        NoteStorageService storageService) {
        this.notesRepository = notesRepository;
        this.subjectRepository = subjectRepository;
        this.storageService = storageService;
    }

    public Notes uploadNote(Long subjectId, String title, MultipartFile file) throws IOException {

        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        String filePath = storageService.savePdf(file);

        Notes note = new Notes();
        note.setTitle(title);
        note.setFileUrl(filePath);
        note.setSubject(subject);

        return notesRepository.save(note);
    }

    public List<Notes> getNotesBySubject(Long subjectId) {
        return notesRepository.findBySubjectId(subjectId);
    }
}