package com.student.studentmanagement.repository;

import com.student.studentmanagement.entity.Notes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotesRepository extends JpaRepository<Notes, Long> {

    List<Notes> findBySubjectId(Long subjectId);
}