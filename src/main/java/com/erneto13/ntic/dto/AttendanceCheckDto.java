package com.erneto13.ntic.dto;

import com.erneto13.ntic.model.Course;
import com.erneto13.ntic.model.Professor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class AttendanceCheckDto {
    private Professor professor;
    private Course course;
}
