package com.erneto13.ntic.dto;

import lombok.Data;

@Data
public class ProfessorDto {
    private Integer id;
    private String name;
    private String email;
    private String specialty;
    private String department;
    private String roleName;
}