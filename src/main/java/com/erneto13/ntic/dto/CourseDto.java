package com.erneto13.ntic.dto;

import lombok.Data;

@Data
public class CourseDto {
    private Integer id;
    private String name;
    private String groupCode;
    private ProfessorDto professor;
    private String description;
    private String classroom;

}
