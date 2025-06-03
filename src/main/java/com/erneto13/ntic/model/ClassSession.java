package com.erneto13.ntic.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "class_sessions")
public class ClassSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "group_id")
    @JsonIgnoreProperties("classSessions")
    private Group group;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    @JsonIgnoreProperties("classSessions")
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    @JsonIgnoreProperties("classSessions")
    private Professor professor;

    @ManyToOne
    @JoinColumn(name = "classroom_id")
    @JsonIgnoreProperties("classSessions")
    private ClassRoom classRoom;

    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    private LocalTime startTime;
    private LocalTime endTime;

    @OneToMany(mappedBy = "classSession")
    @JsonIgnoreProperties("classSession")
    private Set<Attendance> attendances = new HashSet<>();
}