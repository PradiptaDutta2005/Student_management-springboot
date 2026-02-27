package com.student.studentmanagement.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignSubjectRequest {
    private Long subjectId;
    private Long teacherId;
}
