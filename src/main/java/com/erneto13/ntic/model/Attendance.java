package com.erneto13.ntic.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@Table(name = "attendances")
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "professor_id", nullable = false)
    private Professor professor;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime checkInTime;

    @Column
    private LocalTime checkOutTime;

    @Column(nullable = false)
    private boolean present;

    @Column
    private String weeklyTopic;

    @ManyToOne
    @JoinColumn(name = "supervisor_id")
    private User supervisor;
}
