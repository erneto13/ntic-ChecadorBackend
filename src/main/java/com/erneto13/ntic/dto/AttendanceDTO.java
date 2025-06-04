package com.erneto13.ntic.dto;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class AttendanceDTO {
    private Integer id;
    private Long classSessionId;
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

    private Long checkerId;
}