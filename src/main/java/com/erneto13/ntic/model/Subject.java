package com.erneto13.ntic.model;

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
    private Career career;

    @ManyToMany(mappedBy = "subjects")
    private Set<Professor> professors = new HashSet<>();
}
