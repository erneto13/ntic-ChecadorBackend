package com.erneto13.ntic.service;

import com.erneto13.ntic.dto.AttendanceDTO;
import com.erneto13.ntic.dto.ClassSessionDTO;
import com.erneto13.ntic.model.Attendance;
import com.erneto13.ntic.model.ClassSession;
import com.erneto13.ntic.repository.AttendanceRepository;
import com.erneto13.ntic.repository.ClassSessionRepository;
import com.erneto13.ntic.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private ClassSessionRepository classSessionRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public List<AttendanceDTO> getAllAttendances() {
        return attendanceRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public AttendanceDTO getAttendanceById(Long id) {
        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asistencia no encontrada con id: " + id));
        return convertToDTO(attendance);
    }

    @Transactional
    public AttendanceDTO createAttendance(AttendanceDTO dto) {
        Attendance attendance = new Attendance();
        return saveOrUpdateAttendance(dto, attendance);
    }

    @Transactional
    public AttendanceDTO updateAttendance(Long id, AttendanceDTO dto) {
        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asistencia no encontrada con id: " + id));
        return saveOrUpdateAttendance(dto, attendance);
    }

    @Transactional
    public void deleteAttendance(Long id) {
        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asistencia no encontrada con id: " + id));
        attendanceRepository.delete(attendance);
    }

    private AttendanceDTO saveOrUpdateAttendance(AttendanceDTO dto, Attendance attendance) {
        attendance.setDate(dto.getDate());
        attendance.setAttended(dto.isAttended());
        attendance.setProfessorVerified(dto.isProfessorVerified());
        attendance.setProfessorVerificationTime(dto.getProfessorVerificationTime());
        attendance.setHeadStudentVerified(dto.isHeadStudentVerified());
        attendance.setHeadStudentVerificationTime(dto.getHeadStudentVerificationTime());
        attendance.setCheckerVerified(dto.isCheckerVerified());
        attendance.setCheckerVerificationTime(dto.getCheckerVerificationTime());

        if (dto.getClassSessionId() != null) {
            attendance.setClassSession(classSessionRepository.findById(dto.getClassSessionId())
                    .orElseThrow(() -> new RuntimeException("Sesión de clase no encontrada")));
        }

        if (dto.getCheckerId() != null) {
            attendance.setChecker(userRepository.findById(Math.toIntExact(dto.getCheckerId()))
                    .orElseThrow(() -> new RuntimeException("Usuario checador no encontrado")));
        }

        return convertToDTO(attendanceRepository.save(attendance));
    }

    private AttendanceDTO convertToDTO(Attendance attendance) {
        AttendanceDTO dto = new AttendanceDTO();
        dto.setId(attendance.getId());
        dto.setDate(attendance.getDate());
        dto.setAttended(attendance.isAttended());
        dto.setProfessorVerified(attendance.isProfessorVerified());
        dto.setProfessorVerificationTime(attendance.getProfessorVerificationTime());
        dto.setHeadStudentVerified(attendance.isHeadStudentVerified());
        dto.setHeadStudentVerificationTime(attendance.getHeadStudentVerificationTime());
        dto.setCheckerVerified(attendance.isCheckerVerified());
        dto.setCheckerVerificationTime(attendance.getCheckerVerificationTime());

        if (attendance.getClassSession() != null) {
            dto.setClassSessionId(Long.valueOf(attendance.getClassSession().getId()));
        }

        if (attendance.getChecker() != null) {
            dto.setCheckerId(Long.valueOf(attendance.getChecker().getId()));
        }

        return dto;
    }
}
