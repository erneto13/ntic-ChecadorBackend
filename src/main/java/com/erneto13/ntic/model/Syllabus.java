package com.erneto13.ntic.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "syllabi")
public class Syllabus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column
    private boolean approved;

    @ManyToOne
    @JoinColumn(name = "approved_by")
    private User approvedBy;
}
