package com.erneto13.ntic.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "subjects")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "career_id")
    @JsonIgnoreProperties("subjects")
    private Career career;

    @ManyToMany(mappedBy = "subjects")
    @JsonIgnoreProperties("subjects")
    private Set<Professor> professors = new HashSet<>();
}