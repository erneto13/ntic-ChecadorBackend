package com.erneto13.ntic.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    private Professor professor;

    @Column
    private String groupCode;

    @Column
    private String classroom;
}
