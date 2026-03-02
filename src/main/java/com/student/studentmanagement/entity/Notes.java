package com.student.studentmanagement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String originalFileName;   // original name
    private String storedFileName;     // hashed filename

    private LocalDate uploadDate;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;
}