package com.erneto13.ntic.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "attendances")
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "class_session_id")
    @JsonIgnoreProperties("attendances")
    private ClassSession classSession;

    private LocalDate date;

    private boolean attended;

    // Verificación por profesor
    private boolean professorVerified;
    private LocalDateTime professorVerificationTime;

    // Verificación por jefe de grupo
    private boolean headStudentVerified;
    private LocalDateTime headStudentVerificationTime;

    // Verificación por checador
    private boolean checkerVerified;
    private LocalDateTime checkerVerificationTime;

    @ManyToOne
    @JoinColumn(name = "checker_id")
    private User checker;
}