package com.erneto13.ntic.dto;

import lombok.Data;
import java.util.HashSet;
import java.util.Set;

@Data
public class SubjectDTO {
    private Long id;
    private String name;
    private Long careerId;
    private String careerName;
    private Set<Integer> professorIds = new HashSet<>();
}