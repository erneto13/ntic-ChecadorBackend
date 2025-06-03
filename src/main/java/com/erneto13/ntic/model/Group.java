package com.erneto13.ntic.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "`groups`")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name; // Ej: "2-01 Civil"

    @ManyToOne
    @JoinColumn(name = "career_id")
    @JsonIgnoreProperties("groups")
    private Career career;

    @ManyToOne
    @JoinColumn(name = "head_student_id")
    private User headStudent; // Jefe de grupo

    @OneToMany(mappedBy = "group")
    @JsonIgnoreProperties("group")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<ClassSession> classSessions = new HashSet<>();
}